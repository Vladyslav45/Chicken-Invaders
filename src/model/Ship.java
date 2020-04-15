package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Ship {

    public int wspx=500;
    public int wspy=700;
    public BufferedImage image;

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private boolean visible;

    public Ship() {
        visible = true;
        File imageFile = new File("image\\ship.png");
        try {
            image = ImageIO.read(imageFile);

        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

    }

    public boolean isVisible() {
        return visible;
    }

    public void die(){
        visible = false;
    }

    public Rectangle rectangle(){
        return new Rectangle(wspx,wspy, 40,40);
    }

}
