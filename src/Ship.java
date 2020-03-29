import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Ship extends JPanel {

    public int wspx=320;
    public int wspy=590;
    private BufferedImage image;

    public Ship() {
        super();
        File imageFile = new File("image\\ship.png");
        try {
            image = ImageIO.read(imageFile);

        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

    }
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image,wspx,wspy,70,70,this);
    }


}
