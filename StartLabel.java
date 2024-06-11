package game2.src.Game2;

import java.awt.*;
import javax.swing.*;

public class StartLabel extends JLabel{
    int x,y,width,height;
    StartLabel(int x,int y,int width,int height){
        this.setBounds(x,y,width,height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }
}
