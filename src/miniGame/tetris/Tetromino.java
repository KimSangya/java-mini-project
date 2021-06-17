package miniGame.tetris;

import java.awt.Color;

/**
 * 블럭들의 부모 클래스
 *
 */
public class Tetromino {

	void Tetromino(int[][][] Tetrominos) {
		this.Tetrominos = Tetrominos;
	}

	int[][][] Tetrominos;
	/**
	 * 시작 좌표값
	 */
	int x; // 시작 좌표값
	/**
	 * 시작 좌표값
	 */
	int y; // 시작 좌표값
	/**
	 * 현재 모양
	 */
	int r; // 모양
	/**
	 * 블럭의 색
	 */
	Color color;
	/**
	 * 가능한 모양 개수
	 */
	int type; // 모양의 개수
	
	/**
	 * 블럭의 너비
	 */
	int[] wid; // 블럭의 너비
	/**
	 * 블럭의 높이
	 */
	int[] hei; // 블럭의 높이

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getwid() {
		return wid[r];
	}

	public int gethei() {
		return hei[r];
	}

	public int getr() {
		return r;
	}

	public Color getcolor() {
		return color;
	}

	/**
	 * r 값에대한 블럭을 리턴
	 */
	int[][] getBlock() {// r 모양의 블럭 가져오기
		return Tetrominos[r];
	}

}
