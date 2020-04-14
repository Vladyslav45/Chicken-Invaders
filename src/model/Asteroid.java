package model;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Asteroid {
    private AffineTransform at;
    public BufferedImage asteroid;
    private int rotate = 0;
    private int przesun = 0;
    Random random = new Random();
    private double randomMove = random.nextInt(300) * 2;

    public Asteroid(){
    try {
        asteroid = ImageIO.read(new File("image\\Asteroid.png"));
    } catch (IOException ex) {
        ex.printStackTrace();
    }

}

    public void move(){
        at = AffineTransform.getTranslateInstance(randomMove + (przesun / 2), 20 + (przesun));
        at.rotate(Math.toRadians(rotate++), asteroid.getWidth() / 2 , asteroid.getHeight() / 2);
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
}
