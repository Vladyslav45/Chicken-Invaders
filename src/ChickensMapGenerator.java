import java.awt.*;

public class ChickensMapGenerator {
    public int[][] chickens;

    public ChickensMapGenerator(){
        chickens = new int[][]{
                {2,2,2,2,2,2,2,2,2,2,2},
                {1,1,1,1,1,1,1,1,1,1,1},
                {2,2,2,2,2,2,2,2,2,2,2},
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0},
        };
    }

    public void draw(Graphics g){
        for (int i = 0; i < chickens.length; i++){
            for (int j = 0; j < chickens[i].length; j++){
                switch (chickens[i][j]){
                    case 0: g.drawImage(Chicken.image, GameWidok.chickenList[i][j].getPosX(), GameWidok.chickenList[i][j].getPosY(), 40,40,null); break;
                    case 1: g.drawImage(Chicken.image1, GameWidok.chickenList[i][j].getPosX(), GameWidok.chickenList[i][j].getPosY(),40,40,null); break;
                    case 2: g.drawImage(Chicken.image2, GameWidok.chickenList[i][j].getPosX(), GameWidok.chickenList[i][j].getPosY(), 40,40,null); break;
                }
            }
        }
    }
}
