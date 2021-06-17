package miniGame.game_2048;

import java.util.Random;

/**
 * 
 * @author 김건호
 *	게임 블럭들의 스피드, 블럭의 값, 색, 블럭의 랜덤으로 나오는 값을 생성해준 곳입니다.
 */
public class GameObject {
	public double x, y;
	public int width, height;
	public Sprite sprite;
	public int value, speed = 8;
	public boolean moving = false, remove = false, hasMoved = false;
	
	Random rand = new Random();
	
	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
		this.value = (rand.nextBoolean() ? 2 : 4);
		createSprite();
		this.width = sprite.width;
		this.height = sprite.height;
	}
	
	public void createSprite() {
		/**
		 * 	@value 2,4,8...등의 색깔 및 더한값들 생성
		 */
		if(this.value == 2) {
			this.sprite = new Sprite(100, 100, 0xefe5db);
		}else if(this.value == 4) {
			this.sprite = new Sprite(100, 100, 0xece0c8);
		}else if(this.value == 8) {
			this.sprite = new Sprite(100, 100, 0xf1b078);
		}else if(this.value == 16) {
			this.sprite = new Sprite(100, 100, 0xEB8C52);
		}else if(this.value == 32) {
			this.sprite = new Sprite(100, 100, 0xF57C5F);
		}else if(this.value == 64) {
			this.sprite = new Sprite(100, 100, 0xEC563D);
		}else if(this.value == 128) {
			this.sprite = new Sprite(100, 100, 0xF2D86A);
		}else if(this.value == 256) {
			this.sprite = new Sprite(100, 100, 0xECC750);
		}else if(this.value == 512) {
			this.sprite = new Sprite(100, 100, 0xE5BF2D);
		}else if(this.value == 1024) {
			this.sprite = new Sprite(100, 100, 0xE2B913);
		}else if(this.value == 2048) {
			this.sprite = new Sprite(100, 100, 0xEDC22E);
		}else if(this.value == 4096) {
			this.sprite = new Sprite(100, 100, 0x5DDB92);
		}else if(this.value == 8192) {
			this.sprite = new Sprite(100, 100, 0xEC4D58);
		}
	}
	/**
	 * 블럭값들이 어느정도 움직일수있는지를 설정해준 값입니다.
	 * @return false
	 */
	public boolean canMove() {
		if(x < 0 || x + width > Main.WIDTH || y < 0 || y + height > Main.HEIGHT) {
			return false;
		}
		for(int i = 0; i < Game.objects.size(); i++) {
			GameObject o = Game.objects.get(i);
			if(this == o) continue;
			if(x + width > o.x && x < o.x + o.width && y + height > o.y && y < o.y + o.height && value != o.value) {
				return false;
			}
		}
		return true;
	}
	/**
	 *  블럭의 위치가 옆에 얼마나 떨어져있는지에 대해서 키보드를 입력했을때 똑같은 속도로 움직이게 하기 위해 지정해준 값들
	 */
	public void update() {
		if(Game.moving) {
			if(!hasMoved) {
				hasMoved = true;
			}
			if(canMove()) {
				moving = true;
			}
			
			if(moving) {
				if(Game.dir == 0) x -= speed;
				if(Game.dir == 1) x += speed;
				if(Game.dir == 2) y -= speed;
				if(Game.dir == 3) y += speed;
			}
			if(!canMove()) {
				moving = false;
				x = Math.round(x / 100) * 100;
				y = Math.round(y / 100) * 100;
			}
		}
	
	}
	
	public void render() {
		Renderer.renderSprite(sprite, (int) x, (int) y);
	}
}

