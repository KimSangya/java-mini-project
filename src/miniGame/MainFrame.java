package miniGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import miniGame.game_2048.MainPanel_2048;
import miniGame.hamburger.MainPanel_Hamburger;
import miniGame.minesweeper.MainPanel_MineSweeper;
import miniGame.pinpon.MainPanel_pinpon;
import miniGame.snake.MainPanel_SnakeGame;
import miniGame.tetris.MainPanel_Tetris;

/**
 * 
 * @author miri
 * 미니게임 선택할 수 있는 메인 창
 * 각자 만든 미니게임 취합하기
 * 
 */
public class MainFrame extends JFrame implements MiniGame, ActionListener{
	
	private Image image; // 버튼에 넣을 이미지
	private Image changeImage; // 크기 바꿀 이미지
	private ImageIcon imageIcon; // 버튼에 부착할 이미지아이콘
	
	/**
	 * 메인 생성자
	 * Frame 설정, 만든 메인 패널 붙이기
	 */
	MainFrame(){
		super("미니게임 천국");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(ROW+50, COL+50);
		
		add(mainPanel());
		
		setFocusable(true);
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}	
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * 메인패널 만들기
	 * 만들어놓은 패널들을 해당 구역에 붙인다
	 * @return 메인패널
	 */
	public JPanel mainPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());
		panel.add(northPanel(), BorderLayout.NORTH);
		panel.add(centerPanel(), BorderLayout.CENTER);
		panel.add(sidePanel(), BorderLayout.EAST);
		panel.add(sidePanel(), BorderLayout.WEST);
		return panel;
	}
	
	/**
	 * 메인 패널의 제목. 간판이미지 붙여넣기
	 * @return 상단 패널
	 */
	public JPanel northPanel() {
		JPanel panel = new JPanel();
		panel.add(setIconButton(null, PATH_MINGAME, ROW-50, 150));
		panel.setBackground(Color.white);
		return panel;
	}
	
	/**
	 * 중앙 패널 만들기
	 * GridLayout을 사용하여 각각의 게임 아이콘을 붙여넣는다
	 * @return 중앙 패널 (게임 선택 패널)
	 */
	public JPanel centerPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(3,2));
		panel.add(setIconButton(TITLE_MINESWEEPER, PATH_MINESWEEPER, 150, 150));
		panel.add(setIconButton(TITLE_HAMBURGER, PATH_HAMBURGER, 150, 150));
		panel.add(setIconButton(TITLE_TETRIS, PATH_TETRIS, 150, 150));
		panel.add(setIconButton(TITLE_SNAKE, PATH_SNAKE, 150, 150));
		panel.add(setIconButton(TITLE_2048, PATH_2048, 150, 150));
		panel.add(setIconButton(TITLE_PINPON, PATH_PINPON, 150, 150));
		
		panel.setBackground(Color.white);
		
		return panel;
	}
	
	/**
	 * 게임을 선택하는 중앙 패널을 보기 좋게 만들기 위해서
	 * 양 옆에 공백을 넣어주기 위한 패널
	 * @return 사이드 패널 (양옆에 동일한 패널넣음)
	 */
	public JPanel sidePanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setPreferredSize(new Dimension(100,800));
		
		return panel;
	}
	
	/**
	 * 버튼 아이콘 만들기
	 * 버튼에 이미지를 넣고, 버튼을 세팅한뒤에 액션리스너를 붙여준다.
	 * 
	 * @param title 만들 버튼의 이름. button.setText();
	 * @param imagePath 버튼에 넣을 이미지 경로
	 * @param ImageWidth 버튼에 넣을 이미지 가로 크기
	 * @param ImageHeight 버튼에 넣을 이미지 세로 크기
	 * 
	 * @return 세팅된 버튼 아이콘
	 */
	public JButton setIconButton(String title, String imagePath, int ImageWidth, int ImageHeight) {
		// 버튼에 이미지 붙이기
		JButton button = new JButton(title);
		
		addButtonImage(button, imagePath, ImageWidth, ImageHeight);
		setButtonView(button);
		button.addActionListener(this);
		
		return button;
	}
	
	/**
	 * 버튼에 붙일 이미지 세팅하기 (이미지 크기, 버튼에 아이콘으로 붙이기)
	 * @param button 세팅할 버튼 지정
	 * @param imagePath 버튼에 넣을 이미지 경로
	 * @param ImageWidth 버튼에 넣을 이미지 가로 크기
	 * @param ImageHeight 버튼에 넣을 이미지 세로 크기
	 */
	public void addButtonImage(JButton button, String imagePath, int ImageWidth, int ImageHeight) {
		// 버튼에 붙일 이미지 세팅하기
		image = new ImageIcon(MainFrame.class.getResource(imagePath)).getImage();
		changeImage = image.getScaledInstance(ImageWidth, ImageHeight, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(changeImage);
		button.setIcon(imageIcon);
	}
	
	/**
	 * 버튼에 이미지를 제외한 보이는 부분 세팅 (버튼 테두리, 색채움, 텍스트 위치, 텍스트 크기)
	 * @param button 세팅할 버튼 지정
	 */
	public void setButtonView(JButton button) {
		// 버튼 자체의 세팅
		button.setBorderPainted(false); // 버튼 테두리 없애기
		button.setFocusPainted(false); // 버튼 선택되었을때 테두리 안생기게
		button.setContentAreaFilled(false); // 버튼 내용 색 안채움
		button .setHorizontalTextPosition(JButton.CENTER); // 텍스트 가운데
		button .setVerticalTextPosition(JButton.BOTTOM); // 텍스트 아래로
		button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
	}
	
	/**
	 * 클릭한 버튼이름에 맞춰 해당 클래스 불러오기.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton click = (JButton)e.getSource();
		switch(click.getText()) {
		case TITLE_MINESWEEPER :
			new MainPanel_MineSweeper(this);
			break;
		
		case TITLE_HAMBURGER :
			new MainPanel_Hamburger(this);
			break;
		
		case TITLE_SNAKE :
			new MainPanel_SnakeGame(this);
			break;
			
		case TITLE_TETRIS : 
			new MainPanel_Tetris(this);
			break;
			
		case TITLE_2048 : 
			new MainPanel_2048(this);
			break;
			
		case TITLE_PINPON : 
			new MainPanel_pinpon(this);
			break;
		
		}
		
	}
	
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
}
