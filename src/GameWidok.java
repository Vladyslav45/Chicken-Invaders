import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class GameWidok extends JPanel implements ActionListener {
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton closeButton;
    private Timer timer;
    private Clip clip;
    private Ship ship;
    private ChickensMapGenerator chickensMapGenerator;
    private Shot shot;
    static Chicken[][] chickenList;
    private int score;
    private JLabel scoreLabel;
    private int chickensAlive = 55;
    private int gameWinPanel;

    public GameWidok() {
        addKeyListener(new keyPressPlayer());
        setFocusable(true);
        setLayout(null);

        timer = new Timer(200, this);
        timer.start();

        gameInit();
        musicOfTheGame();
        scoreLabel = new JLabel();
        scoreLabel.setBounds(20, 20, 100, 20);
        scoreLabel.setText("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        chickensMapGenerator = new ChickensMapGenerator();
        pauseButton = new JButton();
        pauseButton.setIcon(new ImageIcon("image\\pause.png"));
        pauseButton.setOpaque(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setBorderPainted(false);
        pauseButton.setBounds(920, 5, 60, 60);
        pauseButton.addActionListener(e -> {
            //TODO Ship doesn't shot and Shot press space open menu Pause
            timer.stop();
            JFrame frameForDialog = new JFrame();
            JDialog jDialog = new JDialog(frameForDialog, "Pause", true);
            resumeButton = new JButton();
            resumeButton.setBounds(10, 10, 60, 60);
            resumeButton.setIcon(new ImageIcon("image\\resume.png"));
            resumeButton.setOpaque(false);
            resumeButton.setContentAreaFilled(false);
            resumeButton.setBorderPainted(false);
            resumeButton.addActionListener(e1 -> {
                jDialog.setVisible(false);
                timer.start();
            });

            closeButton = new JButton();
            closeButton.setBounds(100, 10, 60, 60);
            closeButton.setIcon(new ImageIcon("image\\exit.png"));
            closeButton.setOpaque(false);
            closeButton.setContentAreaFilled(false);
            closeButton.setBorderPainted(false);
            closeButton.addActionListener(c -> {
                frameForDialog.dispose();
                SwingUtilities.windowForComponent(this).dispose();
                clip.stop();
                StartWidok.rankingMap.put(StartWidok.nickname, score);
                new StartWidok().setVisible(true);
            });

            jDialog.setLayout(null);
            jDialog.add(closeButton);
            jDialog.add(resumeButton);
            jDialog.setSize(200, 120);
            jDialog.setLocationRelativeTo(null);
            jDialog.setVisible(true);
        });
        add(pauseButton);
        add(scoreLabel);
    }

    private void gameInit() {
        chickenList = new Chicken[5][11];
        for (int i = 0; i < chickenList.length; i++) {
            for (int j = 0; j < chickenList[i].length; j++) {
                chickenList[i][j] = new Chicken(200 + j * 50, 80 + i * 40);
            }
        }
        ship = new Ship();
        shot = new Shot();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("image\\tloGry.jpg").getImage(), 0, 0, 1000, 800, this);
        chickensMapGenerator.draw(g);
        g.drawImage(ship.image, ship.wspx, ship.wspy, 70, 70, this);
        if (shot.isVisible()) {
            g.drawImage(shot.img, shot.getPosX(), shot.getPosY(), 10, 30, this);
        }
    }

    private void shotPlayer() {
        for (Chicken[] chickens : chickenList) {
            for (int j = 0; j < chickens.length; j++) {
                if (shot.rectangle().intersects(chickens[j].rectangle()) && shot.isVisible() && chickens[j].isVisible()) {
                    chickens[j].die();
                    chickensAlive--;
                    shot.close();
                    score += 1000;
                    scoreLabel.setText("Score: " + score);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (chickensAlive > 0) {
            for (int i = 0; i < chickenList.length; i++) {
                for (int j = 0; j < chickenList[i].length; j++) {
                    if (chickenList[i][j].isVisible()) {
                        chickenList[i][j].move();
                    }
                }
            }

            for (Chicken[] chicken : chickenList) {
                for (Chicken chicken1 : chicken) {
                    if (chicken1.isVisible()) {
                        chicken1.checkBoard();
                    }
                }
            }

            if (shot.isVisible() && shot.getPosY() > 0) {
                shot.move();
            } else {
                shot.close();
            }
            shotPlayer();
        } else {
            timer.stop();
            gameWinPanel = JOptionPane.showConfirmDialog(this, "YOU WIN", "Chicken Invaders", JOptionPane.OK_CANCEL_OPTION);
            if (gameWinPanel == JOptionPane.OK_OPTION) {
                StartWidok.rankingMap.put(StartWidok.nickname, score);
                clip.stop();
                SwingUtilities.windowForComponent(this).dispose();
                new StartWidok().setVisible(true);
            } else if (gameWinPanel == JOptionPane.OK_CANCEL_OPTION) {
                //TODO implementation replay game
            }
        }

        repaint();
    }


    public class keyPressPlayer extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_LEFT && ship.wspx + 20 > 0) {
                ship.wspx -= 10;
            }
            if (code == KeyEvent.VK_RIGHT && ship.wspx + 65 < getWidth()) {
                ship.wspx += 10;
            }
            if (code == KeyEvent.VK_UP && ship.wspy > 0) {
                ship.wspy -= 10;
            }
            if (code == KeyEvent.VK_DOWN && ship.wspy + 70 < getHeight()) {
                ship.wspy += 10;
            }

            if (code == KeyEvent.VK_SPACE) {
                if (!shot.isVisible()) {
                    shot = new Shot(ship.wspx + 30, ship.wspy - 20, -15);
                }
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