package miniGame.game_2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 
 * 게임의 랜덤한 값들을 지정 및 움직이는 값들을 지정한곳입니다.
 *
 */
public class Game {

	public static List<GameObject> objects;
	
	public static boolean moving = false, hasMoved = true, somethingIsMoving = false;
	public static int dir = 0;
	
	private Random rand = new Random();
	
	public Game() {
		init();
	}
	/**
	 * 움직이는 숫자들을 나오게 하는 값들입니다.
	 */
	public void init() {
		objects = new ArrayList<GameObject>();
		moving = false;
		hasMoved = true;
		somethingIsMoving = false;
		spawn();
	}
	/**
	 * 값들이 마음에 안들때 다시 값들을 랜덤하게 바꿔주는 키 값입니다.
	 */
	public void update() {
		if(Keyboard.keyUp(KeyEvent.VK_R)) {
			init();
		}
		
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).update();
		}
		
		checkForValueIncrease();
		
		movingLogic();
	}
	
	/**
	 * 값들의 증가를 확인해주고, 만약 키보드의 움직임의 의해서 그 칸들이 더해졌다면 밀리는 칸을 지우고 더해지는 쪽을 계산해주는 메서드입니다.
	 */
	private void checkForValueIncrease() {
		for(int i = 0; i < objects.size(); i++) {
			for(int j = 0; j < objects.size(); j++) {
				if(i == j) continue;
				if(objects.get(i).x == objects.get(j).x && objects.get(i).y == objects.get(j).y && !objects.get(i).remove && !objects.get(j).remove) {
					objects.get(j).remove = true;
					objects.get(i).value *= 2;
					objects.get(i).createSprite();
				}
			}
		}
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).remove) objects.remove(i);
		}
		System.out.println(objects.size());
	}
	/**
	 * 16개의 칸들을 다시 지정해준 값입니다.
	 */
	private void spawn() {
		if(objects.size() == 16) return;
		
		boolean available = false;
		int x = 0, y = 0;
		while(!available) {
			x = rand.nextInt(4);
			y = rand.nextInt(4);
			boolean isAvailable = true;
			for(int i = 0 ; i < objects.size(); i++) {
				if(objects.get(i).x / 100 == x && objects.get(i).y / 100 == y) {
					isAvailable = false;
				}
			}
			if(isAvailable) available = true;
		}
		objects.add(new GameObject(x * 100, y * 100));
	}
	/** 키보드의 움직이는 값들을 지정해준곳입니다.
	 * 
	 */
	private void movingLogic() {
		somethingIsMoving = false;
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).moving) {
				somethingIsMoving = true;
			}
		}
		if(!somethingIsMoving) {
			moving = false;
			for(int i = 0; i < objects.size(); i++) {
				objects.get(i).hasMoved = false;
			}
		}
		if(!moving && hasMoved) {
			spawn();
			hasMoved = false;
		}
		if(!moving && !hasMoved) {
			if(Keyboard.keyDown(KeyEvent.VK_LEFT)) {
				hasMoved = true;
				moving = true;
				dir = 0;
			}else if(Keyboard.keyDown(KeyEvent.VK_RIGHT)) {
				hasMoved = true;
				moving = true;
				dir = 1;
			}else if(Keyboard.keyDown(KeyEvent.VK_UP)) {
				hasMoved = true;
				moving = true;
				dir = 2;
			}else if(Keyboard.keyDown(KeyEvent.VK_DOWN)) {
				hasMoved = true;
				moving = true;
				dir = 3;
			}
		}
	}
	/**
	 *  움직이는 크기들을 설정 해준 값입니다.
	 */
	public void render() {
		Renderer.renderBackground();

		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).render();
		}
		
		for(int i = 0 ; i < Main.pixels.length; i++) {
			Main.pixels[i] = Renderer.pixels[i];
		}
	}
	
	/**
	 * 
	 * @param g 그래픽의 메인 화면 값을 입력한 값입니다.
	 */
	public void renderText(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("Verdana", 0, 100));
		g.setColor(Color.BLACK);
		
		for(int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			String s = o.value + "";
			int sw = (int) (g.getFontMetrics().stringWidth(s) / 2 / Main.scale);
			g.drawString(s, (int) (o.x + o.width / 2 - sw) * Main.scale, (int) (o.y + o.height / 2 + 18) * Main.scale);
		}
		
	}	
}