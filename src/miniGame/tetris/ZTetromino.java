package miniGame.tetris;

import java.awt.Color;

public class ZTetromino extends Tetromino {
	public ZTetromino() {
		this.Tetrominos =  new int [][][]{
			{
				{0,	1, 1},
				{1,	1, 0}
			},
			{
				{1,	0},
				{1,	1},
				{0,	1}
			},
			
		};
	x = 4;
	y = 0;
	r = 0;
	color = new Color(239,71,71);
	
	wid = new int[]{3,2};
	hei = new int[]{2,3};
	type = 2;
	}

}
