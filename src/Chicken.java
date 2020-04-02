import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chicken {
    private int posX;
    private int posY;
    private static int go = 10;
    public static BufferedImage image;
    public static BufferedImage image1;
    public static BufferedImage image2;
    private boolean visible;

    public Chicken(int posX, int posY) {

        this.posX = posX;
        this.posY = posY;
        visible = true;
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
        posY += 1;
    }

    public void checkBoard() {
        if (posX < 20) {
            go = 10;
        } else if (posX > 940) {
            go = -10;
        }

    }

    public Rectangle rectangle() {
        return new Rectangle(posX, posY, 40, 40);
    }
}
