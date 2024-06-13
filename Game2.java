package game2.src.Game2;

import game.GameFrame;

//執行緒，可與main執行緒分開 - 同時執行多件事
//Thread gameThread;
//未完成 : 碰撞後的音效,結束後的畫面,獲勝音效,倒計時
public class Game2 {

	public static GameFrame frame;
	//遊戲開始
	public Thread startThread;
	private StartPanel startPanel;

	//遊戲進行設置
	public static Thread game2Thread;
	public static Game2Panel game2Panel;
	
	//遊戲結束
	public static Thread endThread;
	public static EndPanel endPanel;
	
    int waffle_score;
    boolean success; //遊戲通關
    String[] randomEnemy; 
    static double enemy_CP;
    static int total_CP;

    public Game2(String[] randomEnemy,double enemy_CP,GameFrame gameFrame)throws Exception{

        this.randomEnemy = randomEnemy;
        Game2.enemy_CP = enemy_CP;
        Game2.frame = gameFrame; 
        Game2.total_CP = 0;
    
		startPanel = new StartPanel(randomEnemy);
		startThread = new Thread(startPanel);
		System.out.println("START GAME!");
		frame.add(startPanel);
		frame.setFrame();
		startThread.start();
        System.out.println("GameENd!");   
    }
}
