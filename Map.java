package game;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.Timer;

import com.martio.game.EndPanel;
import com.martio.game.Game;
import com.martio.game.GameFrame;
import com.martio.game.GamePanel;
import com.martio.game.StartPanel;

import Game2Data.ChooseEnemy;
import Game2Data.ReadEnemyFile;
import game2.src.Game2.Game2;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Map extends JComponent {
	private Polygon[] fields;
    private int enteredFieldIndex = -1, selectedFieldIndex = -1;
    
    private Image spinImage, pointer; // 轉盤 指針
    private Image yourTurn, notice; // 箭頭 關卡介紹
    public static int whoseTurn = 1;
    private int angle = 0, RandomAngle;
    private JLabel guesslabel, guessnum;
    private JTextField textField;
    private JButton button;
    private Timer timer; // 轉盤

    private Tree[] trees;
    public static Player p1, p2;

    private Thread callout_thread;
    
    Map() {
    	try {
        	yourTurn = ImageIO.read(new File("src/Resources/yourturn.png"));
        	notice = ImageIO.read(new File("src/Resources/notice.png"));
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
//    	-------------------------------------------------------------------------------------
    	/* 建土地 */
        fields = new Polygon[16];
        for (int j = 0; j < 4; j++) { // 上排
            int i = j;
            fields[j] = new Polygon(new int[]{325 + 100 * i, 425 + 100 * i, 410 + 110 * i, 300 + 110 * i},
                                    new int[]{100, 100, 210, 210},
                                    4);
        }
        for (int j = 4; j < 8; j++) { // 右排
            int i = j - 4;
            fields[j] = new Polygon(new int[]{725 + 15 * i, 825 + 25 * i, 850 + 25 * i, 740 + 15 * i},
                                    new int[]{100 + 110 * i, 100 + 110 * i, 210 + 110 * i, 210 + 110 * i},
                                    4);
        }
        for (int j = 8; j < 12; j++) { // 下排
            int i = j - 8;
            fields[j] = new Polygon(new int[]{785 - 140 * i, 925 - 140 * i, 950 - 150 * i, 800 - 150 * i},
                                    new int[]{540, 540, 650, 650},
                                    4);
        }
        for (int j = 12; j < 16; j++) { // 左排
            int i = j - 12;
            fields[j] = new Polygon(new int[]{225 + 25 * i, 365 + 15 * i, 350 + 15 * i, 200 + 25 * i},
                                    new int[]{540 - 110 * i, 540 - 110 * i, 650 - 110 * i, 650 - 110 * i},
                                    4);
        }
//        /* 點擊土地，查看關卡說明 */
//        addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent e) {
//                Point clickPoint = e.getPoint();
//                selectedFieldIndex = getSelectedFieldIndex(clickPoint);
//                repaint();
//                if (selectedFieldIndex != -1) {
//                    
//                }
//            }
//        });
        /* 滑鼠進入土地，變色 */
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                Point enterPoint = e.getPoint();
                enteredFieldIndex = getSelectedFieldIndex(enterPoint);
                repaint();
            }
            public void mouseExited(MouseEvent e) {
                enteredFieldIndex = -1; // Reset
                repaint();
            }
        });
//        ------------------------------------------------------------------
        /* 匯入轉盤 */
        try {
    		spinImage = ImageIO.read(new File("src/Resources/spin.png"));
    		pointer = ImageIO.read(new File("src/Resources/pointer.png"));
		} catch (IOException e) {
            e.printStackTrace();
        }
        /* 提示字 */
        guesslabel = new JLabel("Guess a number:");
        guesslabel.setBounds(420, 480, 100, 30);
        this.add(guesslabel);
        /* 顯示預測的數字 */
        guessnum = new JLabel();
        guessnum.setBounds(520, 480, 150, 30);
        guessnum.setVisible(false);
        this.add(guessnum);
        /* 預測框 */
        textField = new JTextField("");
        textField.setBounds(520, 480, 150, 30);
        this.add(textField);
        /* 旋轉按鈕 */
        button = new JButton("GO!");
        button.setBounds(670, 480, 60, 30);
        this.add(button);
        /* 開始旋轉 */
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	if(textField.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "請輸入預測");
            	}else {  	
//	                RandomAngle = (new Random()).nextInt(360) + 720; // 720-1079
            		RandomAngle = 200 + 720;  
	                timer = new Timer(1, event -> {
	                for(int i=0;i<10;i++) { // 1ms轉10度
	                	angle += 1;
	                	repaint();
	                	if (angle >= RandomAngle) {
	                        timer.stop();
	                        /* 轉完轉盤後才執行 */
	                        if(whoseTurn == 1) p1.draw(getGraphics());
	                        if(whoseTurn == 2) p2.draw(getGraphics());
	                        if(whoseTurn == 1) p1.move((RandomAngle/60)%6+1);
	                        if(whoseTurn == 2) p2.move((RandomAngle/60)%6+1);
	                        if(textField.getText().equals(Integer.toString((RandomAngle/60)%6+1))) {
	                        	guessnum.setText("   BINGO!  +20 EXP");
	                        	if(whoseTurn == 1) p1.add_exp(20);
	                        	if(whoseTurn == 2) p2.add_exp(20);
	                        }
	                        else guessnum.setText("   " + textField.getText() + "  WRONG!");
	                        repaint();
	                        callout();
	                        break;
	                    }
	                }
	                });
	                timer.start();
	                /* 按下按鈕後，立即執行 */
	                System.out.println(RandomAngle);
	                // 顯示結果
	                guessnum.setText("   " + textField.getText());
	                guessnum.setVisible(true);
	                textField.setVisible(false);
	                button.setVisible(false);
	            }
            }
        });
//      ------------------------------------------------------------------------
        /* 建空地，之後可以蓋樹屋 */
        trees = new Tree[16];
        for(int i=0;i<16;i++) {
        	trees[i] = new Tree(i, 0);
        }
        /* 新玩家 */
        p1 = new Player(1);
        p2 = new Player(2);
    }
    
    public void check() {
    	Player p;
    	String content="", title="";
    	String method;
    	int cost = 0;
    	if(whoseTurn ==1) p = p1;
    	else p = p2;
    	
    	if(trees[p.get_position()].who_is_master() ==0){ // 荒地
    		method = "buy";
    		title = "荒地開發契約";
    		content = "是否要花 80 exp 購買地塊?";
    		cost = 80;
    	}
    	else if(whoseTurn == trees[p.get_position()].who_is_master()) { // 是地主
    		method = "upgrade";
    		title = "樹屋升級";
    		content = "是否要花 "+trees[p.get_position()].get_value()+" exp 升級您的樹屋?";
    		cost = trees[p.get_position()].get_value();
    	}
    	else { // 非地主
    		method = "grab";
    		title = "土地搶奪";
    		content = "是否要花 "+(trees[p.get_position()].get_value()+70)+" exp 進入土地挑戰關卡?"; // 花土地價值+70的exp進入挑戰
    		cost = (trees[p.get_position()].get_value()+70);
    	}
    	
    	if(cost <= p.get_exp()) {
	    	/* 選擇 */
			Object[] options = {"確認", "拒絕"};
	        int result = JOptionPane.showOptionDialog(
	            this,								// 父組件
	            content,			                // 顯示的消息
	            title, 						        // 框的標題
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.INFORMATION_MESSAGE,
	            null,								// 圖標
	            options,
	            options[0]
	        );
	        // 買!!!
	        if (result == JOptionPane.YES_OPTION) {
	        	if(method.equals("buy")) {
	        		p.add_exp(-80);
	        		trees[p.get_position()].buy(whoseTurn);
	        	}
	        	if(method.equals("upgrade")) {
	        		p.add_exp(-1*trees[p.get_position()].get_value());
	        		trees[p.get_position()].upgrade();
	        	}
	        	if(method.equals("grab")) trees[p.get_position()].grab(whoseTurn);
	            repaint();
	        }
    	}
        // 轉盤再度顯示
        angle = 0;
        guessnum.setVisible(false);
        textField.setText("");
        textField.setVisible(true);
        button.setVisible(true);
        // 換人
        if(whoseTurn==1) whoseTurn=2;
        else whoseTurn=1;
	}
    /* play game */
    private void callout(){
    	GameFrame GameFrame = new GameFrame();
    	 ActionListener taskPerformer = new ActionListener() {
             public void actionPerformed(ActionEvent evt) {
             	GameFrame.setVisible(true);
             }
         };
         Timer timer2 = new Timer(2000, taskPerformer);
         timer2.setRepeats(false);
         timer2.start();
    	if(whoseTurn == 2) {
	    	if(p1.get_position() % 2 == 0) {
	    		double playerCp = 300;
	    		Random random = new Random();
	    		double enemyCp = Math.round(1+random.nextDouble(playerCp));
	    		System.out.println("enemyCp = "+enemyCp);
	    		
	    		try {
					Game game1 = new Game(playerCp,enemyCp,GameFrame);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}
	    	else {
	    		GameFrame.setTitle("Gain Meteor");
	            GameFrame.add(new GainMeteor(p1));
	    		GameFrame.setFrame();
	    	}
    	}
    	if(whoseTurn == 1) {
	    	if(p2.get_position() % 2 == 0) {
	    		ReadEnemyFile readEnemyFile = null;
				try {
					readEnemyFile = new ReadEnemyFile();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //讀all怪獸檔
	    		ArrayList<String[]> allEnemyList = readEnemyFile.getEnemyFile(); //取得all怪獸檔
    		
		        //-------------------------game2----------------------------------
		        //1. 隨機選擇一隻怪獸
		        //2. 將怪獸資訊及等級傳入game2
		        //3. 依據cp值,設定enemy移動速度和攻擊物掉落多寡
		        //[id, 怪獸名, 攻擊物1, 攻擊物2, 怪獸w, 怪獸h, 攻擊物1w, 攻擊物1h, 攻擊物2w, 攻擊物2h]
		        ChooseEnemy chooseEnemy = null;
				try {
					chooseEnemy = new ChooseEnemy(allEnemyList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //隨機挑選怪獸
		        String[] game2_RandomEnemy = chooseEnemy.getRandomEnemy(); //取得game2 enemy
		        System.out.println("chooseEnemy = "+game2_RandomEnemy[1]);
		        int game2_enemyCP = chooseEnemy.getCP(); //取得CP
		        Game2 game2 = null;
				try {
					game2 = new Game2(game2_RandomEnemy,game2_enemyCP,GameFrame);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        int game2_totalCP = game2.getCP();
		        System.out.println("GAME2 END TOTAL CP = "+game2_totalCP);
	    	}else {
	    		//GameFrame.setTitle("Gain Meteor");
	            GameFrame.add(new GainMeteor(p2));
	            GameFrame.setFrame();
	    	}
    	}
//        // next round
        GameFrame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
            	// 問:是否要買土地
            	repaint();
                check();
            }
        });
    	
    	
    }
    private int getSelectedFieldIndex(Point point) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].contains(point)) {
                return i;
            }
        }
        return -1; // (滑鼠點擊處不屬於任一合法區域)
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // 土地顏色 
        int x_average = 0, y_average =0;
        for (int i = 0; i < fields.length; i++) {
        	if(i % 2 == 0) g2d.setColor(new Color(154 ,255, 154));
        	else g2d.setColor(Color.yellow);
            if (i == selectedFieldIndex) {
                g2d.setColor(Color.RED); // 如果 field 被選中，將其顏色設定為紅色
            }
            else if (i == enteredFieldIndex) {
                g2d.setColor(Color.BLUE);
                if(notice != null) {
                	for(int j:fields[i].xpoints) {
                		x_average += j;
                	}
                	for(int j:fields[i].ypoints) {
                		y_average += j;
                	}
                }
            }
            g2d.fill(fields[i]);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));
            g2d.draw(fields[i]);
        }
        // 樹
        for (Tree tree : trees) {
            tree.draw(g);
        }
        // 玩家
        if (p1 != null) {
            p1.draw(g);
        }
        if (p2 != null) {
            p2.draw(g);
        }
        // 轉盤
        if (spinImage != null) {
            int x = 460, y = 220, width = 250, height = 250;
            AffineTransform old = g2d.getTransform();
            g2d.rotate(Math.toRadians(angle*-1), x + width / 2, y + height / 2);
            g2d.drawImage(spinImage, x, y, width, height, this);
            g2d.setTransform(old); // Restore original transform
        }
        if (pointer != null) {
            g.drawImage(pointer, 571, 220, 22, 26, this);
        }
        if (yourTurn != null && whoseTurn == 1) {
            g.drawImage(yourTurn, 160, 70, 70, 70, this);
        }
        if (yourTurn != null && whoseTurn == 2) {
            g.drawImage(yourTurn, 160, 170, 70, 70, this);
        }
        g.setColor(Color.BLACK);
        if(x_average != 0){
        	g.drawImage(notice, x_average/4-50, y_average/4-150, 300, 160, this);
        	g.drawString("Field "+enteredFieldIndex, x_average/4, y_average/4-110);
        }
        
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(1200, 650); // 組件
    }
}