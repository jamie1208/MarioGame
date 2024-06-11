package game2.src.Game2;

import java.awt.*;
import java.awt.image.BufferedImage;

//單純建立一個pinecone
public class Attack1 extends Rectangle {

    int yDirection;
    int speed = 5; //降下速度
    BufferedImage pinecone_img = null;


    public Attack1(int x,int y,int PINECONE_WIDTH,int PINECONE_HEIGHT ,BufferedImage pinecone_img){
        super(x,y,PINECONE_WIDTH,PINECONE_HEIGHT);
        this.pinecone_img = pinecone_img;
        setyDirection(speed);
    }

    public void setyDirection(int yDirection){
        this.yDirection = yDirection;
    }

    public void move(){
        y = y+yDirection;
    }
    
    public void draw(Graphics g){
        g.drawImage(pinecone_img, x, y, width, height, null);
    }
}
