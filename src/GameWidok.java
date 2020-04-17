
import model.*;
import music.Music;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;


import javax.swing.*;
import javax.swing.Timer;


public class GameWidok extends JPanel implements ActionListener {
    public JButton pauseButton;
    private JButton resumeButton;
    private JButton closeButton;
    static Timer timer;
    private Ship ship;
    private Boss boss;
    private FirstAidKiT firstAidKiT = new FirstAidKiT();
    private Ammo ammo;
    private ArrayList<Integer> livePlayer;
    private ChickensMapGenerator chickensMapGenerator;
    private ArrayList<Shot> shots;
    static Chicken[][] chickenList;
    public static int score;
    private JLabel scoreLabel;
    private int chickensAlive = 55;

    private JPanel healthBoss;
    private JProgressBar healthBossBar;
    private int countBossHealth = 15;
    private int timeAsteroid;
    private int timerDelay = 16;
    private long lastFirstAidKit;
    private long lastAsteroid;
    private long bossTouchShip;
    private long lastAmmo;
    private long timeNewAmmo;
    private ArrayList<Asteroid> asteroids;
    private Asteroid asteroid = new Asteroid();


    public GameWidok() {
        addKeyListener(new keyPressPlayer());
        setFocusable(true);
        setLayout(null);

        timer = new Timer(timerDelay, this);
        timer.start();
        lastFirstAidKit = System.currentTimeMillis();
        lastAsteroid = System.currentTimeMillis();
        lastAmmo = System.currentTimeMillis();
        gameInit();
        CompletableFuture.runAsync(Music::musicOfTheGame);
        healthBoss = new JPanel();
        healthBossBar = new JProgressBar(0, 15);
        healthBoss.setBounds(400, 15, 250, 20);
        healthBossBar.setPreferredSize(new Dimension(250, 20));
        healthBoss.setVisible(false);
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
        pauseButton.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "pause");
        pauseButton.getActionMap().put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                JFrame frameForDialog = new JFrame();
                JDialog jDialog = new JDialog(frameForDialog, "Pause", true);
                resumeButton = new JButton();
                resumeButton.setBounds(10, 10, 60, 60);
                resumeButton.setIcon(new ImageIcon("image\\resume.png"));
                resumeButton.setOpaque(false);
                resumeButton.setContentAreaFilled(false);
                resumeButton.setBorderPainted(false);
                resumeButton.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "resume");
                resumeButton.getActionMap().put("resume", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jDialog.setVisible(false);
                        timer.start();
                    }
                });

                closeButton = new JButton();
                closeButton.setBounds(100, 10, 60, 60);
                closeButton.setIcon(new ImageIcon("image\\exit.png"));
                closeButton.setOpaque(false);
                closeButton.setContentAreaFilled(false);
                closeButton.setBorderPainted(false);
                closeButton.addActionListener(c -> {
                    frameForDialog.dispose();
                    SwingUtilities.windowForComponent(GameWidok.this).dispose();
                    Music.setLoop(false);
                    Music.getPlayer().close();
                    ConnectionJDBC.save();
                    StartWidok.rankingMap.put(StartWidok.nickname, score);
                    new StartWidok().setVisible(true);
                });

                jDialog.setLayout(null);
                jDialog.add(closeButton);
                jDialog.add(resumeButton);
                jDialog.setSize(200, 120);
                jDialog.setLocationRelativeTo(null);
                jDialog.setVisible(true);
            }
        });
        add(pauseButton);
        add(scoreLabel);
        add(healthBoss);
        healthBoss.add(healthBossBar);
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
        asteroids = new ArrayList<>();
        ship = new Ship();
        shots = new ArrayList<>();
        firstAidKiT.checkVisibleFirstAidKit();
        ammo = new Ammo();
        boss = new Boss();
        boss.checkVisible();
        ammo.checkVisibleAmmo();
    }

    private void asteroids(Graphics g, Asteroid as) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(as.asteroid, as.getAt(), this);
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
            if ( timeNewAmmo + 4000 > System.currentTimeMillis()){
                g.drawImage(shot.img1, shot.getPosX()-5, shot.getPosY(), 20, 30, this);

            }else
                g.drawImage(shot.img, shot.getPosX(), shot.getPosY(), 10, 30, this);
        }

        for (int i = 0; i <= livePlayer.size(); i++) {
            g.drawImage(new ImageIcon("image\\serce.png").getImage(), getWidth() - (50 * i), getHeight() - 50, 30, 30, this);

        }

        for (Chicken[] chickens : chickenList) {
            for (Chicken chicken : chickens) {
                Chicken.ShotChicken shotChicken = chicken.getShotChicken();
                if (!shotChicken.isDestroyed()) {
                    g.drawImage(shotChicken.img, shotChicken.getX(), shotChicken.getY(), 10, 30, this);
                }
            }
        }

        if (chickensAlive == 0) {
            if (boss.isVisible()) {
                g.drawImage(boss.getImg(), boss.getPosX(), boss.getPosY(), 120, 120, this);
            }
        }
        if (ammo.isVisible()) {
            g.drawImage(ammo.getImg(), ammo.getPosX(), ammo.getPosY(), 40, 12, this);
        }


        if (firstAidKiT.isVisible()) {
            g.drawImage(firstAidKiT.getImg(), firstAidKiT.getPosX(), firstAidKiT.getPosY(), 40, 40, this);
        }
        for (Asteroid a : asteroids) {
          if(a.isVisible()){
              asteroids(g,a);
          }
        }


        repaint();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Iterator<Shot> shotIterator = shots.iterator();
        while (shotIterator.hasNext()) {
            Shot shot = shotIterator.next();
            if (shot.getPosY() > 0) {
                shot.move();
            } else {
                shotIterator.remove();
            }
        }

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
            shotPlayer();
            shotChickens();
        } else {
            boss.setVisible(true);
            bossHealth();
        }
        if (lastFirstAidKit + 25000 < System.currentTimeMillis()) {
            firstAidKiT = new FirstAidKiT();
            firstAidKiT.checkVisibleFirstAidKit();
            firstAidKiT.setVisible(true);
            lastFirstAidKit = System.currentTimeMillis();
        }
        if (lastAmmo + 30000 < System.currentTimeMillis()) {
            ammo = new Ammo();
            ammo.checkVisibleAmmo();
            ammo.setVisible(true);
            lastAmmo = System.currentTimeMillis();
        }
        if (ammo.isVisible()){
            ammo.move();
        }

        if (boss.isVisible()){
            timeAsteroid = 4000;
        }else
            timeAsteroid = 8000;


        if (lastAsteroid + timeAsteroid < System.currentTimeMillis()) {
            asteroids.add(new Asteroid());
            lastAsteroid = System.currentTimeMillis();

        }
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).move();

        }

        if (asteroids.size() > 0) {
            for (Asteroid a : asteroids) {
                if (ship.rectangle().intersects(a.rectangle()) && a.isVisible()) {
                    livePlayer.remove(livePlayer.size() - 1);
                    a.setVisible(false);
                    Music.musicExplosion();
                }
            }
        }

        if (ship.rectangle().intersects(boss.rectangle()) && boss.isTouch()) {
            livePlayer.remove(livePlayer.size() - 1);
            boss.setTouch(false);
            Music.musicExplosion();
            bossTouchShip = System.currentTimeMillis();
        }
        if (bossTouchShip + 2000 < System.currentTimeMillis()) {
            boss.setTouch(true);
        }

        if (ship.rectangle().intersects(ammo.rectangle())){
            timeNewAmmo = System.currentTimeMillis();
            ammo.setVisible(false);
        }

    firstAidKit();

    addLife();
        if (livePlayer.isEmpty())
            gameLose();
    repaint();
}


public class keyPressPlayer extends KeyAdapter {
    private long lastShoot = System.currentTimeMillis();

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT && ship.wspx - 5 > 0) {
            ship.wspx -= 15;
        }
        if (code == KeyEvent.VK_RIGHT && ship.wspx + 45 < getWidth()) {
            ship.wspx += 15;
        }
        if (code == KeyEvent.VK_UP && ship.wspy > 0) {
            ship.wspy -= 15;
        }
        if (code == KeyEvent.VK_DOWN && ship.wspy + 70 < getHeight()) {
            ship.wspy += 15;
        }

        if (code == KeyEvent.VK_SPACE && lastShoot + 500 < System.currentTimeMillis()) {
            shots.add(new Shot(ship.wspx+15, ship.wspy-30, 7));
            Music.musicShoot();
            lastShoot = System.currentTimeMillis();
        }
        repaint();
    }

}

    private void firstAidKit() {
        if (firstAidKiT.isVisible()) {
            firstAidKiT.move();
        }
    }

    private void bossHealth() {
        healthBoss.setVisible(true);
        healthBossBar.setValue(countBossHealth);
        healthBossBar.setBackground(Color.RED);
        healthBossBar.setForeground(Color.GREEN);
        if (boss.isVisible()) {
            boss.move();

        }
        shotPlayer();
        if (healthBossBar.getValue() == 0) {
            boss.setVisible(false);
            gameWin();
        }
    }

    private void addLife() {
        if (ship.rectangle().intersects(firstAidKiT.rectangle()) && firstAidKiT.isVisible()) {
            livePlayer.add(livePlayer.size() + 1);
            firstAidKiT.setVisible(false);
        }
    }

    private void shotPlayer() {
        shots.removeIf(this::checkCollision);
        shots.removeIf(this::check);
        shots.removeIf(this::checkColilisionAsteroid);
    }

    private boolean check(Shot shot) {
        if (shot.rectangle().intersects(boss.rectangle())) {
            countBossHealth--;
            return true;
        }
        return false;
    }

    private boolean checkCollision(Shot shot) {
        for (Chicken[] chickens : chickenList) {
            for (Chicken chicken : chickens) {
                if (shot.rectangle().intersects(chicken.rectangle()) && chicken.isVisible()) {
                    Music.musicShootChicken2();
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
private boolean checkColilisionAsteroid (Shot shot) {
    for (Asteroid a : asteroids) {
        if (shot.rectangle().intersects(a.rectangle()) && a.isVisible()) {
                a.setVisible(false);
                //asteroid.destructionAsteroid();
                Music.musicExplosion();
                score += 5000;
                scoreLabel.setText("Score: " + score);
                return true;
            }
        }
    return false;
}
    private void shotChickens() {
        for (Chicken[] chickens : chickenList) {
            for (Chicken chicken : chickens) {
                int shot = (int) (Math.random() * 150 + 1);
                Chicken.ShotChicken shotChicken = chicken.getShotChicken();
                if (shot == 150 && chicken.isVisible() && shotChicken.isDestroyed()) {
                    Music.musicShootChicken();
                    shotChicken.setDestroyed(false);
                    shotChicken.setX(chicken.getPosX());
                    shotChicken.setY(chicken.getPosY());
                }


                if (ship.isVisible() && !shotChicken.isDestroyed()) {
                    if (ship.rectangle().intersects(shotChicken.rectangleBomb())) {
                        livePlayer.remove(livePlayer.size() - 1);
                        shotChicken.setDestroyed(true);
                        Music.musicExplosion();
                    }
                }
                if (!shotChicken.isDestroyed()) {
                    shotChicken.setY(shotChicken.getY() + 2);
                    if (shotChicken.getY() >= 750) {
                        shotChicken.setDestroyed(true);
                    }
                }
            }
        }
    }

    private void gameWin() {
        Music.musicGameWin();
        timer.stop();
        int res = JOptionPane.showConfirmDialog(this, "YOU WIN!!!\n" + "Do you want to continue game", "Chicken Invaders", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            timer.start();
            chickensAlive = 55;
            countBossHealth = 15;
            healthBoss.setVisible(false);
            gameInit();
            Music.getClipGameWin().close();
        } else if (res == JOptionPane.OK_CANCEL_OPTION) {
            StartWidok.rankingMap.put(StartWidok.nickname, score);
            Music.setLoop(false);
            Music.getPlayer().close();
            ConnectionJDBC.save();
            SwingUtilities.windowForComponent(this).dispose();
            new StartWidok().setVisible(true);
        }
    }

    private void gameLose() {
        Music.musicGameOver();
        timer.stop();
        int res = JOptionPane.showConfirmDialog(this, "You lose.\n" + "Are you replay game?", "Chicken Invaders", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            timer.start();
            score = 0;
            scoreLabel.setText("Score: " + score);
            chickensAlive = 55;
            countBossHealth = 15;
            healthBoss.setVisible(false);
            gameInit();
            Music.getClipGameLose().close();
        } else if (res == JOptionPane.NO_OPTION) {
            StartWidok.rankingMap.put(StartWidok.nickname, score);
            Music.setLoop(false);
            Music.getPlayer().close();
            ConnectionJDBC.save();
            SwingUtilities.windowForComponent(this).dispose();
            new StartWidok().setVisible(true);
        }
    }

}