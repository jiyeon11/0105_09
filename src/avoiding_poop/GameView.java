package avoiding_poop;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameView extends JFrame{
	Poop[] poop = new Poop[15];
	JLabel[] label_poop = new JLabel[poop.length];
	JLabel label_char = new JLabel(new ImageIcon("image/사람.png"));
	Diamond[] dia = new Diamond[5];
	JLabel[] label_dia = new JLabel[dia.length];
	int score = 50;
	JLabel label_score = new JLabel("점수 : " + score + "점");

	public GameView() {
		setLayout(null);
		setTitle("똥 피하기");
		setBounds(10, 10, 1000, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		Random random = new Random();
		label_score.setBounds(1100,20,70,30);
		add(label_score);
		PoopThread pt;
		DiaThread dt;
		
		//똥
		for(int i = 0; i<poop.length; i++) {
			poop[i] = new Poop();
			poop[i].setX(i*50);
			poop[i].setY(i*random.nextInt(50));
			poop[i].setW(80);
			poop[i].setH(80);
			poop[i].setPoint(10);
			poop[i].setPoopImage("image/똥.png");
			label_poop[i] = new JLabel(new ImageIcon(poop[i].getPoopImage()));
			label_poop[i].setBounds(poop[i].getX(), poop[i].getY(), poop[i].getW(), poop[i].getH());
			add(label_poop[i]);
			pt = new PoopThread(label_poop[i],poop[i]);
			pt.start();
		}
		
		//다이아
		for (int i = 0; i < dia.length; i++) {
			dia[i] = new Diamond();
			dia[i].setX(i * 50 + random.nextInt(500));
			dia[i].setY(i * random.nextInt(30));
			dia[i].setW(60);
			dia[i].setH(70);
			dia[i].setDiaimage("image/보석.png");
			dia[i].setPoint(20);
			label_dia[i] = new JLabel(new ImageIcon(dia[i].getDiaimage()));
			label_dia[i].setBounds(dia[i].getX(), dia[i].getY(), dia[i].getW(), dia[i].getH());
			add(label_dia[i]);
			dt = new DiaThread(label_dia[i], dia[i]);
			dt.start();
		}
		label_char.setBounds(500, 400, 100, 140);  //캐릭터
		add(label_char);
		addKeyListener(keyListener);
		setVisible(true);
	}
	
	KeyAdapter keyListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
//			case KeyEvent.VK_UP:
//				label_char.setLocation(label_char.getX(), label_char.getY()-10);
//				break;
//			case KeyEvent.VK_DOWN:
//				label_char.setLocation(label_char.getX(), label_char.getY()+10);
//				break;
			case KeyEvent.VK_LEFT:
				label_char.setLocation(label_char.getX()-10, label_char.getY());
				break;
			case KeyEvent.VK_RIGHT:
				label_char.setLocation(label_char.getX()+10, label_char.getY());
				break;
			}
		}
	};
	class PoopThread extends Thread{
		JLabel label_poop;
		Poop poop;
		public PoopThread(JLabel label_poop,Poop poop) {
			this.label_poop = label_poop;
			this.poop = poop;
		}
		
		@Override
		public void run() {
			Random random = new Random();
			while(true) {
				if(label_poop.getY() <= 600)
					label_poop.setLocation(label_poop.getX(), label_poop.getY() + 10);
				else {
					label_poop.setLocation(label_poop.getX(), random.nextInt(70));
				}
				try {
					sleep(20 * random.nextInt(50));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public class DiaThread extends Thread{
		JLabel label_dia;
		Diamond dia;
		
		public DiaThread(JLabel label_dia, Diamond dia) {
			this.label_dia = label_dia;
			this.dia = dia;
		}
		@Override
		public void run() {
			while (true) {
				Random random = new Random();
				if( label_dia.getY() <= 600)
					label_dia.setLocation(label_dia.getX(), label_dia.getY() + 15);
				else {
					label_dia.setLocation(label_dia.getX(), random.nextInt(30));
				}
				try {
					sleep(10 * random.nextInt(40));
				} catch (InterruptedException e) {
	
				}
			}
		}
	}
	public static void main(String[] args) {
		new GameView();
	}
}
