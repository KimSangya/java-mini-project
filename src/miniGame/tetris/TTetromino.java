package miniGame.tetris;

import java.awt.Color;

public class TTetromino extends Tetromino {
	public TTetromino() {
		this.Tetrominos =  new int [][][]{
			{
				
				{0,	1, 0},
				{1,	1, 1},
			},
			{
				
				{1, 0},
				{1, 1},
				{1, 0}
			},
			{
				
				{1,	1, 1},
				{0,	1, 0}
			},
			{
				
				{0,	1},
				{1,	1},
				{0,	1}
			}
		};
	x = 4;
	y = 0;
	r =  0;
	color =new Color(218,68,230);
	
	wid = new int[]{3,2,3,2};
	hei = new int[]{2,3,2,3};
	type = 4;
	}

}
