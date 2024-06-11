package game2.src.Game2;

import java.awt.*;
import java.awt.image.BufferedImage;

//主動移動,按照speed速度
public class Enemy extends Rectangle{

    int xVelocity; //初始化就決定,並依照碰牆改變
    int speed = 5; //人物移動速度
    double speed_mtp; //依據cp決定的倍數
    BufferedImage enemy_img = null;

    Enemy(int x,int y,int ENEMY_WIDTH,int ENEMY_HEIGHT,BufferedImage enemy_img,double speed_mtp){
        super(x,y,ENEMY_WIDTH,ENEMY_HEIGHT);
        this.enemy_img = enemy_img;
        //speed *= speed_mtp; //乘幾倍
        setxVelocity(speed);

    }

    public void setxVelocity(int xVelocity){
        this.xVelocity = xVelocity;
    }

    //panel判斷撞到牆壁,則換方向
    public void changexVelocity(){
        setxVelocity(-xVelocity);
    }

    //自動移動,每次repaint都會呼叫move()
    public void move(){
        x += xVelocity;
    }

    public void draw(Graphics g){
        g.drawImage(enemy_img, x, y, width,height,null);
    }
}
