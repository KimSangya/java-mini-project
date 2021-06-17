package miniGame;

import java.awt.Font;

/**
 * 
 * @author miri
 * 미니 게임에 통용되는 상수 지정
 *
 */
public interface MiniGame {
	int ROW = 800;
	int COL = 800;
	
	
	String BUTTON_START = "게임 시작";
	String BUTTON_RULE = "게임 방법";
	
	String SCORE = "현재 점수 : ";
	String BEST_SCORE = "최고 점수 : ";
	
	Font FONT = new Font(Font.SANS_SERIF,Font.BOLD,20);
	
	String PATH_MINESWEEPER = "image/폭탄아이콘.png";
	String PATH_HAMBURGER = "image/햄버거아이콘.png";
	String PATH_TETRIS = "image/테트리스아이콘.png";
	String PATH_SNAKE = "image/스네이크아이콘.png";
	String PATH_2048 = "image/2048아이콘.png";
	String PATH_PINPON = "image/니편내편아이콘.png";
	String PATH_MINGAME = "image/미니게임천국.png";
	
	
	String TITLE_MINESWEEPER = "지 뢰 찾 기";
	String TITLE_HAMBURGER = "햄버거 만들기";
	String TITLE_TETRIS = "테 트 리 스";
	String TITLE_SNAKE = "스 네 이 크";
	String TITLE_2048 = "2 0 4 8";
	String TITLE_PINPON = "니 편 내 편";
	

}
