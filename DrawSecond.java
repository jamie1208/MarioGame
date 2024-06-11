package game2.src.Game2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DrawSecond {

    private static final String SECONOD1_NAME = "src/Resources/1.png";
    private static final String SECONOD2_NAME = "src/Resources/2.png";
    private static final String SECONOD3_NAME = "src/Resources/3.png";
    private static final String GO_NAME = "src/Resources/go.png";

    int GAME_WIDHTH;
    int GAME_HEIGHT;
    int SECOND_WIDTH;
    int SECOND_HEIGHT;
    BufferedImage second1_img,second2_img,second3_img,go_img,second_img;

    DrawSecond(int GAME_WIDHTH,int GAME_HEIGHT){
        this.GAME_WIDHTH = GAME_WIDHTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        //this.SECOND_WIDTH = GAME_WIDHTH/4;
        this.SECOND_HEIGHT = GAME_HEIGHT/4;
        setAllImg();
    }

    public void setAllImg(){
        try{
            second1_img = ImageIO.read(new File(SECONOD1_NAME));
            second2_img = ImageIO.read(new File(SECONOD2_NAME));
            second3_img = ImageIO.read(new File(SECONOD3_NAME));
            go_img = ImageIO.read(new File(GO_NAME));
        }catch(IOException e){System.out.println("no second file!");}
    }

    public void setImg(int i){
        if(i == 3) second_img = second1_img;
        if(i == 2) second_img = second2_img;
        if(i == 1) second_img = second3_img;
        if(i == 4) second_img = go_img;
    }

    public void draw(Graphics g){
        SECOND_WIDTH = (int)(((double)SECOND_HEIGHT/second_img.getHeight())*second_img.getWidth());
        g.drawImage(second_img, GAME_WIDHTH/2-SECOND_WIDTH/2, GAME_HEIGHT/2-SECOND_HEIGHT/2, SECOND_WIDTH,SECOND_HEIGHT,null);
    }
}
