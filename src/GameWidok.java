import javax.swing.*;
import java.awt.*;

public class GameWidok extends JFrame {

    private JButton jButton;

    public GameWidok(){
        setLayout(null);
        setVisible(true);
        setSize(700,700);
        setLocation(500,250);

        jButton = new JButton("Return");
        jButton.setBounds(100,100,60,20);
        jButton.addActionListener(e -> {
            new StartWidok();
        });
        add(jButton);
    }
}
