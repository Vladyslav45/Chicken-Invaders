import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class GameWidok extends JFrame {

    private JButton pauseButton;
    private JButton resumeButton;
    private JButton closeButton;
    private Timer timer;
    private Clip clip;
    Ship ship = new Ship();

    public GameWidok(){
        addKeyListener(new keyPressPleyer());
        setFocusable(true);
        setSize(700,700);
        setLocation(500,150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        timer = new Timer(100, e -> {
            //TODO implementation game this
        });
        timer.start();

        musicOfTheGame();
        pauseButton = new JButton();
        pauseButton.setIcon(new ImageIcon("image\\pause.png"));
        pauseButton.setOpaque(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setBorderPainted(false);
        pauseButton.setBounds(620,5,60,60);
        pauseButton.addActionListener(e -> {
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
                clip.stop();
                StartWidok.rankingMap.put(StartWidok.nickname, 1000);
                new StartWidok().setVisible(true);
            });

            jDialog.setLayout(null);
            jDialog.add(closeButton);
            jDialog.add(resumeButton);
            jDialog.setSize(200,120);
            jDialog.setLocation(500, 150);
            jDialog.setVisible(true);
        });
        add(pauseButton);
        add(ship);
    }

    public class keyPressPleyer extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_LEFT && ship.wspx+20 > 0){
                ship.wspx -=10;
            }
            if(code == KeyEvent.VK_RIGHT && ship.wspx+65 < getWidth()){
                ship.wspx +=10;
            }
            if(code == KeyEvent.VK_UP && ship.wspy > 0){
                ship.wspy -=10;
            }
            if(code == KeyEvent.VK_DOWN && ship.wspy+100 < getHeight()) {
                ship.wspy +=10;
            }
            invalidate();
            validate();
            repaint();
        }
    }

    private void musicOfTheGame() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\muzyka rozpoczynająca rozgrywkę.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException s) {
            s.printStackTrace();
        }
    }
}
