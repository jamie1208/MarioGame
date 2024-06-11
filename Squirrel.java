package game2.src.Game2;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

//被動移動,按left,right,依照speed速度
public class Squirrel extends Rectangle{

    int xVelocity; //按key後才決定
    int speed = 10;
    BufferedImage squirrel_img = null;

    Squirrel(int x,int y,int SQUIRREL_WIDTH,int SQUIRREL_HEIGHT,BufferedImage squirrel_img){
        super(x,y,SQUIRREL_WIDTH,SQUIRREL_HEIGHT);
        this.squirrel_img = squirrel_img;
    }

    //按鍵
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            setxVelocity(-speed);//設定要移動多少
            move(); //實際移動位置 ＝ 更改x
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            setxVelocity(speed);
            move();
        }
    }

    //放鍵
    //在此遊戲中，有無released無差 (因speed設為0)
    //但若希望object有反彈效果（按往前，放開往後),即可用released
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            setxVelocity(0);//設定要移動多少
            move(); //實際移動位置 ＝ 更改x
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            setxVelocity(0);
            move();
        }
    }

    public void move(){
        x = x+xVelocity;
    }

    //x跑的方向
    public void setxVelocity(int xVelocity){
        this.xVelocity = xVelocity;
    }
    
    //別人呼叫時，畫出image
    public void draw(Graphics g){
        g.drawImage(squirrel_img, x, y, width,height,null);
        // g.setColor(Color.red);
        // g.fillRect(x, y, width, height);
    }


}
