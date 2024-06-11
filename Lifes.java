package game2.src.Game2;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Lifes extends Rectangle{

    static final String LOVE3_NAME = "src/Resources/love3.png";
    static final String LOVE2_NAME = "src/Resources/love2.png";
    static final String LOVE1_NAME = "src/Resources/love1.png";
    static final String LOVE0_NAME = "src/Resources/love0.png";
    int love = 3;
    BufferedImage love3_img,love2_img,love1_img, love0_img, love_img;

    Lifes(int x,int y,int LOVE_WIDTH,int LOVE_HEIGHT){
        super(x,y,LOVE_WIDTH,LOVE_HEIGHT);
        setLoveImg();
        changeLoveImg();
    }

    public void setLoveImg(){
        try{
            love3_img = ImageIO.read(new File(LOVE3_NAME));
            love2_img = ImageIO.read(new File(LOVE2_NAME));
            love1_img = ImageIO.read(new File(LOVE1_NAME));
            love0_img = ImageIO.read(new File(LOVE0_NAME));
        }catch(IOException e){System.out.println("not lifes!");}
    }

    public void changeLoveImg(){
        if(love == 3){
            love_img = love3_img;
        }
        else if(love == 2){
            love_img = love2_img;
        }
        else if(love == 1){
            love_img = love1_img;
        }
        else if(love == 0){
            love_img = love0_img;
        }
    }

    public void draw(Graphics g){

        g.drawImage(love_img, x, y, width, height, null);
    }
}
