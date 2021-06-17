package miniGame.tetris;

import java.awt.Color;
/**
 * 
 * @author ChoiMunKyoung
 *
 */

public class ITetromino extends Tetromino {
	public ITetromino() {
		
		
			this.Tetrominos =  new int[][][]{
				{
					{0, 0,	0,	0},
					{1,	1,	1,	1},
				},
				{
					{1},
					{1},
					{1},
					{1},
				}
			};
			
		

	
	x = 3;	
	y = 0;
	r = 0;
	color =new Color(2,218,218);
	wid = new int []{4,1};
	hei = new int []{2,4};
	
	type = 2;
	
	}
	
}
