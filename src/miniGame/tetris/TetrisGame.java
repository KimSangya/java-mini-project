package miniGame.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
/**
 * 
 * @author ChoiMunKyoung
 *
 */
/**
 * 쓰래드를 사용해서 정해진 시간마다 게임 속도 증가
 * @author ChoiMunKyoung
 *
 */
class Tick extends Thread {
	
	private int INTERVAL = 15; // 15초 단위로 속도 증가
	private TetrisGame parent;
	private long startTime;
	
	private int previousSec;
	private int currentSec = 1;
	
	
	public Tick(TetrisGame parent) {
		this.parent = parent;
	}
	
	@Override
	public void run() {
		// 15 초 단위로 속도 20퍼씩 
		startTime = System.currentTimeMillis();
		
		while( true ) {
			
			currentSec = (int) ((System.currentTimeMillis() - startTime) / (4000 * INTERVAL) );  
			if(currentSec > previousSec) {
				System.out.println(currentSec + "0초");
				previousSec = currentSec;
				parent.rapidVelocity();
			}
			
			
		}
		
	}
}

public class TetrisGame extends JPanel implements KeyListener, BlockStatus {

	private int check;
	private boolean gameOver = false;
	int line;
	Timer timer;
	JPanel board;
	JPanel gamePanel;
	MainPanel_Tetris parents;
	JTextArea scoreboard;
	
	/**
	 * 처음 게임 속도
	 */
	private int ms = 500;
	
	int score;
	
	private boolean isreach = false;
	/**
	 * status 배열에 맞춰서 색이 들어갈 패널들
	 */
	private JPanel[][] panels = new JPanel[PANELHEIGHT][PANELWIDTH]; // panel
	/**
	  값이 계속 입력될 배열
	 */
	private int[][] status = new int[PANELHEIGHT][PANELWIDTH]; // status

	Tetromino randBlock;
	/**
	 * 블럭의 현재 행과 열
	 */
	private int currentRow, currentCol; 
	
	/**
	 * 생성된 랜덤한 블럭의 모양
	 */
	private int[][] currentTetrominos;

	
	

	public TetrisGame(MainPanel_Tetris parents) {
		this.parents = parents;
		
		randBlock = TetrominosFactory.create();// 랜덤 블록 생성
		currentRow = randBlock.getY();
		currentCol = randBlock.getX();
		currentTetrominos = randBlock.getBlock();
		scoreboard = new JTextArea();
		scoreboard.setPreferredSize(new Dimension(100,200));
		
		
		gamePanel = new JPanel();
		board = new JPanel();
		board.add(scoreboard);
		
		gamePanel.setPreferredSize(new Dimension(400,800));
		board.setPreferredSize(new Dimension(400,800));

		gamePanel.setLayout(new GridLayout(PANELHEIGHT, PANELWIDTH, 2, 2));

		for (int i = 0; i < PANELHEIGHT; ++i) {

			for (int j = 0; j < PANELWIDTH; ++j) {

				panels[i][j] = new JPanel();

				gamePanel.add(panels[i][j]);

			}

		} // 메인패널 초기화 후 색 설정 배널 붙이기
		setLayout(new BorderLayout());
		add(gamePanel,BorderLayout.WEST);
		add(board,BorderLayout.EAST);
		parents.frame.addKeyListener(this);
		setSize(new Dimension(800, 800));
		setVisible(true);
		resetTimer(ms);
		new Tick(this).start();
	}
	
	
	/**
	 * 타이머 주기에들어갈 delay값을 지속적으로 감소
	 */
	public void rapidVelocity() {
		ms /= 5;
		resetTimer(ms);
	}
	
	public void resetTimer(int ms) {
		if(timer != null) {
			timer.stop();
		}
		
		timer = new Timer(ms, new ActionListener() {

			@Override
			/**
			 * 		받아온 ms밀리초마다
			 * 		redraw()
					falling()
					isreach()
					isreach_a()
					makeStop()
					removeBlock()
					spawnNewBlock()
					gameOver()
					반복
			 * 
			 */
			public void actionPerformed(ActionEvent e) {
				if (gameOver == true) {
					timer.stop();
				}

				// 패널 다시 그리기
				if (isreach == false) {

					redraw();

					falling();
					// redraw();

					isreach();
					if (currentRow + randBlock.gethei() < PANELHEIGHT) {
						isreach_a();
					}

				} else {

					redraw();

					makeStop();

					removeBlock();

					spawnNewBlock();

					gameOver();
				}

			}

		});

		timer.start();
	}
	

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	/**
	 * 좌우 방향키에 맞춰서 좌우로 이동시키는 메서드 실행
	 * 
	 * 위키는 rotate() 메서드 실행
	 * 
	 * 아래키는 닿을떄까지 
	 * falling()
		isreach()
		반복
		
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			moveLeft();
			redraw();

			break;
		case KeyEvent.VK_RIGHT:
			moveRight();
			redraw();
			break;
		case KeyEvent.VK_UP:
			rotate();
			break;
		case KeyEvent.VK_DOWN:

			while (isreach != true) {

				falling();

				isreach();

				if (currentRow + randBlock.gethei() < PANELHEIGHT) {

					isreach_a();
				}
				redraw();
			}

		default:
			break;
		}
		redraw();
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	/**
	 * 멈춘블록이나 배열의 끝이아니라면 현재 행값을 감소시킴
	 */
	void moveLeft() {
		if (currentCol > 0 && status[currentRow][currentCol - 1] != STOPPEDBLOCK)
			--currentCol;
	}
	/**
	 * 멈춘블록이나 배열의 끝이 아니라면 현재 높이값을 감소시킴
	 */
	void moveRight() {
		if (currentCol + randBlock.getwid() < PANELWIDTH
				&& status[currentRow][currentCol + randBlock.getwid()] != STOPPEDBLOCK)
			++currentCol;
	}
	
	/**
	 * 생성된 블럭의 r 값을 증가시켜 돌아간 모양으로 변경
	 */
	void rotate() {
		if (randBlock.r < randBlock.type - 1) {
			randBlock.r++;
			currentTetrominos = randBlock.getBlock();
		} else {
			randBlock.r = 0;
			currentTetrominos = randBlock.getBlock();
		}
	}
	
	/**
	 * 현재 열 값을 증가시켜 블럭이 내려가게 보이게함
	 */
	void falling() {
		if (currentRow < PANELHEIGHT) {
			++currentRow;
		}
	}
	
	/**
	 *	현재 블럭의 좌표를 status배열에서 MOVINGBLOCK 에서 STOPPEDBLOCK 으로 변경
	 */
	void makeStop() {
		for (int i = 0; i < randBlock.gethei(); ++i) {
			for (int j = 0; j < randBlock.getwid(); ++j) {
				try {
					if (status[currentRow + i][currentCol + j] == MOVINGBLOCK) {
						status[currentRow + i][currentCol + j] = STOPPEDBLOCK;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}

			}
		}

	}
	
	/**
	 *  이전의 status 배열의 MOVINGBLOCK값들을 지워주기 위해
	 *  redraw() 할때 먼저 모든 status 의 MOVINGBLOCK 값을 EMPTY 로 변경
	 */
	void clean() {
		for (int i = 0; i < PANELHEIGHT; ++i) {
			for (int j = 0; j < PANELWIDTH; ++j) {
				if (status[i][j] == MOVINGBLOCK) {
					status[i][j] = EMPTY;
				}
			}
		}

	}

	/**
	 *블럭이 바닥에 닿으면 isreach 값을 true로 변경
	 */
	void isreach() {
		if (currentRow + randBlock.gethei() == PANELHEIGHT) {
			isreach = true;
		} else {
			isreach = false;
		}

	}

	/**
	 * randBlock에 새로운 블록 생성및
	 * 현재 값들 초기화
	 * 점수 증가
	 */
	void spawnNewBlock() {
		randBlock = TetrominosFactory.create();// 랜덤 블록 생성
		currentRow = randBlock.getY();
		currentCol = randBlock.getX();
		currentTetrominos = randBlock.getBlock();
		check = 0;
		isreach();
		score += 100;
		redraw();
	}
	
	/**
	 * 블럭이 STOPPED블럭이라면 isreach를 true로 변경
	 */
	void isreach_a() {
		redraw();
		for (int i = 0; i < randBlock.gethei(); ++i) {
			for (int j = 0; j < randBlock.getwid(); ++j) {

				try {
					if (status[currentRow + i][currentCol + j] == MOVINGBLOCK
							&& status[currentRow + i + 1][currentCol + j] == STOPPEDBLOCK) {

						isreach = true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}

			}
		}
		scoreboard.setText(score+"점");;
	}

	/**
	 * check 에 줄마다 값들을 더해 20이라면 그줄의 removeLine()실행
	 * 점수 증가
	 */
	void removeBlock() {
		for (int i = PANELHEIGHT - 1; i > 0; i--) {
			for (int j = 0; j < PANELWIDTH; j++) {
				check += status[i][j];
				if (check == 20) {
					removeLine();
					pullDownBlock(i);
					check = 0;
					removeBlock();
					score += 500;
					
				}

			}
			check = 0;
		}
		

	}

	/**
	 * line에 입력된 줄의 배열을 EMPTY로 변경
	 */
	void removeLine() {
		for (int j = 0; j < PANELWIDTH; j++) {
			status[line][j] = EMPTY;
		}
	}

	/**
	 * 
	 * 지워진 블럭위 배열들을 아래로 내려줌
	 * @param line removeLine의 행값
	 */
	void pullDownBlock(int line) {
		for (int i = line; i > 2; i--) {
			for (int j = 0; j < PANELWIDTH; j++) {
				status[i][j] = status[i - 1][j];
			}
		}

	}
	/**
	 * status의 2번째 열에 STOPPEDBLOCK 이면 
	 * LOSE 점수 출력
	 */
	void gameOver() {
		for (int i = 0; i < PANELWIDTH; i++) {
			if (status[2][i] == STOPPEDBLOCK) {
				gameOver = true;
				JOptionPane.showMessageDialog(gamePanel, "Lose \n 당신의 점수는: " + score);
			}
		}

	}
	/**
	 * 블럭의 배열을 status에 대입하고
	 * 
	 * status 배열에 맞춰서 panels에 색을 설정해준다
	 * 
	 */
	private void redraw() {

		clean();

		for (int i = 0; i < randBlock.gethei(); ++i) {
			for (int j = 0; j < randBlock.getwid(); ++j) {
				try {
					if (status[currentRow + i][currentCol + j] == EMPTY) {
						status[currentRow + i][currentCol + j] = currentTetrominos[i][j];
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}

			}
		}

		for (int i = 0; i < PANELHEIGHT; ++i) {
			for (int j = 0; j < PANELWIDTH; ++j) {

				if (status[i][j] == EMPTY) {
					panels[i][j].setBackground(Color.gray);
				} else if (status[i][j] == MOVINGBLOCK) {
					panels[i][j].setBackground(randBlock.getcolor());
				}
			}
		}

		add(gamePanel);
	}

	public static void main(String[] args) {
	}
}