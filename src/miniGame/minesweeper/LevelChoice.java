package miniGame.minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import miniGame.MiniGame;

/**
 * 
 * @author miri
 * 난이도 선택 화면
 *
 */
public class LevelChoice extends JPanel implements MiniGame,ActionListener{
	private MainPanel_MineSweeper parents;
	private final String EASY = "쉬움 (9X9)"; // 폭탄 10개
	private final String BASIC = "보통 (16X16)"; // 폭탄 40개
	private final String HARD = "어려움 (30X16)"; // 폭탄 100개
	
	/**
	 * 메인 화면(패널)
	 * @param parents 이전 화면에서 해당 패널을 불러올 때 연동하기 위해 사용
	 */
	LevelChoice(MainPanel_MineSweeper parents){
		super();
		this.parents = parents;
		
		setPreferredSize(new Dimension(ROW,COL));
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		add(setEasyButton());
		add(setBasicButton());
		add(setHardButton());
		
	}
	
	/**
	 * 난이도 쉬움 버튼 만들기
	 * 버튼 글씨, 위치 등 세팅 후에 액션리스너 추가
	 * @return 난이도 쉬움 버튼
	 */
	private JButton setEasyButton() {
		JButton easy = new JButton(EASY);
		
		easy.setFont(new Font(Font.SANS_SERIF,Font.BOLD,45));
		easy.setBackground(new Color(135, 135, 135));
		easy.setBounds(200, 100, 400, 150);
		easy.setBorderPainted(true);
		easy.addActionListener(this);
		
		return easy;
	}
	
	/**
	 * 난이도 보통 버튼 만들기
	 * 버튼 글씨, 위치 등 세팅 후에 액션리스너 추가
	 * @return 난이도 보통 버튼
	 */
	private JButton setBasicButton() {
		JButton basic = new JButton(BASIC);
		
		basic.setFont(new Font(Font.SANS_SERIF,Font.BOLD,45));
		basic.setBackground(new Color(135, 135, 135));
		basic.setBounds(200, 300, 400, 150);
		basic.setBorderPainted(true);
		basic.addActionListener(this);
		
		return basic;
	}
	
	/**
	 * 난이도 어려움 버튼 만들기
	 * 버튼 글씨, 위치 등 세팅 후에 액션리스너 추가
	 * @return 난이도 어려움 버튼
	 */
	private JButton setHardButton() {
		JButton easy = new JButton(HARD);
		
		easy.setFont(new Font(Font.SANS_SERIF,Font.BOLD,45));
		easy.setBackground(new Color(135, 135, 135));
		easy.setBounds(200, 500, 400, 150);
		easy.setBorderPainted(true);
		easy.addActionListener(this);
		
		return easy;
	}
	
	/**
	 * switch문을 이용하여 난이도 별로 행, 열, 총 폭탄 수를 지정한다
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton click = (JButton)e.getSource();
		switch(click.getText()) {
		case EASY : // 9 X 9  // 폭탄 10개
			parents.addNewPanel(new MineSweeper(parents, 9, 9, 10));
			parents.editFrameTitle(EASY);
			break;
		
		case BASIC : // 16 X 16 // 폭탄40개
			parents.addNewPanel(new MineSweeper(parents, 16, 16, 40));
			parents.editFrameTitle(BASIC);
			break;
		
		case HARD : // 30 X 16 // 폭탄 100개
			parents.addNewPanel(new MineSweeper(parents, 30, 16, 100));
			parents.editFrameTitle(HARD);
			break;
			
		}
		
	}

	
}
