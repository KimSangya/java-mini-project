package miniGame.tetris;

import java.awt.Color;
/**
 * 
 * @author ChoiMunKyoung
 *
 */

public class OTetromino extends Tetromino{
	public OTetromino() {
	
		this.Tetrominos =  new int [][][] {
		{
			{1,	1},
			{1,	1}
		},
		{
			
			{1,	1},
			{1,	1}
		}
		};
	x = 4;
	y = 0;
	r = 0;
	color =  new Color(218,218,2);
	wid = new int[]{2,2};
	hei = new int[]{2,2};
	type = 2;
	}

}
