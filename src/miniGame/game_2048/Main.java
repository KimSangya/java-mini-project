package miniGame.game_2048;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

	/**
	 * @author 김건호
	 * 
	 * 인기 게임인 2048을 JAVA를 이용하여 만들어보았습니다.
	 * @see <a href="https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqbXZCVldrV2dBNEhNS3g3NEpKWXJxNk5MRU1IUXxBQ3Jtc0tuckE1cmN5bmp2bkx4a1NGVXB3ZTRkcnEzcGdKOTY4ZXE1X1A3U1Fxcm44T3hVbzNldTBEOTB5QzM1c210MExPamptX0g2dHhKZnFTUjdYU3RFV2U5cFQ1LW5uZ3FzcmQ0cEdseVRvSndZN3lBVzQ5MA&q=https%3A%2F%2Fprogrammingtoinspire.com%2Fyoutube%2F2048%2Fsrc.zip">원출처</a> 
	 * 
	 */


	/**
	 * 
	 * 메인 페이지의 크기 설정값들 및 프레임 크기를 설정해준곳입니다.
	 *
	 */
public class Main extends Canvas implements Runnable {
	
	public static final int WIDTH = 400, HEIGHT = 400;
	public static int scale = 2;
	
	public JFrame frame;
	public Thread thread;
	public Keyboard key;
	public Game game;
	public boolean running = false;
	
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public Main() {
		setPreferredSize(new Dimension((int) (WIDTH * scale), (int) (HEIGHT * scale)));
		
		game = new Game();	
		key = new Keyboard();
		addKeyListener(key);
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("2048");
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		start();
		
	}
	/**
	 * 스레드가 돌수있도록 만들어준 값입니다.
	 */
	public void start() {	
		running = true;
		thread = new Thread(this, "loopThread");
		thread.start();
	}
	
	public void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  실시간으로 프레임 값이 얼마나 도는지를 보여주는 곳입니다.
	 */
	public void run() {
		long lastTimeInNanoSeconds = System.nanoTime();
		long timer = System.currentTimeMillis();
		double nanoSecondsPerUpdate = 1000000000.0 / 60.0;
		double updatesToPerform = 0.0;
		int frames = 0;
		int updates = 0;		
		requestFocus();
		while(running) {
			long currentTimeInNanoSeconds = System.nanoTime();
			updatesToPerform += (currentTimeInNanoSeconds - lastTimeInNanoSeconds) / nanoSecondsPerUpdate;
			if(updatesToPerform >= 1) {
				update();
				updates++;
				updatesToPerform--;
			}
			lastTimeInNanoSeconds = currentTimeInNanoSeconds;
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				frame.setTitle("2048 " + updates + " updates, " + frames + " frames");
				updates = 0;
				frames = 0;
				timer += 1000;
			}
		}	
	}
	
	/**
	 *  키가 정상 작동하는 것을 설명
	 */
	
	public void update() {
		game.update();
		key.update();
	}
	/**
	 * 게임 프레임의 크기의 칸들을 지정해준 값입니다.
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		game.render();
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.drawImage(image, 0, 0, (int) (WIDTH * scale), (int) (HEIGHT * scale), null);
		game.renderText(g);
		g.dispose();
		bs.show();
	}	
	
	public static void main(String[] args) {
		Main m = new Main();
	}
}
