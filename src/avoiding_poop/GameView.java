package avoiding_poop;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameView extends JFrame {
	int level = 0;
	String[] levelComboStr = {"하수", "중수","고수"};
	JComboBox<String> levelCombo = new JComboBox<String>(levelComboStr);
	Poop[] poop = new Poop[level]; 
	JLabel[] label_poop = new JLabel[poop.length];
	Diamond[] diamonds = new Diamond[poop.length / 2];
	PoopThread pThread = null;
	DiamondThread dThread = null;
	JLabel[] label_dia = new JLabel[diamonds.length];
	JLabel label_char = new JLabel(new ImageIcon("image/사람.png"));
	JLabel scoreLbl = new JLabel("점수: 1000점");
	JButton start_button = new JButton("스타또");
	String[] comboCharStr = {"사람", "변기", "펭귄", "옴팡이", "라이언"};
	JComboBox<String> comboChar = new JComboBox<String>(comboCharStr);
	String[] comboHailStr = {"폭탄", "골룸", "노랑악마", "오렌지악마"};
	JComboBox<String> comboPoop = new JComboBox<String>(comboHailStr);
	int score = 1000;
	
	public GameView() {
		setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		scoreLbl.setBounds(1100, 20, 100, 30);
		comboChar.setBounds(1030, 20, 60, 30);
		comboChar.addItemListener(comboL);
		comboPoop.setBounds(960, 20, 60, 30);
		comboPoop.addItemListener(comboL);
		levelCombo.setBounds(780, 20, 60, 30);
		levelCombo.addItemListener(comboL);
		start_button.setBounds(860, 20, 80, 30);
		start_button.addActionListener(buttonL);
		label_char.setBounds(550, 450, 80, 120);
		add(levelCombo);
		add(label_char);
		addKeyListener(keyL);
		add(start_button);
		add(scoreLbl);
		add(comboChar);
		add(comboPoop);
		
		setTitle("똥 피하기");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(10, 10, 1200, 600);
		setVisible(true);
//		setResizable(false);
		setFocusable(true);
		requestFocus();
	}
	
	public void changeScore() {
		for (int i = 0; i < label_poop.length; i++) {				
			if(label_char.getX() >= label_poop[i].getX() && label_char.getX() <= label_poop[i].getX()+label_poop[i].getWidth()) {
				if(label_char.getY() >= label_poop[i].getY() && label_char.getY() <= label_poop[i].getY()+label_poop[i].getHeight()) {
					if(score > poop[i].getPoint()) {
						score -= poop[i].getPoint();
						scoreLbl.setText("점수: "+score+"점");
					}
				}
			}
		}
		
		for (int i = 0; i < label_dia.length; i++) {				
			if(label_char.getX() >= label_dia[i].getX() && label_char.getX() <= label_dia[i].getX()+label_dia[i].getWidth()) {
				if(label_char.getY() >= label_dia[i].getY() && label_char.getY() <= label_dia[i].getY()+label_dia[i].getHeight()) {
					if(score <= 980) {
						score += diamonds[i].getPoint();
						scoreLbl.setText("점수: "+score+"점");
					}
				}
			}
		}
	}
	
	ActionListener buttonL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "스타또") {
				Random random = new Random();
//				똥 객체를 생성해서 배열에 저장
				for (int i = 0; i < poop.length; i++) {
					poop[i] = new Poop();
					poop[i].setX(i * 70);
					poop[i].setY(i * random.nextInt(70));
					poop[i].setW(75);
					poop[i].setH(60);
					poop[i].setPoopImage("image/똥.png");
					poop[i].setPoint(10);
					label_poop[i] = new JLabel(new ImageIcon(poop[i].getPoopImage()));
					label_poop[i].setBounds(poop[i].getX(), poop[i].getY(), poop[i].getW(), poop[i].getH());
					add(label_poop[i]);
					pThread = new PoopThread(label_poop[i], poop[i]);
					pThread.start();
				}
				
//				10개의 다이아몬드 객체를 생성해서 배열에 저장
				for (int i = 0; i < diamonds.length; i++) {
					diamonds[i] = new Diamond();
					diamonds[i].setX(i * 140 + random.nextInt(70));
					diamonds[i].setY(i * random.nextInt(30));
					diamonds[i].setW(70);
					diamonds[i].setH(50);
					diamonds[i].setDiaimage("image/보석.png");
					diamonds[i].setPoint(20);
					label_dia[i] = new JLabel(new ImageIcon(diamonds[i].getDiaimage()));
					label_dia[i].setBounds(diamonds[i].getX(), diamonds[i].getY(), diamonds[i].getW(), diamonds[i].getH());
					add(label_dia[i]);
					dThread = new DiamondThread(label_dia[i], diamonds[i]);
					dThread.start();
				}
			}
		}
	};
	
	ItemListener comboL = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			ImageIcon icon = null;
			String imgName = null;
			int level  =  0;
			if(e.getSource() == comboChar) {
				switch (comboChar.getSelectedIndex()) {
				case 0:
					imgName = "사람";
					break;
				case 1:
					imgName = "변기";
					break;
				case 2:
					imgName = "penguin";
					break;
				case 3:
					imgName = "ompang";
					break;
				case 4:
					imgName = "lion";
					break;
				}
				icon = new ImageIcon("image/" + imgName + ".png");
				label_char.setIcon(icon);
				
			}else if(e.getSource() == comboPoop) {
				switch (comboPoop.getSelectedIndex()) {
				case 0:
					imgName = "사람";
					break;
				case 1:
					imgName = "변기";
					break;
				case 2:
					imgName = "devil1.png";
					break;
				case 3:
					imgName = "devil2.png";
					break;
				}
				icon = new ImageIcon("image/" + imgName + ".png");
				for (int j = 0; j < label_poop.length; j++) {
					label_poop[j].setIcon(icon);
				}
			}else if(e.getSource() == levelComboStr) {
				switch(levelCombo.getSelectedIndex()) {
				case 0 : //하수
					level = 5;
					break;
				case 1 : //중수
					level = 20;
					break;
				case 2 : //고수
					level = 30;
					break;
				}
			}
			GameView.this.setFocusable(true);
			GameView.this.requestFocus();
		}
	};
	
	KeyAdapter keyL = new KeyAdapter() {
		
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: 
				if(label_char.getX() > 0)
					label_char.setLocation(label_char.getX()-10, label_char.getY());
				break;
			case KeyEvent.VK_RIGHT: 
				if(label_char.getX() < 1200-label_char.getWidth())
					label_char.setLocation(label_char.getX()+10, label_char.getY());
				break;
			}
			changeScore();
		}
	};
	
	public class PoopThread extends Thread{
		JLabel label_poop;
		Poop poop;
		
		public PoopThread(JLabel label_poop, Poop poop) {
			this.label_poop = label_poop;
			this.poop = poop;
		}
		
		@Override
		public void run() {
			while (true) {
				Random random = new Random();
				if( label_poop.getY() <= 600)
					label_poop.setLocation(label_poop.getX(), label_poop.getY() + 10);
				else {
					label_poop.setLocation(label_poop.getX(), random.nextInt(70));
				}
				//changeScore();
				try {
					sleep(20 * random.nextInt(100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public class DiamondThread extends Thread{
		JLabel label_dia;
		Diamond diamond;
		
		public DiamondThread(JLabel diamondLbl, Diamond diamond) {
			this.label_dia = diamondLbl;
			this.diamond = diamond;
		}
		
		@Override
		public void run() {
			while (true) {
				Random random = new Random();
				if( label_dia.getY() <= 600)
					label_dia.setLocation(label_dia.getX(), label_dia.getY() + 10);
				else {
					label_dia.setLocation(label_dia.getX(), random.nextInt(70));
				}
				try {
					sleep(30 * random.nextInt(50));
				} catch (InterruptedException e) {
	
				}
			}
		}
	}
	public static void main(String args[]) {
		new GameView();
	}
}