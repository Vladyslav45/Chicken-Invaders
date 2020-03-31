import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        add(new GameWidok());
        setSize(1000,800);
        setIconImage(new ImageIcon("image\\chicken0.png").getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
