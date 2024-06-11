package game2.src.Game2;

import com.martio.game.GameFrame;

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
    static int enemy_CP;
    int total_CP;

    public Game2(String[] randomEnemy,int enemy_CP,GameFrame gameFrame)throws Exception{

        this.randomEnemy = randomEnemy;
        Game2.enemy_CP = enemy_CP;
        Game2.frame = gameFrame; 
    
		startPanel = new StartPanel(randomEnemy);
		startThread = new Thread(startPanel);
		System.out.println("START GAME!");
		frame.add(startPanel);
		frame.setFrame();
		startThread.start();
        System.out.println("GameENd!");   
    }

    //回傳遊戲結束後獲得的enemy_CP
    public int getCP(){
        return total_CP;
    }
}
