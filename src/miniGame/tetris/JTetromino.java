package miniGame.tetris;

import java.awt.Color;
/**
 * 
 * @author ChoiMunKyoung
 *
 */
public class JTetromino extends Tetromino{
	public JTetromino() {
		this.Tetrominos =  new int [][][] {
	{
		
		{0, 1},
		{0, 1},	
		{1, 1}
	},
	{
		
		{1,	0, 0},
		{1,	1, 1}
	},
	{
		
		{1,	1},
		{1,	0},
		{1,	0}
	},
	{
		
		{1,	1, 1},
		{0,	0, 1},
	}
};
	x = 4;
	y = 0;
	r = 0;
	color = new Color(0,85,255);
	wid = new int[]{2,3,2,3};
	hei = new int[]{3,2,3,2};
	type = 4;
	
	
	}
}