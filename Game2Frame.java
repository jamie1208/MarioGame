package game2.src.Game2;

import java.awt.*;
import javax.swing.*;

public class Game2Frame extends JFrame{
    Game2Panel panel;
    Game2Frame(Game2Panel panel){
        //panel = new Game2Panel();
        // 獲取螢幕解析度
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        //設定視窗顯示在螢幕畫面中間位置
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        this.panel = panel;
        this.add(panel);
    }
    public void setFrame(){
        //panel = new Game2Panel();
        this.setTitle("Game2");
        this.setResizable(false);
        //this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();//依據panel 大小改變，所以不用特定設size
        this.setLocationRelativeTo(null);//若無component,則顯示螢幕中間
        this.setVisible(true); //顯示
    }
}
