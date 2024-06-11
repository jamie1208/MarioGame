package game2.src.Game2;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Game2Panel extends Canvas implements Runnable{

    static final int COUNT_TIME = 30; //倒計時時間
    //set panel size
    static final int GAME_WIDHTH = 800;
    static final int GAME_HEIGHT = 800;
    //Dimension 封裝了width,heigjt
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDHTH,GAME_HEIGHT);
    

    //set waffle size,enemy size, squirrel size, all image_name
    //固定
    static final int WAFFLE_WIDTH = 50;
    static final int WAFFLE_HEIGHT = 50;
    static final int SQUIRREL_WIDTH = 100;
    static final int SQUIRREL_HEIGHT = 100;
    static final int WAFFLE_SCORE_WIDTH = 60;
    static final int WAFFLE_SCORE_HEIGHT = 40;
    static final int LOVE_WIDTH = 150;
    static final int LOVE_HEIGHT = 50;
    static final String BACKGROUND_NAME = "src/Resources/background.jpg";
    static final String WAFFLE_NAME = "src/Resources/waffle.png";
    static final String SQUIRREL_NAME = "src/Resources/p2.png";

    //自訂
    int ENEMY_WIDTH;
    int ENEMY_HEIGHT; 
    int ATTACK1_WIDTH;
    int ATTACK1_HEIGHT;
    int ATTACK2_WIDTH;
    int ATTACK2_HEIGHT;
    String ENEMY_NAME;
    String ATTACK1_NAME;
    String ATTACK2_NAME;

    //all sound
    static final String SECOND123_SOUND = "src/Resources/second321.wav";
    static final String WAFFLE_SOUND = "src/Resources/waffle.wav";
    static final String ATTSCK_SOUND = "src/Resources/attack.wav";

    //321倒數
    DrawSecond drawSecond; 
    int second = -1;
    int enemy_CP;
    boolean isDrawSecond = false;
    //音效
    Thread second_sound; //321倒數
    Thread waffle_sound;
    Thread attack_sound;
    //是否通關
    boolean success;
    Image image;
    Graphics graphics; //雙緩衝的畫布
    Graphics mGraphics; //主畫面的畫布
    double speed_mtp;//怪獸速度倍數
    Random random;
    Lifes lifes;
    CountDown countDown;
    Enemy enemy;
    Squirrel squirrel;
    static WaffleScore waffleScore;
    BufferedImage background_img,squirrel_img,enemy_img,attack1_img,attack2_img;
	static BufferedImage waffle_img;
    ArrayList<Waffle>waffles = new ArrayList<>();
    ArrayList<Attack1>attack1s = new ArrayList<>();
    ArrayList<Attack2> attack2s = new ArrayList<>();
    String[] randomEnemy; 


    //建立敵人,松鼠,背景
    Game2Panel(String[] randomEnemy){
    	this.randomEnemy = randomEnemy;
        random = new Random();
        System.out.println("game2Panel constuctor!");
        ENEMY_NAME = "src/Resources/"+randomEnemy[1]+".png";
        ATTACK1_NAME = "src/Resources/"+randomEnemy[2]+".png";
        ATTACK2_NAME = "src/Resources/"+randomEnemy[3]+".png";
        ENEMY_WIDTH = Integer.parseInt(randomEnemy[4]);
        ENEMY_HEIGHT = Integer.parseInt(randomEnemy[5]);
        ATTACK1_WIDTH = Integer.parseInt(randomEnemy[6]);
        ATTACK1_HEIGHT = Integer.parseInt(randomEnemy[7]);
        ATTACK2_WIDTH = Integer.parseInt(randomEnemy[8]);
        ATTACK2_HEIGHT = Integer.parseInt(randomEnemy[9]);
        this.setPreferredSize(SCREEN_SIZE);
    }

    //起始設定
    public void initialSetting()throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        this.enemy_CP = Game2.enemy_CP;
        //新增敵人,自己
        newEnemy();
        newSquirrel();
        //設定所有圖片,音效
        setAllImage();
        setAllSound();;
        //設定移動倍數
        setSpeedMtp();
        //計時器開始
        drawSecond = new DrawSecond(GAME_WIDHTH, GAME_HEIGHT);
        waffleScore = new WaffleScore(GAME_WIDHTH-LOVE_WIDTH-10,13+LOVE_HEIGHT,WAFFLE_SCORE_WIDTH,WAFFLE_SCORE_HEIGHT);
        countDown = new CountDown(COUNT_TIME);
        lifes = new Lifes(GAME_WIDHTH-LOVE_WIDTH-10,10,LOVE_WIDTH,LOVE_HEIGHT);
        addKeyListener(new AL());
        requestFocus(true);
    }

    public void setAllSound()throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        second_sound = new SoundHandler(SECOND123_SOUND);
        waffle_sound = new SoundHandler(WAFFLE_SOUND);
        attack_sound = new SoundHandler(ATTSCK_SOUND);
    }

    public void setAllImage()throws IOException{
        //設定獎勵物的img
        waffle_img = ImageIO.read(new File(WAFFLE_NAME));

        //設定攻擊物的img
        attack1_img = ImageIO.read(new File(ATTACK1_NAME));
        attack2_img = ImageIO.read(new File(ATTACK2_NAME));

        //設定背景圖片
        background_img = ImageIO.read(new File(BACKGROUND_NAME));
    }

    public void setSpeedMtp(){
        speed_mtp = Math.pow(enemy_CP/10, 1/2);
    }

    //新建enemy
    public void newEnemy(){
        try{
            enemy_img = ImageIO.read(new File(ENEMY_NAME));
        }catch(IOException e){System.out.println("not enemy!");}
        enemy = new Enemy(GAME_WIDHTH/2-ENEMY_WIDTH/2,10, ENEMY_WIDTH,ENEMY_HEIGHT,enemy_img, speed_mtp);
    }

    //新建squirrel
    public void newSquirrel(){
        try{
            squirrel_img = ImageIO.read(new File(SQUIRREL_NAME));
        }catch(IOException e){System.out.println("not squirrel!");}
        squirrel = new Squirrel(GAME_WIDHTH/2-SQUIRREL_WIDTH/2,GAME_HEIGHT-SQUIRREL_HEIGHT,SQUIRREL_WIDTH,SQUIRREL_HEIGHT,squirrel_img);

    }

    //新增waffle
    public void newwaffle(){
        Waffle waffle = new Waffle(enemy.x+enemy.width/2-WAFFLE_WIDTH/2,enemy.height,WAFFLE_WIDTH,WAFFLE_HEIGHT,waffle_img);
        waffles.add(waffle);
    }

    //新增attack2
    public void newAttack2(){
        Attack2 attack2 = new Attack2(enemy.x+enemy.width/2-ATTACK2_WIDTH/2,enemy.height,ATTACK2_WIDTH,ATTACK2_HEIGHT,attack2_img);
        attack2s.add(attack2);
    }
    
    //新增attack1
    public void newAttack1(){
        Attack1 attack1 = new Attack1(enemy.x+enemy.width/2-ATTACK1_WIDTH/2,enemy.height,ATTACK1_WIDTH,ATTACK1_HEIGHT,attack1_img);
        attack1s.add(attack1);
    }


    //建立的雙緩衝畫面,所有object畫於雙緩衝,雙緩衝再畫於panel
    public void paint(){
		//getbufferStrategy返回此元件使用的緩衝區策略
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			//建立緩衝區
			System.out.println("creat bufferStrategy!");
			createBufferStrategy(3);
			return;//not建立完馬上畫,等到下次才畫
		}
		Graphics g = bs.getDrawGraphics();//取得畫筆
		draw(g);
		g.dispose();
		bs.show();//顯示在frames
    }

    //畫在雙緩衝畫面
    public void draw(Graphics g){
        //System.out.println("Draw All");
        g.drawImage(background_img, 0, 0,GAME_WIDHTH,GAME_HEIGHT, null);
        squirrel.draw(g);
        enemy.draw(g);
        waffleScore.draw(g);
        lifes.draw(g);
        countDown.draw(g);
        for(Attack1 p : attack1s){
            p.draw(g);
        }
        for(Attack2 p : attack2s){
            p.draw(g);
        }
        for(Waffle w : waffles){
            w.draw(g);
        }
        if(second!=-1){
            System.out.println("Draw Second : "+second);
            drawSecond.draw(g);
            if(second == 4)isDrawSecond = true;
        }
    }

    //所有人物移動(主動)
    public void move(){
        enemy.move();
        squirrel.move();
        for(Attack1 p : attack1s){
            p.move();
        }
        for(Attack2 p : attack2s){
            p.move();
        }
        for(Waffle w : waffles){
            w.move();
        }

    }   

    //為了建立單獨的thread
    public void playSound(String SOUND,Thread sound){
        try{
            sound = new SoundHandler(SOUND);
            Thread thread = new Thread(sound);
            thread.start();
        }catch(Exception e){System.out.println("ERROR SOUND!");}
    }

    //撞擊確認
    public void checkCollision(){
        //boolean isCollision = false;
        //System.out.println("CheckCollision!");

        //敵人撞牆確認,撞牆換方向
        if(enemy.getX()+ENEMY_WIDTH >= GAME_WIDHTH){
            enemy.changexVelocity();
        }
        if(enemy.getX() <= 0){
            enemy.changexVelocity();
        }

        //squirrel out of windows,撞牆不動
        if(squirrel.x<=0){
            squirrel.x = 0;
        }
        if(squirrel.x>=GAME_WIDHTH-SQUIRREL_WIDTH){
            squirrel.x = GAME_WIDHTH-SQUIRREL_WIDTH;
        }

        //攻擊物撞擊確認
        for(Attack2 p : attack2s){
            //attack1碰到squirrel
            if(p.intersects(squirrel)){
                playSound(ATTSCK_SOUND,attack_sound);
                System.out.println("attack2 Collisioin!");
                lifes.love -= 1;
                lifes.changeLoveImg();
                //System.out.println("lifes.love = "+lifes.love);
                attack2s.remove(p);
                //isCollision = true;
                break;
            }
            //attack1掉落地面
            if(p.y>=GAME_HEIGHT-ATTACK2_HEIGHT){
                attack2s.remove(p);
                //isCollision = true;
                break;
            }
        }
        for(Attack1 p : attack1s){
            //attack1碰到squirrel
            if(p.intersects(squirrel)){
                playSound(ATTSCK_SOUND,attack_sound);
                System.out.println("attack1 Collisioin!");
                lifes.love -= 1;
                lifes.changeLoveImg();
                //System.out.println("lifes.love = "+lifes.love);
                attack1s.remove(p);
                //isCollision = true;
                break;
            }
            //attack1掉落地面
            if(p.y>=GAME_HEIGHT-ATTACK1_HEIGHT){
                attack1s.remove(p);
                //isCollision = true;
                break;
            }
        }
        
        //獎勵物撞擊確認
        for(Waffle w : waffles){
            //waffle碰到squirrel
            if(w.intersects(squirrel)){
                playSound(WAFFLE_SOUND, waffle_sound);
                waffleScore.setWaffleScore(1);
                waffles.remove(w);
                break;
            }
            //waffle掉落地面
            if(w.y>=GAME_HEIGHT-WAFFLE_HEIGHT){
                waffles.remove(w);
                break;
            }
        }

    }
    
    //結束確認
    public boolean checkStopThread(){
        //撞擊3次 (失敗)
        if(lifes.love == 0){
            System.out.println("Loose!");
            // System.out.println("Waffles = "+waffleScore.getWaffleScore());
            success = false;
            return true;
        }

        //撐過30秒=timer not Running (成功)
        if(!countDown.timer.isRunning()){
            System.out.println("Success!");
            // System.out.println("Waffles = "+waffleScore.getWaffleScore());
            success = true;
            return true;
        }

        return false;
    }


    //執行緒執行
    //每 1/amountofTicks秒 循環一次
    //-----------------------------
    //a.確認松果,敵人是否撞擊 
    //b.move所有object 
    //c.移動完後重新繪製於panel上
    //-----------------------------
    public void run(){
    	try {
			initialSetting();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //開始321音效
        //playSound(SECOND123_SOUND, second_sound);
        //nanoTime()用來計算時間差,判斷 (現在時間-上次移動時間)/ns > 1,才會動
		long lastTime = System.nanoTime();//虛擬機開啟時,會隨機生成一個long值,nanotTime()返回long值到現在的奈秒數
        double amountofTicks = 60.0; // 控制循環時間
        double ns = 1000000000/amountofTicks;
        double delta = 0;
        //double delta_pcone = 0;
        int addpc; //以此數字判斷是否要增加attack1
        int pc_delta = 0;
        while(true){
            if(checkStopThread()){break;}
            long now = System.nanoTime();
            delta += (now-lastTime)/ns; // 單位：秒/60
            lastTime = now;
//            if(!isDrawSecond && delta>1){
//                //System.out.println("second delta");
//                //繪製倒數3秒
//                for(int i=0;i<4;i++){
//                    second = i+1;
//                    drawSecond.setImg(second);
//                    paint();
//                    try{
//                        Thread.sleep(1000);
//                    }catch(InterruptedException e){
//                        System.out.println("執行緒中斷");
//                    }
//                }
//                second = -1;
//                paint();//先讓go消失
//
//                //下一次的delta從這時候開始重算，否則delta會非常大（因為seconde 321耗費很多時間）
//                delta=0;
//                lastTime = System.nanoTime();
//            }
            if(delta>1){
                //System.out.println("delta = "+delta);
                //speed range(根號5-10) -> ()
                if(pc_delta >= 50){
                    //System.out.println("Pc_delta"+pc_delta);
                    addpc = random.nextInt(100);
                    //System.out.println(addpc);
                    if(addpc<=35){ //更改此數字可以改變attack1降下的機率
                        System.out.println("attack1");
                        newAttack1();
                    }
                    if(addpc<=75 && addpc>40){
                        System.out.println("attack2");
                        newAttack2();
                    }
                    if(addpc >= 80){
                        newwaffle();
                    }
                    pc_delta = 0;
                }
                move();
                checkCollision();
                paint();
                delta --;
                pc_delta += 1;
            }
            
        }
        Game2.frame.remove(this);
		try {
			Game2.endPanel = new EndPanel(getResult());
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Game2.endThread = new Thread(Game2.endPanel);	
        Game2.endThread.start();
        Game2.frame.add(Game2.endPanel);
        Game2.frame.setFrame();
    }

    //傳給end panel
    public static int getWaffleScore(){
        return waffleScore.getWaffleScore();
    }

    public boolean getResult(){
        return success;
    }

    //key event
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            squirrel.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            squirrel.keyReleased(e);
        }
    }
}
