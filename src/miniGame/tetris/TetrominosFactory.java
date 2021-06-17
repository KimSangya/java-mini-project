package miniGame.tetris;


public class TetrominosFactory {
	
	/**
	 * 랜덤한 정수를 받아서 랜덤한 블럭 리턴
	 * @return
	 */
	public static Tetromino create () {
		
		switch ( (int)(Math.random()*7 + 1) ) {
		case 1:
			return new OTetromino();
			
		case 2:
			return new JTetromino();
		
		case 3:
			return new ITetromino();
			
		case 4:
			return new STetromino();
			
		case 5:
			return new ZTetromino();
			
		case 6:
			return new LTetromino();
				
		case 7:
			return new TTetromino();
			
		default:
			return new TTetromino();
			
		}
		
		
		
	}
	

}
