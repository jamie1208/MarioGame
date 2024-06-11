package game2.src.Game2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Waffle extends Rectangle{

    int yDirection;
    int speed = 10;
    BufferedImage waffle_img = null;

    Waffle(int x,int y,int WAFFLE_WIDTH,int WAFFLE_HEIGHT,BufferedImage waffle_img){
        super(x,y,WAFFLE_WIDTH,WAFFLE_HEIGHT);
        this.waffle_img = waffle_img;
        setyDirection(speed);
    }

    public void setyDirection(int yDirection){
        this.yDirection = yDirection;
    }

    public void move(){
        y += yDirection;
    }

    public void draw(Graphics g){
        g.drawImage(waffle_img, x, y, width,height,null);
    }
}
