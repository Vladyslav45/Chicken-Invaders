package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chicken {
    private int posX;
    private int posY;
    private static int go = 2;
    public static BufferedImage image;
    public static BufferedImage image1;
    public static BufferedImage image2;
    private boolean visible;
    private Bomb bomb;

    public Chicken(int posX, int posY) {

        this.posX = posX;
        this.posY = posY;
        visible = true;
        bomb = new Bomb(posX, posY);
        File imageFile = new File("image\\chicken0.png");
        File imageFile1 = new File("image\\chicken1.png");
        File imageFile2 = new File("image\\chicken2.png");
        try {
            image = ImageIO.read(imageFile);
            image1 = ImageIO.read(imageFile1);
            image2 = ImageIO.read(imageFile2);

        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void die() {
        visible = false;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void move() {
        posX += go;
    }

    public void checkBoard() {
        if (posX < 20) {
            go = 2;
        } else if (posX > 940) {
            go = -2;
        }

    }

    public Bomb getBomb() {
        return bomb;
    }

    public Rectangle rectangle() {
        return new Rectangle(posX, posY, 40, 40);
    }

    public class Bomb {
        private int x;
        private int y;
        public BufferedImage img;
        private boolean destroyed;

        public Bomb(int x, int y) {
            this.x = x;
            this.y = y;
            destroyed = true;
            File imageFile = new File("image\\red-laser.png");
            try {
                img = ImageIO.read(imageFile);

            } catch (IOException e) {
                System.err.println("Blad odczytu obrazka");
                e.printStackTrace();
            }
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public boolean isDestroyed() {
            return destroyed;
        }

        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public Rectangle rectangleBomb(){
            return new Rectangle(x, y, 10,30);
        }
    }
}
