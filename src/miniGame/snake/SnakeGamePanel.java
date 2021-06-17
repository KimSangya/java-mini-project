package miniGame.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
 
/**
 * 
 * @author ChoiMunKyoung
 *
 */

public class SnakeGamePanel extends JPanel implements SNAKEGAMEINTERFACE, KeyListener {
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	/**
	 * 방향키에 맞춰서 direction 값 변경
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
	
		case KeyEvent.VK_UP:
			if (direction != DOWN) {
				direction = UP;
			}
			break;
		case KeyEvent.VK_DOWN:
			if (direction != UP) {
				direction = DOWN;
			}
			break;

		case KeyEvent.VK_LEFT:
			if (direction != RIGHT) {
				direction = LEFT;
			}
			break;

		case KeyEvent.VK_RIGHT:
			if (direction != LEFT) {
				direction = RIGHT;
			}
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	/**
	 사과의 x 좌표
	 */
	int appleX;
	/**
	 사과의 Y 좌표
	 */
	int appleY;
	/**
	 먹은 사과의 개수
	 */
	int eatenApple;
	/**
	 몸의 길이
	 */
	int bodyLength;
	/**
	 몸의 x 좌표값들의 배열
	 */
	int[] bodyX;
	/**
	 몸의 y 좌표값들의 배열
	 */
	int[] bodyY;
	/**
	 방향값
	 */
	int direction;
	

	Timer timer;
	
	/**
	 * status 배열에 맞춰서 색이 들어갈 패널들
	 */
	private JPanel[][] panels = new JPanel[GAMEHEIGHT][GAMEWIDTH]; // panel
	/**
	  사과 몸 값이 들어갈 배열
	 */
	private int[][] status = new int[GAMEHEIGHT][GAMEWIDTH]; // status
	JPanel mainPanel;
	
	MainPanel_SnakeGame parent;

	public SnakeGamePanel(MainPanel_SnakeGame parent) {
		
		this.parent = parent;

		bodyLength = 3;
		bodyX = new int[GAMEHEIGHT];
		bodyY = new int[GAMEWIDTH];
		//초기값 몸의 좌표 , 방향은 오른쪽
		bodyX[0] = 0;
		bodyY[0] = 3;
		bodyX[1] = 0;
		bodyY[1] = 2;
		bodyX[2] = 0;
		bodyY[2] = 1;
		direction = RIGHT;

		mainPanel = new JPanel();
		
		mainPanel.setPreferredSize(new Dimension(800,800));
		
		mainPanel.setLayout(new GridLayout(GAMEHEIGHT, GAMEWIDTH));

		for (int i = 0; i < GAMEHEIGHT; ++i) {
			for (int j = 0; j < GAMEWIDTH; ++j) {
				panels[i][j] = new JPanel();
				panels[i][j].setBackground(Color.black);
				mainPanel.add(panels[i][j]);
			}
		}

		add(mainPanel);
		setPreferredSize(new Dimension(800, 800));
		parent.frame.addKeyListener(this);
		setVisible(true);
		spawnApple();
		redraw();

		timer = new Timer(100, new ActionListener() {
			@Override
			/**
			100밀리초 마다 
			redraw()
			setBodyXY()
			eatApple()
			loseGame()
			를 실행
			 */
			public void actionPerformed(ActionEvent e) {
				redraw();
				setBodyXY();
				eatApple();
				loseGame();
			}
		});

		timer.start();
	}
	
/**
 *  랜덤한 두 정수로 좌표값 설정후 status 배열에 EMPTY면 APPLE 값으로 변경
 */
	void spawnApple() {
		appleX = (int) (Math.random() * GAMEHEIGHT);
		appleY = (int) (Math.random() * GAMEWIDTH);
		if (status[appleX][appleY] == EMPTY) {
			status[appleX][appleY] = APPLE;
		} else {
			spawnApple();
		}
	}

	/**
	 * 몸의 배열을 status에 대입하고
	 * 
	 * status 배열에 맞춰서 panels에 색을 설정해준다
	 * 
	 * panels 를 main패널에 add
	 * 
	 */
	void redraw() {
		clean();
		
		try {
		for (int i = 0; i < bodyLength; i++) {
			status[bodyX[i]][bodyY[i]] = BODY;
		}
		}catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.getStackTrace();
			JOptionPane.showMessageDialog(null, "LOSE \n"
					+ "먹은 사과의 개수는 : " + eatenApple + " 개 입니다!");
			timer.stop();
		}
		

		for (int i = 0; i < GAMEHEIGHT; i++) {
			for (int j = 0; j < GAMEWIDTH; j++) {
				if (status[i][j] == EMPTY) {
					panels[i][j].setBackground(BORADCOLOR);
				} else if (status[i][j] == APPLE) {
					panels[i][j].setBackground(APPLECOLOR);
				} else if (status[i][j] == BODY) {
					panels[i][j].setBackground(SNAKECOLOR);
				}
			}
		}
		panels[bodyX[0]][bodyY[0]].setBackground(new Color(6,128,0));
		add(mainPanel);

	}

	/**
	 *  이전의 몸 배열의 값들을 지워주기 위해
	 *  redraw() 할때 먼저 모든 status 의 BODY 값을 EMPTY 로 변경
	 */
	void clean() {
		for (int i = 0; i < GAMEHEIGHT; i++) {
			for (int j = 0; j < GAMEWIDTH; j++) {
				if (status[i][j] == BODY) {
					status[i][j] = EMPTY;
				}
			}
		}
	}
	/**
	 * 머리부분이 APPLE값이라면 몸의길이를 늘리고 먹은 사과 개수 늘리고 spawnApple()실행
	 */
	void eatApple() {
		try {
		if (status[bodyX[0]][bodyY[0]] == APPLE) {
			bodyLength++;
			eatenApple++;
			spawnApple();
			
		}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			e.getStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * direction 값에 맞춰서 증감식 리턴
	 */
	int moving(int pressedKey) {

		switch (pressedKey) {
		case 1:
			return bodyX[0]--;

		case 2:

			return bodyX[0]++;

		case 3:

			return bodyY[0]--;

		case 4:

			return bodyY[0]++;

		case 5:
			return bodyY[0]++;

		default:

			break;
		}
		return -1;

	}
/**
 * 머리부분이 몸의 좌표값과 같아지면 lose 먹은 사과 개수 출력
 */
	void loseGame() {
		for (int i = 1; i < bodyLength; i++) {
			if (bodyX[0]==bodyX[i]&&bodyY[0]==bodyY[i]) {
				JOptionPane.showMessageDialog(null, "LOSE \n"
						+ "먹은 사과의 개수는 : " + eatenApple + " 개 입니다!");
				timer.stop();
			}
		}

	}
	/**
	 * 머리부분을 몸이 따라가게 보이기위해
	 * 머리 부분을 제외하고 몸의 배열을 이전 배열 넣어준다
	 */

	void setBodyXY() {

		for (int i = bodyLength - 1; i > 0; i--) {
			bodyX[i] = bodyX[i - 1];
		}
		for (int i = bodyLength - 1; i > 0; i--) {
			bodyY[i] = bodyY[i - 1];
		}

		moving(direction);

	}

	public static void main(String[] args) {
	}

}