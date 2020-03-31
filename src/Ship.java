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

    public Ship() {
        File imageFile = new File("image\\ship.png");
        try {
            image = ImageIO.read(imageFile);

        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

    }

}
