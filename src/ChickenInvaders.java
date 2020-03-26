import javax.swing.*;
import java.awt.*;

public class ChickenInvaders {
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            StartWidok startWindow = new StartWidok();
            startWindow.setVisible(true);
            startWindow.setLocation(500,250);
            startWindow.setSize(800,600);
            startWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        });
    }
}
