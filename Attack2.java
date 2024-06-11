package game2.src.Game2;

import java.awt.*;
import java.awt.image.BufferedImage;

//單純建立一個pupu
public class Attack2 extends Rectangle {

    int yDirection;
    int speed = 10; //降下速度
    BufferedImage pupu_img = null;


    public Attack2(int x,int y,int PUPU_WIDTH,int PUPU_HEIGHT ,BufferedImage pupu_img){
        super(x,y,PUPU_WIDTH,PUPU_HEIGHT);
        this.pupu_img = pupu_img;
        setyDirection(speed);
    }

    public void setyDirection(int yDirection){
        this.yDirection = yDirection;
    }

    public void move(){
        y = y+yDirection;
    }
    
    public void draw(Graphics g){
        g.drawImage(pupu_img, x, y, width, height, null);
    }
}
