
import model.Chicken;
import model.Ship;
import model.Shot;
import music.Music;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class GameWidok extends JPanel implements ActionListener {
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton closeButton;
    private Timer timer;
    private Ship ship;
    private ArrayList<Integer> livePlayer;
    private ChickensMapGenerator chickensMapGenerator;
    private ArrayList<Shot> shots;
    static Chicken[][] chickenList;
    private int score;
    private JLabel scoreLabel;
    private int chickensAlive = 55;
    private int asteroidAlive = 275;
    private int timerDelay = 17;
    private int rotate = 0;
    private int przesun = 0;
    Random random = new Random();
    private double randomMove = random.nextInt(300) * 2;
    private boolean visible;

    public GameWidok() {
        addKeyListener(new keyPressPlayer());
        setFocusable(true);
        setLayout(null);

        timer = new Timer(timerDelay, this);
        timer.start();
        gameInit();
        Music.musicOfTheGame();
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
            //TODO model.Ship doesn't shot and model.Shot press space open menu Pause
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
                Music.getClip().stop();
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
        livePlayer = new ArrayList<>();
        livePlayer.add(0);
        livePlayer.add(0);
        livePlayer.add(0);
        ship = new Ship();
        shots = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("image\\tloGry.jpg").getImage(), 0, 0, 1000, 800, this);
        chickensMapGenerator.draw(g);
        if (ship.isVisible()) {
            g.drawImage(ship.image, ship.wspx, ship.wspy, 40, 40, this);
        }
        for (Shot shot : shots) {
            g.drawImage(shot.img, shot.getPosX(), shot.getPosY(), 10, 30, this);
        }

        for (int i = 0; i <= livePlayer.size(); i++) {
            g.drawImage(new ImageIcon("image\\serce.png").getImage(), getWidth() - (50 * i), getHeight() - 50, 30, 30, this);

        }

        for (Chicken[] chickens : chickenList) {
            for (Chicken chicken : chickens) {
                Chicken.Bomb b = chicken.getBomb();
                if (!b.isDestroyed()) {
                    g.drawImage(b.img, b.getX(), b.getY(), 10, 30, this);
                }
            }
        }

        drawAsteroid(g);
        drawFirstAidKit(g);
        repaint();


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
            Iterator<Shot> shotIterator = shots.iterator();
            while (shotIterator.hasNext()) {
                Shot shot = shotIterator.next();
                if (shot.getPosY() > 0) {
                    shot.move();
                } else {
                    shotIterator.remove();
                }
            }
            shotPlayer();
            shotChickens();
        } else {
            gameWin();
        }

        repaint();
    }


    public class keyPressPlayer extends KeyAdapter {
        private long lastShoot = System.currentTimeMillis();

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_LEFT && ship.wspx + 20 > 0) {
                ship.wspx -= 15;
            }
            if (code == KeyEvent.VK_RIGHT && ship.wspx + 65 < getWidth()) {
                ship.wspx += 15;
            }
            if (code == KeyEvent.VK_UP && ship.wspy > 0) {
                ship.wspy -= 15;
            }
            if (code == KeyEvent.VK_DOWN && ship.wspy + 70 < getHeight()) {
                ship.wspy += 15;
            }

            if (code == KeyEvent.VK_SPACE && lastShoot + 500 < System.currentTimeMillis()) {
                shots.add(new Shot(ship.wspx + 30, ship.wspy - 20, 7));
                Music.musicShoot();
                lastShoot = System.currentTimeMillis();
            }
            invalidate();
            validate();
            repaint();
        }
    }


    private void shotPlayer() {
        shots.removeIf(this::checkCollision);
    }

    private boolean checkCollision(Shot shot) {
        for (Chicken[] chickens : chickenList) {
            for (Chicken chicken : chickens) {
                if (shot.rectangle().intersects(chicken.rectangle()) && chicken.isVisible()) {
                    chicken.die();
                    chickensAlive--;
                    score += 1000;
                    scoreLabel.setText("Score: " + score);
                    return true;
                }
            }
        }
        return false;
    }

    private void shotChickens() {

        for (Chicken[] chickens : chickenList) {
            for (Chicken chicken : chickens) {
                int shot = (int) (Math.random() * 320 + 1);
                Chicken.Bomb bomb = chicken.getBomb();
                if (shot == 320 && chicken.isVisible() && bomb.isDestroyed()) {
                    bomb.setDestroyed(false);
                    bomb.setX(chicken.getPosX());
                    bomb.setY(chicken.getPosY());
                }

                if (ship.isVisible() && !bomb.isDestroyed()) {
                    if (ship.rectangle().intersects(bomb.rectangleBomb())) {
                        livePlayer.remove(livePlayer.size() - 1);
                        bomb.setDestroyed(true);

                        if (livePlayer.isEmpty())
                            gameLose();

                    }
                }

                if (!bomb.isDestroyed()) {
                    bomb.setY(bomb.getY() + 2);
                    if (bomb.getY() >= 750) {
                        bomb.setDestroyed(true);
                    }
                }
            }
        }
    }

    private void gameWin() {
        Music.musicGameWin();
        timer.stop();
        int res = JOptionPane.showConfirmDialog(this, "YOU WIN!!!\n" + "Do you want to continue game", "model.Chicken Invaders", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            timer.start();
            chickensAlive = 55;
            gameInit();
        } else if (res == JOptionPane.OK_CANCEL_OPTION) {
            StartWidok.rankingMap.put(StartWidok.nickname, score);
            Music.getClip().stop();
            SwingUtilities.windowForComponent(this).dispose();
            new StartWidok().setVisible(true);
        }
    }

    private void gameLose() {
        Music.musicGameOver();
        timer.stop();
        int res = JOptionPane.showConfirmDialog(this, "You lose.\n" + "Are you replay game?", "model.Chicken Invaders", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            timer.start();
            score = 0;
            scoreLabel.setText("Score: " + score);
            chickensAlive = 55;
            gameInit();
        } else if (res == JOptionPane.NO_OPTION) {
            StartWidok.rankingMap.put(StartWidok.nickname, score);
            Music.getClip().stop();
            SwingUtilities.windowForComponent(this).dispose();
            new StartWidok().setVisible(true);
        }
    }

    private void drawAsteroid(Graphics g) {

        BufferedImage Asteroid = null;
        try {
            Asteroid = ImageIO.read(new File("image\\Asteroid.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AffineTransform at = AffineTransform.getTranslateInstance(randomMove + (przesun / 2.5), 10 + (przesun / 2.5));
        at.rotate(Math.toRadians(rotate++), Asteroid.getWidth() / 2, Asteroid.getHeight() / 2);

        AffineTransform at1 = AffineTransform.getTranslateInstance(randomMove * 1.5, 10 + (przesun / 2.5));
        at1.rotate(Math.toRadians(rotate++), Asteroid.getWidth() / 2, Asteroid.getHeight() / 2);
        przesun++;

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(Asteroid, at, this);
        g2d.drawImage(Asteroid, at1, this);

    }

    private void drawFirstAidKit(Graphics g) {

        BufferedImage FirstAidKit = null;
        try {
            FirstAidKit = ImageIO.read(new File("image\\apteczka.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AffineTransform at3 = AffineTransform.getTranslateInstance(randomMove * 0.5, 10 + (przesun / 2.5));

        przesun++;

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(FirstAidKit, at3, this);

    }
}