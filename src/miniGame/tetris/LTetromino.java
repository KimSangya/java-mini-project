package miniGame.tetris;

import java.awt.Color;
/**
 * 
 * @author ChoiMunKyoung
 *
 */
public class LTetromino extends Tetromino {
	
		public LTetromino() {
			this.Tetrominos =  new int [][][]{
				{
					
					{1, 0},
					{1, 0},
					{1, 1}
				},
				{
					
					{1,	1, 1},
					{1,	0, 0}
				},
				{
					
					{1,	1},
					{0,	1},
					{0,	1}
				},
				{
					
					{0,	0, 1},
					{1,	1, 1},
				}
			};
		x = 4;
		y = 0;
		r = 0;
		color = new Color(255, 166, 0);
		
		wid = new int[]{2,3,2,3};
		hei = new int[]{3,2,3,2};
		type = 4;
		}

	}