package miniGame.game_2048;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * @author 김건호
 * 	
 * 키보드 입력이 맞는지 안맞는지를 설정해주고 최대값을 설정해준곳입니다.
 */
public class Keyboard implements KeyListener{
	

	public static boolean[] keys = new boolean[120];
	public static boolean[] lastKeys = new boolean[120];
	
	
	/**
	 * 움직임을 최대로 지정해줄수있는 값을 조정.
	 */
	public void update() {
		for(int i = 0; i < keys.length; i++) {
			lastKeys[i] = keys[i];
		}
	}
	
	/**
	 * 키보드의 입력값을 나타내주는 곳입니다.
	 * @param key = 키보드 값
	 * @return 키보드의 움직임의 값을 리턴해줍니다.
	 */
	public static boolean key(int key) {
		return keys[key];
	}
	/**
	 * 키보드의 아래를 입력했을때 입력값을 나타내주는 곳입니다.
	 * @param key = 키보드 값
	 * @return 키보드의 움직임의 값을 리턴해줍니다.
	 */
	public static boolean keyDown(int key) {
		return keys[key] && !lastKeys[key];
	}
	/**
	 * 키보드의 위를 입력했을때 입력값을 나타내주는 곳입니다.
	 * @param key = 키보드 값
	 * @return 키보드의 움직임의 값을 리턴해줍니다.
	 */
	public static boolean keyUp(int key) {
		return !keys[key] && lastKeys[key];
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	/**
	 * 키를 입력했을때 True값으로 변경해주는 값
	 */
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	/**
	 * 키를 누르지 않고 있을때 False값으로 변경해주는 값
	 */
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}	
}
