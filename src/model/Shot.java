package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Shot {
    private int posX;
    private int posY;
    public BufferedImage img;
    public BufferedImage img1;
    public int go;
    private boolean visible;

    public Shot(int posX, int posY, int go){
        this.posX = posX;
        this.posY = posY;
        this.go = go;
        visible = true;
        File imageFile = new File("image\\blue-laser.png");
        File imageFile1 = new File("image\\blue-laser1.png");
        try {
            img = ImageIO.read(imageFile);
            img1 = ImageIO.read(imageFile1);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }

    public void move(){
        this.posY -= go;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Rectangle rectangle(){
        return new Rectangle(posX,posY, 10,30);
    }
}