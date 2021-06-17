package miniGame.tetris;

import java.awt.Color;
/**
 * 
 * @author ChoiMunKyoung
 *
 */

public class STetromino extends Tetromino {
	
	public STetromino() {
		
	
		this.Tetrominos =  new int [][][] {
			{
				{1,	1, 0},
				{0,	1, 1}
			},
			{
				{0,	1},
				{1,	1},
				{1,	0}
			}
			
		};
	x = 4;
	y = 0;
	r = 0;
	color = new Color(2,218,2);
	
	wid = new int[]{3,2};
	hei = new int[]{2,3};
	type = 2;
	
	}
	

}