package Game2Data;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadEnemyFile{

    private ArrayList<String[]> allEnemyList = new ArrayList<>();
    File allEnemy;
    Scanner scanner;

    public ReadEnemyFile()throws Exception{
        allEnemy = new File("src/Game2Data/allEnemy.txt");
        if (allEnemy.exists()){
            System.out.println("data exits!");
        }
        scanner = new Scanner(allEnemy);
        while(scanner.hasNext()){
            allEnemyList.add(scanner.nextLine().split(", "));
        }
    }

    public ArrayList<String[]> getEnemyFile(){
        return allEnemyList;
    }
}