import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWidok extends JFrame implements KeyListener {

    private JButton jButton;
    private JButton resumeButton;
    private JButton closeButton;
    private Timer timer;
    Ship ship = new Ship();
    public GameWidok(){
        //setLayout(null);
        addKeyListener(this);
        setFocusable(true);
        setSize(700,700);
        setLocation(500,150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        timer = new Timer(100, e -> {
            //TODO implementation game this
        });
        timer.start();


        jButton = new JButton();
        jButton.setIcon(new ImageIcon("image\\pause.png"));
        jButton.setOpaque(false);
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        jButton.setBounds(620,5,60,60);
        jButton.addActionListener(e -> {
            timer.stop();
            JDialog jDialog = new JDialog(this, "Pause", true);
            resumeButton = new JButton();
            resumeButton.setBounds(10, 10, 60,60);
            resumeButton.setIcon(new ImageIcon("image\\resume.png"));
            resumeButton.setOpaque(false);
            resumeButton.setContentAreaFilled(false);
            resumeButton.setBorderPainted(false);
            resumeButton.addActionListener(e1 -> {
                jDialog.setVisible(false);
                timer.start();
            });

            closeButton = new JButton();
            closeButton.setBounds(100,10,60,60);
            closeButton.setIcon(new ImageIcon("image\\exit.png"));
            closeButton.setOpaque(false);
            closeButton.setContentAreaFilled(false);
            closeButton.setBorderPainted(false);
            closeButton.addActionListener(c -> {
                dispose();
                StartWidok.rankingMap.put(StartWidok.nickname, 1000);
                new StartWidok().setVisible(true);
            });

            jDialog.setLayout(null);
            jDialog.add(closeButton);
            jDialog.add(resumeButton);
            jDialog.setSize(200,120);
            jDialog.setLocation(500, 250);
            jDialog.setVisible(true);
        });
        add(jButton);

        getContentPane().add(ship);

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_LEFT && ship.wspx+20 > 0){
            ship.wspx -=5;
        }
        if(code == KeyEvent.VK_RIGHT && ship.wspx+65 < getWidth()){
            ship.wspx +=5;
        }
        if(code == KeyEvent.VK_UP && ship.wspy > 0){
            ship.wspy -=5;
        }
        if(code == KeyEvent.VK_DOWN && ship.wspy+100 < getHeight()) {
            ship.wspy +=5;
        }
        invalidate();
        validate();
        repaint();
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
