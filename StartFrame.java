package game2.src.Game2;

import java.awt.*;
import javax.swing.*;

public class StartFrame extends JFrame{

    StartPanel panel;

    StartFrame(StartPanel panel){
        this.panel = panel;
        // 獲取螢幕解析度
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        //設定視窗顯示在螢幕畫面中間位置
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }
    public void setFrame(){
        this.add(panel);
        this.setTitle("Start Game2");
        this.setResizable(false);
        //this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();//依據panel 大小改變，所以不用特定設size
        this.setLocationRelativeTo(null);//若無component,則顯示螢幕中間
        this.setVisible(true); //顯示
    }
}
