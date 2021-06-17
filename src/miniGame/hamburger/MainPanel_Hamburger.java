package miniGame.hamburger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import miniGame.MiniGame;

/**
 * 
 * @author miri
 * MineSweeper 게임의 첫 시작 화면 (게임시작 / 게임방법)
 * 
 */
public class MainPanel_Hamburger extends JPanel implements MiniGame,ActionListener{
	
	protected JFrame frame; 
	protected int totalClear;
	
	private JFrame parent;
	
	/**
	 * 메인 화면(패널)
	 * @param parent 이전 화면에서 해당 패널을 불러올 때 연동하기 위해 사용
	 * 게임을 고르는 화면에서 해당 생성자를 불러와야하기 때문에 public으로 지정한다.
	 */
	public MainPanel_Hamburger(JFrame parent){
		super();
		this.parent = parent;
		
		setPreferredSize(new Dimension(ROW,COL));
		setBackground(Color.white);
		setLayout(null);
		
		add(setStartButton());
		add(setRuleButton());
		
		
		// 프레임 부분 
		frame = new JFrame("햄버거 만들기");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 해당 클래스 단독으로 사용시에만 처리.
		frame.add(this);
		frame.pack();
		frame.setFocusable(true);
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}	
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					frame.dispose();
					parent.requestFocus(); // 해당 프레임이 꺼지고 포커스를 지정 된 parent에 맞춘다.
				}
			}
		});
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	
	/**
	 * 게임 시작 버튼 만들기
	 * 버튼 글씨, 위치 등 세팅 후에 액션리스너 추가
	 * 
	 * @return 게임시작 버튼
	 */
	private JButton setStartButton() {
		JButton start = new JButton(BUTTON_START);
		
		start.setFont(new Font(Font.SANS_SERIF,Font.BOLD,45));
		start.setBackground(new Color(255, 240, 122));
		start.setBounds(250, 150, 300, 200);
		start.setBorderPainted(false);
		start.addActionListener(this);
		
		return start;
	}

	/**
	 * 게임 방법 버튼 만들기
	 * 버튼 글씨, 위치 등 세팅 후에 액션리스너 추가
	 * 
	 * @return 게임방법 버튼
	 */
	private JButton setRuleButton() {
		JButton rule = new JButton(BUTTON_RULE);
		
		rule.setFont(new Font(Font.SANS_SERIF,Font.BOLD,45));
		rule.setBackground(new Color(255, 240, 122));
		rule.setBounds(250,400,300,200);
		rule.setBorderPainted(false);
		rule.addActionListener(this);
		
		return rule;
	}
	
	/**
	 * 게임 방법 화면 생성하기 
	 * 게임 방법을 누르게되면 그림파일로 작성된 게임파일이 새로운 프레임으로 열림
	 */
	public void rulePanel() {
		JFrame frame = new JFrame("게임방법");
		
		JPanel panel = new JPanel()	{
			Image background = new ImageIcon(MainPanel_Hamburger.class.getResource("image/게임방법.png")).getImage();
			public void paint(Graphics g) {//그리는 함수
				g.drawImage(background, 0, 0, 800, 750, null);//background를 그려줌		
		}};
		panel.setPreferredSize(new Dimension(800,750));
		
		frame.add(panel);
		frame.pack();
		frame.setFocusable(true);
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}	
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					frame.dispose();
					parent.requestFocus();
				}
			}
		});
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * 새로운 패널 열기
	 * 메인프레임에 설정된 지금 패널을 모두 지우고 새롭게 열릴 패널을 지정하여 불러온다.
	 * @param panel 새롭게 열릴 패널
	 */
	public void addNewPanel(JComponent panel) {
		//새로운 패널 열기
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}
	
	
	/**
	 * 액션리스너 - 게임시작 / 게임 방법 시작 시 해당되는 패널 또는 프레임으로 이동
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton click = (JButton)e.getSource();
		switch(click.getText()) {
		case BUTTON_START :
			addNewPanel(new Hamburger(this));
			break;
		
		case BUTTON_RULE :
			rulePanel();
			break;
		
		}
		
	}

	
	public static void main(String[] args) {
		new MainPanel_Hamburger(null);
	}
	
}