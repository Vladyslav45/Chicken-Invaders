import javax.swing.*;

public class GameWidok extends JFrame {

    private JButton jButton;

    public GameWidok(){
        setLayout(null);
        setSize(700,700);
        setLocation(500,250);

        jButton = new JButton("Return");
        jButton.setBounds(100,100,60,20);
        jButton.addActionListener(e -> {
            dispose();
            StartWidok.rankingMap.put(StartWidok.nickname, 1000);
            new StartWidok().setVisible(true);
        });
        add(jButton);
    }
}
