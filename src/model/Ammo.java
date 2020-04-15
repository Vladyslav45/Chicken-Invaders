package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Ammo {
    private int posX;
    private int posY;
    private BufferedImage img;
    private boolean visible;
    private static int goDown = 0;
    Random random = new Random();
    private double randomMove = random.nextInt(760) + 20;


    public Ammo() {
        visible = false;
        File imageFile = new File("image\\ammo.png");
        try {
            img = ImageIO.read(imageFile);

        } catch (
                IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }

    public Rectangle rectangle() {
        return new Rectangle(posX, posY, 40, 40);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void move() {
        posX = (int) (randomMove * 0.5);
        posY = (goDown + 5);
        goDown++;

    }

    public void checkVisibleAmmo() {
        if (!visible) {
            posX = 30;
            posY = 0;
            goDown=0;
        }
    }
}



