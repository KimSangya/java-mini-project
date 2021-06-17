package miniGame.snake;

import java.awt.Color;

public interface SNAKEGAMEINTERFACE {
	int PANELHEIGHT = 800;
	int PANELWIDTH = 800;
	
	int GAMEHEIGHT = 50;
	int GAMEWIDTH = 50;
	
	int EMPTY = 0;
	int BODY = 1;
	int APPLE = 2;
	
	int UP = 1;
	int DOWN =2;
	int LEFT = 3;
	int RIGHT = 4;
	
	
	Color BORADCOLOR = Color.black;
	Color APPLECOLOR = Color.red;
	Color SNAKECOLOR = Color.green;

}
