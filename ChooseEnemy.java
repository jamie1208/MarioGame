package Game2Data;

import java.util.ArrayList;
import java.util.Random;

public class ChooseEnemy {

    Random random;
    ArrayList<String[]> allEnemyList;
    int allenemy_num; //所有資料數
    String[] random_all;//隨機選擇的資料(含等級)
    String[] random_enemy;//隨機選擇的enemy
    String level;//等級
    int CP;


    public ChooseEnemy(ArrayList<String[]> allEnemyList) throws Exception{
        random = new Random();
        this.allEnemyList = allEnemyList;
        allenemy_num = allEnemyList.size();
        System.out.println("allenemy_num = "+allenemy_num);
        random_all = allEnemyList.get(random.nextInt(allenemy_num));
        random_enemy = new String[random_all.length-1];
        printData();
    }

    //回傳 enemy的資料（不含等級）
    public String[] getRandomEnemy(){
        //System.arraycopy(elist, 0, flist, 0, elist.length);
        System.arraycopy(random_all, 0, random_enemy, 0, 10);
        return random_enemy;
    }

    //根據怪獸的等級(A,AA,AAA)隨機產生cp值 -> cp(50-299,300-599,600-999)
    public int getCP(){
        level = random_all[10];
        if(level.equals("A")){
            CP = random.nextInt(50,299);
        }
        else if(level.equals("AA")){
            CP = random.nextInt(300,599);
        }
        else {
            CP = random.nextInt(600,999);
        }
        System.out.println("enemy cp = "+CP);
        return CP;
    }

    public void printData(){
        for(String[] i:allEnemyList){
            for(String j:i){
                System.out.print(j+" ");
            }
            System.out.println();
        }
    }
}
