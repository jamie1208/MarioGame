package game2.src.Game2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

//傳入時間後，開始計時
public class CountDown {

    Timer timer;
    int time;

    CountDown(int time){
        this.time = time;
        timer = new Timer(1000, countDownPerformer);
        timer.start();
    }

    //開始倒數
    ActionListener countDownPerformer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            //System.out.println("countDouwnPerformer");
            if(time>0){
                //數字倒計時減1
                time -= 1;
            }
            else{ timer.stop(); }//停止倒計時
        }
    };

    public void draw(Graphics g){
        //樣式
        g.setColor(Color.GREEN.darker().darker().darker());
        g.setFont(new Font("ComicSansMS",Font.PLAIN,60));
        //座標是左下角
        g.drawString(String.valueOf(time), 15, 60);
    }
}
