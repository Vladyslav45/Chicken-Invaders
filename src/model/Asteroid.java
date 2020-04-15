package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Asteroid {
    private AffineTransform at;
    public BufferedImage asteroid;
    private int rotate = 10;
    private int przesun = 10;
    Random random = new Random();
    private int randomMove = random.nextInt(300) * 2;
    private int posX;
    private int posY;
    private boolean visible;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Asteroid(){
        visible = true;
    try {
        asteroid = ImageIO.read(new File("image\\Asteroid.png"));
    } catch (IOException ex) {
        ex.printStackTrace();
    }

}

    public void destructionAsteroid() {
        visible = false;
    }
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void move(){
        posX = randomMove+(przesun / 4);
        posY = 20 + przesun;
        at = AffineTransform.getTranslateInstance(getPosX(), getPosY() );
        at.rotate(Math.toRadians(rotate++), getWidth() / 2 , getHeight() / 2);
        przesun++;
    }

    public AffineTransform getAt() {
        return at;
    }

    public double getWidth() {
        return asteroid.getWidth();
    }

    public double getHeight() {
        return asteroid.getHeight();
    }

    public Rectangle rectangle(){
        return new Rectangle(getPosX(),getPosY(), asteroid.getWidth(),asteroid.getHeight());
    }

}
