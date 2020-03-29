import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class GameWidok extends JFrame {

    private JButton jButton;
    private JButton resumeButton;
    private JButton closeButton;
    private Timer timer;
    private Clip clip;

    public GameWidok(){
        setLayout(null);
        setSize(700,700);
        setLocation(500,250);
        setIconImage(new ImageIcon("image\\chicken.png").getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        timer = new Timer(100, e -> {
            //TODO implementation game this
        });
        timer.start();

        melodyOfTheGame();
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
                clip.stop();
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
    }

    private void melodyOfTheGame() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\muzyka rozpoczynająca rozgrywkę.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException d) {
            d.printStackTrace();
        }
    }
}
