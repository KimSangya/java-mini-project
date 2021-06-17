package miniGame.game_2048;

/**
 * 칸의 픽셀의 밑변과 높이를 지정하고 게임 크기에 맞는 판의 색값들을 지정한값입니다.
 *
 */
public class Renderer {
	
	public static int width = Main.WIDTH, height = Main.HEIGHT;
	public static int[] pixels = new int[width * height];
	
	
	public static void renderBackground() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				pixels[x + y * width] = 0xfff4f4f4;
				
				if(x % 100 < 3 || x % 100 > 97 || y % 100 < 3 || y % 100 > 97) {
					pixels[x + y * width] = 0xffcccccc;
				}
			}
		}
	}
	/**
	 * 칸이 움직이면서 움직이던 칸에서 다시 아무것도 없는 칸이 될 경우 원래의 색으로 돌리는 메서드입니다.
	 * @param sprite
	 * @param xp
	 * @param yp
	 */
	public static void renderSprite(Sprite sprite, int xp, int yp) {
		if(xp < -sprite.width || xp > width || yp < -sprite.height || yp > height) return;
		
		for(int y = 0; y < sprite.height; y++) {
			int yy = y + yp;
			if(yy < 0 || yy > height) continue;
			for(int x = 0; x < sprite.width; x++) {
				int xx = x + xp;
				if(xx < 0 || xx > width) continue;
				int col = sprite.pixels[x + y * sprite.width];
				if(col == 0xffff00ff) continue;
				else pixels[xx + yy * width] = col;
			}
		}
		
	}
	
}