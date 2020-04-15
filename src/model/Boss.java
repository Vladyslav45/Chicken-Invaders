package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Boss {
    private int posX;
    private int posY;
    private BufferedImage img;
    private boolean visible;
    private boolean touch;



    private static int go = 5;
    private static int goDown = 1;

    public Boss(){
        visible = false;
        touch = true;
        File imageFile = new File("image\\boss.png");
        try {
            img = ImageIO.read(imageFile);

        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }
    public boolean isTouch() {
        return touch;
    }

    public void setTouch(boolean touch) {
        this.touch = touch;
    }

    public Rectangle rectangle() {
        return new Rectangle(posX, posY, 120,120);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isVisible() {
        return visible;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void move(){
        posX = posX + (int) (Math.random() * 3 + 0) + go;
        posY = posY + (int) (Math.random() * 3 + 0) + goDown;

        if (posX < 20){
            go += 1;
        }
        if (posX > 865){
            go -= 1;
        }

        if (posY < 20){
            goDown += 1;
        }
        if (posY > 650){
            goDown -= 1;
        }
    }


    public void checkVisible(){
        if (!visible){
            posX = 0;
            posY = 0;
        }
    }
}
