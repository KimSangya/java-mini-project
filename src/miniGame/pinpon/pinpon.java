package miniGame.pinpon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
/**
 * @author 김건호
 * 
 * 간단하게 내편 내편을 나누는 방식으로 색이 다른 30개의 원을 빠르게 정리 하는 게임.
 * 
 */
	/**
	 * 내부원의 너비, 내부원의 높이, 내부원의 시작점
	 */
class Circle {
	
	public static final int CIRCLE_WIDTH = 100;
	public static final int CIRCLE_HEIGHT = 100;
	public static final int CIRCLE_START_X = pinpon.WINDOW_WIDTH / 2 - CIRCLE_WIDTH / 2;

	Color color;

	int width = CIRCLE_WIDTH;
	int height = CIRCLE_HEIGHT;
	int x = CIRCLE_START_X;
	int y;
		/**
		 * 내부원의 색깔 지정
		 * @param y 높이값을 지정
		 */
	public Circle(int y) {
		

		this.y = y;
		color = Math.random() > 0.5 ? Color.red : Color.green;

	}
}
	/**
	 * 창의 크기 및 원의 개수, 움직이는 원의 시간
	 */
class pinpon extends JFrame implements KeyListener {
	
	public static final int INIT_NUMBER_OF_CIRCLES = 30;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 800;
	public static final int BOTTOM_CIRCLE_Y = 600;
	Circle[] circles = new Circle[INIT_NUMBER_OF_CIRCLES];
	private int currentIndex; // 마지막 공부터..
	int score;
	private Image image;
	private List<Circle> movingCircles = new ArrayList<Circle>();
	private List<Timer> timerList = new ArrayList<Timer>();
	long StartTime = System.currentTimeMillis();
	// 원 생성해서 circle 배열에 담기
	private void initCircles() {
		for (int i = 0; i < circles.length; ++i) {
			circles[i] = new Circle(BOTTOM_CIRCLE_Y - i * Circle.CIRCLE_HEIGHT);
		}
	}
	
	/**
	 * 창의 크기 지정 및 창 생성.
	 */
	public pinpon() {
		
		JOptionPane.showMessageDialog(null, "시작하시려면 확인버튼 눌러주시면됩니다.");
		addKeyListener(this);
		initCircles();
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		setVisible(true);
		setFocusable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 키보드의 움직이는 값 입력 및 생성
	 * 만약 30개의 공이 모두 도달했을때의 결과을 입력
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		
		int key = e.getKeyCode();
		System.out.println("currentIndex : " + currentIndex);
		

		Circle c = circles[currentIndex];
		movingCircles.add(c);

		for (Circle c2 : movingCircles) {
			if (key == KeyEvent.VK_LEFT) {
				moveTo(c.x - 200, c);
			}
			
			else if (key == KeyEvent.VK_RIGHT) {
				moveTo(c.x + 200, c);
			}
				if(circles[currentIndex].color == Color.red && key == KeyEvent.VK_RIGHT) {
					++score;
				}
				else if(circles[currentIndex].color == Color.green && key == KeyEvent.VK_LEFT) {
					++score;
				}
			}
		
	
		currentIndex++;
		if (currentIndex == circles.length) {
			// 모든 공 옮김.
			long EndTime = System.currentTimeMillis();
			JOptionPane.showMessageDialog(null, "당신의 완성 시간은 : " + (EndTime - StartTime)/1000 + "초 입니다! \n 당신의 점수는 : " + score + " 입니다!"  );
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			return;
		}
		
	} 

	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * 공이 어느정도로 움직였는지를 결과값을 작성
	 * @param goal_x 공의 위치를 의미
	 * @param c 어떤 공을 움직이는지를 의미
	 */
	private void moveTo(int goal_x, Circle c) {
		
		Timer timer0 = new javax.swing.Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (c.x < goal_x) {
					c.x += 20;
				} else if (c.x > goal_x) {
					c.x -= 20;
				}

				if (c.x == goal_x) {
					for (Timer timer : timerList) {
						timer.stop();
						timer = null;
						timerList.remove(timer); 
						movingCircles.remove(c);
						System.gc();
					}
				}

				for (int i = currentIndex; i < circles.length; ++i) {
					Circle c2 = circles[i];
					if (c == c2)
						continue;
					c2.y += 10;
				}

				repaint();
			}
		});
		timer0.start();
		timerList.add(timer0);
	}
	/**
	 * 이미지를 생성하는 값
	 */
	public void paint(Graphics g) {
		
		// System.out.println("paint()");
		image = createImage(getWidth(), getHeight());
		paintComponent(image.getGraphics());
		g.drawImage(image, 0, 0, this);

	}
	
	/**
	 *  공을 생성, 어떤 공이 왼쪽 오른쪽 가야하는지를 생성
	 *  @param g 그래픽을 의미
	 */
	public void paintComponent(Graphics g) {
		
		
		for (int i = 0; i < circles.length; ++i) {
			Circle c = circles[i];
			g.setColor(c.color);
			g.fillOval(c.x, c.y, c.width, c.height);
		}
		g.setColor(Color.red);
		g.fillOval(550, 500, 100, 100);
		
		
		g.setColor(Color.green);
		g.fillOval(150, 500, 100, 100);
		repaint();
		
		

		
	}
		

	public static void main(String[] args) {
		new pinpon();
	}
}

/*
 * public class pinpon extends JFrame {
 * 
 * private Image image; private Graphics graph; int x, y; String circle[] = new
 * String[7]; private Timer timer;
 * 
 * private void moveTo(int goal_x) { timer = new javax.swing.Timer(1, new
 * ActionListener() {
 * 
 * @Override public void actionPerformed(ActionEvent e) { if(x < goal_x) { x = x
 * + 10; } else if(x > goal_x) { x = x - 10; }
 * 
 * if(x == goal_x) { if(timer != null) timer.stop();
 * 
 * } repaint(); } }); timer.start();
 * 
 * 
 * 
 * 
 * }
 * 
 * public class klavye extends KeyAdapter { public void keyPressed(KeyEvent e) {
 * int key = e.getKeyCode(); if (key == e.VK_LEFT) moveTo(x - 200); if (key ==
 * e.VK_RIGHT) moveTo(x + 200); }
 * 
 * public void keyReleased(KeyEvent e) { }
 * 
 * }
 * 
 * public pinpon() { addKeyListener(new klavye());
 * 
 * setSize(800, 800); setResizable(false); setVisible(true);
 * setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); x = 350; y = 360; }
 * 
 * public void paint(Graphics g) { image = createImage(getWidth(), getHeight());
 * paintComponent(image.getGraphics()); g.drawImage(image, 0, 0, this);
 * 
 * }
 * 
 * public void paintComponent(Graphics g) { for(int i = 1 ; i < 7; ++i) {
 * g.fillOval(x, y, 100, 100); } repaint(); }
 * 
 * public static void main(String[] args) { new pinpon(); }
 * 
 * }
 */