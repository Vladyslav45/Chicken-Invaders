import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Shot {
    private int posX;
    private int posY;
    public BufferedImage img;
    public int go;
    private boolean visible;

    public Shot(){

    }

    public Shot(int posX, int posY, int go){
        this.posX = posX;
        this.posY = posY;
        this.go = go;
        visible = true;
        File imageFile = new File("image\\ship.png");
        try {
            img = ImageIO.read(imageFile);

        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }

    public void close(){
        visible = false;
    }

    public boolean isVisible() {
        return visible;
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

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Rectangle rectangle(){
        return new Rectangle(posX,posY, 10,30);
    }
}