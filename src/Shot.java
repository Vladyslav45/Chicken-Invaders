import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Shot {
    public int posX;
    public int posY;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void move(){
        this.posY += go;
    }
}
