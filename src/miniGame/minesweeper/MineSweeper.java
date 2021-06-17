package miniGame.minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import miniGame.MiniGame;

/**
 * 
 * @author miri
 * 메인클래스를 위한 보조 클래스
 * 메인 클래스 작성시 버튼을 배열로 만드는데 
 * 해당 버튼의 행,열 등 확인해야 하는 것을 필드에 선언
 * 메인 클래스에서 버튼을 생성 할때 MyButton으로 생성한다.
 *	MyButton은 JButton을 상속함.
 *
 */
class MyButton extends JButton {
	int row, col;
	boolean isClicked;
	String imagePath = "";

	public MyButton(String name) {
		super(name);
	}
	
	public MyButton(int r, int c) {
		this(null, r, c);
	}

	public MyButton(String msg, int r, int c) {
		super(msg);
		row = r;
		col = c;
	}
}

/**
 * 
 * @author miri
 * MineSweeper (지뢰찾기) 게임 클래스
 *
 */
public class MineSweeper extends JPanel implements MiniGame, MouseListener, MineSweeperContents {
	private MainPanel_MineSweeper parents; // 부모로 설정할 클래스의 자료형으로 지정
	private MyButton[][] button;
	private int[][] mineArray;

	private final int GAME_ROW;
	private final int GAME_COL;
	private final int TOTALMINE;
	
	private ImageIcon imageIcon;
	private Image image;
	private Image changeImage;
	private int flag;
	private MyButton flagButton;
	private int totalClick;
	private int totalFlag;
	private boolean gameOver;
	private boolean success;
	private Timer timer;
	protected MyButton timerButton;
	private boolean timerRun;
	private int time;
	
	/**
	 * 메인 클래스 생성자
	 * @param parents 이전 화면에서 해당 패널을 불러올 때 연동하기 위해 사용
	 * @param row 행의 숫자 (게임난이도를 위해 지정)
	 * @param col 열의 숫자 (게임난이도를 위해 지정)
	 * @param totalMine 총 지뢰의 숫자 (게임난이도를 위해 지정)
	 */
	MineSweeper(MainPanel_MineSweeper parents, int row, int col, int totalMine) {
		super();
		this.parents = parents;
		this.GAME_ROW = row;
		this.GAME_COL = col;
		this.TOTALMINE = totalMine;
		this.flag = totalMine;

		mineArray(); // 정답 배열 만들기
		add(mainPanel()); // 메인패널 붙여넣기

		// 정답 참조
		for (int i = 0; i < GAME_ROW; ++i) {
			System.out.println(Arrays.toString(mineArray[i]));
		}


		setTimer(); // 타이머 생성 

	}

	/**
	 * 메인 패널 만들기
	 * 레이아웃 설정하여 각각의 패널을 추가한다.
	 * @return 메인 패널
	 */
	public JPanel mainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		panel.add(setMineSweeper(), BorderLayout.CENTER);
		panel.add(menuPanel(), BorderLayout.NORTH);
		
		panel.setPreferredSize(new Dimension(ROW, COL));
		return panel;
	}
	
	/**
	 * 메뉴 패널 만들기
	 * 다시 시작 / 타이머 / 깃발 수 / 이전으로 버튼을 넣는다.
	 * @return 메뉴 패널
	 */
	public JPanel menuPanel() {
		JPanel panel = new JPanel();
		
		// 다시시작 버튼
		MyButton replay = new MyButton("다시 시작");
		replay.setBackground(Color.LIGHT_GRAY);
		replay.setFont(FONT);
		replay.addMouseListener(this);
		
		// 타이머 표시 버튼
		timerButton = new MyButton("0초");
		timerButton.setBackground(Color.LIGHT_GRAY);
		timerButton.setPreferredSize(new Dimension(100, 35));
		timerButton.setFont(FONT);
		
		// 깃발 갯수 표시 버튼
		flagButton = new MyButton("" + flag);
		image = new ImageIcon(MineSweeper.class.getResource("image/깃발.png")).getImage();
		changeImage = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(changeImage);
		flagButton.setIcon(imageIcon);
		
		flagButton.setBackground(Color.LIGHT_GRAY);
		flagButton.setPreferredSize(new Dimension(100, 35));
		flagButton.setFont(FONT);
		
		// 이전으로 버튼
		MyButton back = new MyButton("이전으로");
		back.setBackground(Color.LIGHT_GRAY);
		back.setFont(FONT);
		back.addMouseListener(this);
		
		panel.add(flagButton);
		panel.add(timerButton);
		panel.add(replay);
		panel.add(back);

		return panel;
	}
	
	/**
	 * 게임 패널 만들기 
	 * 레이아웃으로 게임 판 (지뢰 판)을 만들고 버튼을 추가한다.
	 * @return 게임패널
	 */
	public JPanel setMineSweeper() {
		JPanel panel = new JPanel();

		panel.setBackground(BACKGROUND_COLOR);

		panel.setLayout(new GridLayout(GAME_ROW, GAME_COL));
		panel.setPreferredSize(new Dimension(700,700));
		setMyButton(panel);

		return panel;
	}
	
	/**
	 * 게임판의 버튼 배열 만들기
	 * 버튼을 배열로 만들어 생성하고 글씨체 등을 세팅한다.
	 * @param panel 버튼을 추가할 패널 지정
	 */
	public void setMyButton(JPanel panel) {
		button = new MyButton[GAME_ROW][GAME_COL];
		for (int i = 0; i < GAME_ROW; ++i) {
			for (int j = 0; j < GAME_COL; ++j) {
				button[i][j] = new MyButton(i, j);
				button[i][j].setBackground(BACKGROUND_COLOR);
				button[i][j].setFont(BUTTON_FONT);
				button[i][j].setBorderPainted(true);
				button[i][j].addMouseListener(this);
				panel.add(button[i][j]);
			}
		}
	}
	
	/**
	 * 정답 배열 만들기 
	 * 정답을 담은 배열을 만든다. 지뢰 설정은 상수 BOOM 이용
	 */
	public void mineArray() {
		mineArray = new int[GAME_ROW][GAME_COL];
		
		// 난이도 별로 총 지뢰 갯수에 맞게 지뢰 자리 랜덤으로 설정하기
		for (int i = 0; i < TOTALMINE; ++i) {
			int a = (int) (Math.random() * GAME_ROW);
			int b = (int) (Math.random() * GAME_COL);

			if (mineArray[a][b] == BOOM) {
				--i;
				continue;
			}
			mineArray[a][b] = BOOM;
		}
		
		// 지뢰을 제외한 나머지 숫자 지정
		for (int i = 0; i < GAME_ROW; ++i) {
			for (int j = 0; j < GAME_COL; ++j) {
				if (mineArray[i][j] == BOOM)
					setArrayExceptMine(mineArray, i, j);
			}

		}

	}
	
	/**
	 * 지뢰 제외하고 숫자 배정하는 메서드
	 * 
	 * @param arrays 지정할 배열
	 * @param row 지정할 행
	 * @param col 지정할 열
	 */
	public void setArrayExceptMine(int[][] arrays, int row, int col) {
		// 지뢰 제외하고 숫자 배정
		int startR = row - 1 < 0 ? 0 : row - 1;
		int endR = row + 1 == GAME_ROW ? GAME_ROW - 1 : row + 1;
		int startC = col - 1 < 0 ? 0 : col - 1;
		int endC = col + 1 == GAME_COL ? GAME_COL - 1 : col + 1;

		for (int a = startR; a <= endR; ++a) {
			for (int b = startC; b <= endC; ++b) {
				if (arrays[a][b] == BOOM) {
					continue;
				}
				++arrays[a][b];
			}
		}
	}
	
	/**
	 * 눌렀는데 0번이면 주변 3X3 부분 자동으로 열리게 만들기
	 * 
	 * @param myButton 지정할 버튼 배열
	 * @param row 지정할 행
	 * @param col 지정할 열
	 */
	public void clickToZero(MyButton[][] myButton, int row, int col) {
		// 눌렀는데 0 번일때 주변 뜨게하기

		if ("0".equals(myButton[row][col].getText())) {
			
			int startR = row - 1 < 0 ? 0 : row - 1;
			int endR = row + 1 == GAME_ROW ? GAME_ROW - 1 : row + 1;
			int startC = col - 1 < 0 ? 0 : col - 1;
			int endC = col + 1 == GAME_COL ? GAME_COL - 1 : col + 1;

			myButton[row][col].isClicked = true;

			for (int i = startR; i <= endR; ++i) {
				for (int j = startC; j <= endC; ++j) {
					clickTheButtonColor(button, i, j, false);

					if (!myButton[i][j].isClicked) {
						//클릭 되지 않은건 다시 재귀호출 - 클릭한것 중에 다시 0이있으면 그것도 주변을 뜨게 해야하기 위함
						clickToZero(myButton, i, j);
					}
				}

			}
		}
	}
	
	/**
	 * 숫자별 색상 설정하는 메소드 오버로드
	 * true일때는 굳이 변수로 설정하지 않아도 되도록
	 * @param myButton 지정할 버튼 배열
	 * @param row 지정할 행
	 * @param col 지정할 열
	 */
	public void clickTheButtonColor(MyButton[][] myButton, int row, int col) {
		clickTheButtonColor(myButton, row, col, true);
	}
	
	/**
	 * 숫자별 색상 설정하기
	 * 지뢰를 유추하는 범위가 3x3이므로 최대 지뢰를 표시하는 8까지 나올 수 있다.
	 * 숫자 8까지의 각각의 색상을 지정하고, 지뢰를 눌렀을 때에는 지뢰의 이미지가 표시되게 한다.
	 * @param myButton 지정할 버튼 배열
	 * @param row 지정할 행
	 * @param col 지정할 열
	 * @param isGameover 게임오버인지 여부
	 */
	public void clickTheButtonColor(MyButton[][] myButton, int row, int col, boolean isGameover) {
		// 숫자별 색상 설정
		myButton[row][col].setText(String.valueOf(mineArray[row][col]));
		myButton[row][col].setIcon(null);

		switch (myButton[row][col].getText()) {
		case "0":
			myButton[row][col].setForeground(GAME_BUTTON_COLOR);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "1":
			myButton[row][col].setForeground(COLOR1);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "2":
			myButton[row][col].setForeground(COLOR2);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "3":
			myButton[row][col].setForeground(COLOR3);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "4":
			myButton[row][col].setForeground(COLOR4);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "5":
			myButton[row][col].setForeground(COLOR5);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "6":
			myButton[row][col].setForeground(COLOR6);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "7":
			myButton[row][col].setForeground(COLOR7);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		case "8":
			myButton[row][col].setForeground(COLOR8);
			myButton[row][col].setBackground(GAME_BUTTON_COLOR);
			break;
		}
		if (isGameover) {
			if (mineArray[row][col] == BOOM) {
				myButton[row][col].imagePath = "image\\폭탄.png";
				addButtonImage(button[row][col]);
				myButton[row][col].setText(null);
				myButton[row][col].setBackground(BOOM_BACKGROUND_COLOR);
			}
		}
	}
	
	/**
	 * 버튼에 이미지 붙이기
	 * switch문을 사용하여 게임 난이도별로 나누고(난이도마다 버튼의 크기가 달라 크기 지정하기 위함),
	 * 게임 버튼에 이미지를 추가한다.
	 * @param myButton 지정할 버튼 배열
	 */
	public void addButtonImage(MyButton myButton) {
		// 버튼에 이미지 붙이기
		if (myButton.imagePath.isEmpty())
			return;
		image = new ImageIcon(MineSweeper.class.getResource(myButton.imagePath)).getImage();
		switch(GAME_ROW) {
		case 9 :
			changeImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			break;
		case 16 :
			changeImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			break;
		case 30 :
			changeImage = image.getScaledInstance(30, 20, Image.SCALE_SMOOTH);
			break;
		}
		
		imageIcon = new ImageIcon(changeImage);
		myButton.setIcon(imageIcon);

	}
	
	/**
	 * 지뢰를 눌렀을 때 모든 판이 오픈되도록 설정한다
	 * @param myButton 지정할 버튼 배열
	 * @param row 지정할 행
	 * @param col 지정할 열
	 */
	public void clickTheBOOM(MyButton[][] myButton, int row, int col) {
		// 지뢰 눌렀을 때

		if (myButton[row][col].getText() == null) {
			for (int i = 0; i < GAME_ROW; ++i) {
				for (int j = 0; j < GAME_COL; ++j) {
					clickTheButtonColor(button, i, j);
					myButton[i][j].removeMouseListener(this);
				}
			}
			gameOver = true;
			success = false;
		}
	}
	
	/**
	 * 지뢰를 유추하여 
	 * 지뢰를 제외한 모든 판에 클릭해서 숫자가 뜨게 한 후
	 * 빈화면에 깃발까지 다 꽂혀있을때 최종적으로 게임이 끝나도록 설정한다
	 * 
	 * @param myButton 지정할 버튼 배열
	 */
	public void findAllMine(MyButton[][] myButton) {
		// 지뢰를 모두 찾았을때 게임 끝 클릭 다 하고 + 깃발까지 꽂아야함

		// 클릭한 갯수 총 합 = 폭탄이아닌것 버튼 총 합
		totalClick = 0;

		for (int i = 0; i < GAME_ROW; ++i) {
			for (int j = 0; j < GAME_COL; ++j) {
				if (myButton[i][j].getText() != null && !myButton[i][j].getText().isBlank()) {
					++totalClick;
				}
			}
		}

		// 오른쪽마우스로 깃발표시 총합 = 폭탄 총합
		totalFlag = 0;
		for (int i = 0; i < GAME_ROW; ++i) {
			for (int j = 0; j < GAME_COL; ++j) {
				if (mineArray[i][j] == BOOM && myButton[i][j].imagePath.equals("image\\깃발.png")) {
					++totalFlag;
				}
			}
		}

		if (totalClick == GAME_ROW * GAME_COL - TOTALMINE && totalFlag == TOTALMINE) {
			for (int i = 0; i < GAME_ROW; ++i) {
				for (int j = 0; j < GAME_COL; ++j) {
					myButton[i][j].removeMouseListener(this);
				}
			}
			gameOver = true;
			success = true;
		}

	}
	
	/**
	 * 게임이 종료 되었을 때 게임 성공 / 실패 별 메세지 지정
	 */
	public void GameOver() {
		if (gameOver) {
			if (success) {
				timer.stop();
				JOptionPane.showMessageDialog(getRootPane(), "축하합니다! 폭탄을 모두 찾으셨습니다!\n총 소요 시간 : " + time + "초");
				return;
			}
			
			timer.stop();
			JOptionPane.showMessageDialog(getRootPane(), "---GAME OVER---\n폭탄을 클릭하셨습니다.");
		}
	}
	
	/**
	 * 타이머 생성하기 
	 */
	public void setTimer() {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timerRun = true;
				++time;
				timerButton.setText(String.valueOf(time) + "초");

			}
		});

	}
	
	/**
	 * 게임 사용자가 플래그로 표시한 총 갯수 세기
	 * 
	 * @param myButton 지정할 버튼 배열
	 */
	public void setCountFlag(MyButton[][] myButton) {
		//플래그 갯수 세기
		flag = TOTALMINE;
		for (int i = 0; i < GAME_ROW; ++i) {
			for(int j = 0; j < GAME_COL; ++j)
				
			try {	
				if(myButton[i][j].getText().isEmpty()
						&& myButton[i][j].imagePath.equals("image\\깃발.png")) {
				// 이미 플래그를 했는데 판이 열린 경우 + 플래그로 표시해 둔 경우 
					--flag;
				} 
			}catch(NullPointerException e) {
				return;
			}
				
		}
		flagButton.setText("" + flag); // 버튼에 표시
	}
	
	
	/**
	 * 마우스 클릭시에 발생하는 이벤트 설정
	 */
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		MyButton click = (MyButton) e.getSource();
//		System.out.println(clickButton.row + "행" + clickButton.col + "열"); // 행, 열 맞는지 sysout으로 체크 용도
		int row = click.row; // 버튼의 행
		int col = click.col; // 버튼의 열 
		click.isClicked = true;

		if (e.isMetaDown()) { // *** 오른쪽마우스 클릭할때 ***
			switch (click.imagePath) {
			case "image\\깃발.png": // 오른쪽마우스 두번클릭
				click.imagePath = "image\\물음표.png";
				break;
			case "image\\물음표.png": // 오른쪽마우스 세번클릭
				click.imagePath = "image\\공백.png";
				break;
			default: // 그 외 모든 오른쪽마우스 클릭시
				click.imagePath = "image\\깃발.png";
				break;
			}
			// switch문으로 정해진 이미지파일을 버튼에 추가
			addButtonImage(click);
			findAllMine(button);
			GameOver();
			setCountFlag(button);
			return;
		}

		// *** 여기서부터는 왼쪽마우스 클릭 설정 ***

		switch (click.getText()) {
		case "다시 시작":
			parents.retry();
			return;
			
		case "이전으로":
			parents.addNewPanel(new LevelChoice(parents));
			return;
		}

		timer.start(); // 마우스 클릭 시 timer 시작

		click.setText(String.valueOf(mineArray[row][col]));

		clickTheButtonColor(button, row, col);

		clickToZero(button, row, col);

		findAllMine(button);

		clickTheBOOM(button, row, col);

		GameOver();
		
		setCountFlag(button);
		
		click.removeMouseListener(this);

	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
	}

}
