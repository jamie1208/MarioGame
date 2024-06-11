package game2.src.Game2;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class EndFrame extends JFrame{ 

    EndPanel panel;

    EndFrame(EndPanel panel,JFrame game2Frame, JFrame starFrame){
        this.panel = panel;
        //panel = new Game2Panel();
        // 獲取螢幕解析度
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        //設定視窗顯示在螢幕畫面中間位置
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        this.panel = panel;
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                game2Frame.setVisible(false);
                starFrame.setVisible(false);
                System.out.println("panel3 close");
                panel.close = true;
            }
        });
    }

    public void setFrame(){
        //panel = new Game2Panel();
        this.add(panel);
        this.setTitle("End Game2");
        this.setResizable(false);
        //this.setBackground(Color.BLACK);
        this.pack();//依據panel 大小改變，所以不用特定設size
        this.setLocationRelativeTo(null);//若無component,則顯示螢幕中間
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setVisible(true); //顯示
    }
}
