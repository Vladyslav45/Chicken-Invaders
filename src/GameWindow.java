import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JFrame{
    public GameWindow() {
        add(new GameWidok());
        setUndecorated(true);
        setVisible(true);
        setSize(1000,800);
        initMenuBar();
        setIconImage(new ImageIcon("image\\chicken0.png").getImage());
        setLocationRelativeTo(null);
        setResizable(false);
        ConnectionJDBC.con();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setState(NORMAL);
                GameWidok.timer.start();
            }
        });
    }

    public void initMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        JButton iconified = new JButton();
        JButton frame = new JButton();
        JButton close = new JButton();
        JMenu jMenu = new JMenu();
        JMenuItem jMenuItem = new JMenuItem("Exit");
        jMenuItem.addActionListener(e -> System.exit(0));

        jMenu.setText("Chickens Invaders");
        jMenu.setIcon(new ImageIcon("image\\chicken.png"));
        jMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GameWidok.timer.stop();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                GameWidok.timer.start();
            }
        });
        jMenu.add(jMenuItem);

        jMenuBar.setPreferredSize(new Dimension(getWidth(), 30));
        jMenuBar.add(jMenu);
        jMenuBar.add(Box.createGlue());

        iconified.setIcon(new ImageIcon("image\\minimize.png"));
        iconified.setOpaque(false);
        iconified.setContentAreaFilled(false);
        iconified.setBorderPainted(false);
        iconified.setFocusable(false);
        iconified.addActionListener(e -> {
            GameWidok.timer.stop();
            setState(Frame.ICONIFIED);
        });

        frame.setIcon(new ImageIcon("image\\window.png"));
        frame.setOpaque(false);
        frame.setContentAreaFilled(false);
        frame.setBorderPainted(false);
        frame.setEnabled(false);

        close.setIcon(new ImageIcon("image\\close.png"));
        close.setOpaque(false);
        close.setContentAreaFilled(false);
        close.setBorderPainted(false);
        close.setFocusable(false);
        close.addActionListener(e -> System.exit(0));


        jMenuBar.add(iconified);
        jMenuBar.add(frame);
        jMenuBar.add(close);
        setJMenuBar(jMenuBar);
    }
}
