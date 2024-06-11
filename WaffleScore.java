package game2.src.Game2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WaffleScore extends Rectangle{
    
    static final String WAFFLE_SCOUR_NAME = "src/Resources/waffle_score.png";
    private int waffle_score = 0;
    BufferedImage score_image;

    WaffleScore(int x,int y,int width,int height){
        super(x,y,width,height);
    	waffle_score = 0;
        setWaffleScoreImg();
    }

    public void setWaffleScoreImg(){
        try{
            score_image = ImageIO.read(new File(WAFFLE_SCOUR_NAME));
        }catch(IOException e){System.out.println("no waffle score!");}
    }

    public void setWaffleScore(int s){
        waffle_score += s;
    }

    public int getWaffleScore(){
        return waffle_score;
    }

    public void draw(Graphics g){
        g.drawImage(score_image, x, y, width,height,null);

        g.setColor(Color.GREEN.darker().darker().darker());
        g.setFont(new Font("ComicSansMS",Font.PLAIN,50));
        g.drawString(String.valueOf(waffle_score/10)+String.valueOf(waffle_score%10), x+width+10, y+height);
    }
}
