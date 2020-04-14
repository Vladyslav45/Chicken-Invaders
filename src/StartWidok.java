import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class StartWidok extends JFrame {
    static Map<String, Integer> rankingMap = new HashMap<>();
    static String nickname;


    private JButton buttonStart;
    private JButton buttonRanking;
    private Player player;
    private boolean loop = true;


    public StartWidok() {
        final int[] check = {-1};
        setLayout(null);
        setVisible(true);
        setLocation(500, 250);
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Chicken Invaders");
        setIconImage(new ImageIcon("image\\chicken0.png").getImage());

        CompletableFuture.runAsync(this::music);

        buttonStart = new JButton();
        buttonRanking = new JButton();

        JLabel background = new JLabel(new ImageIcon("image\\tlo.jpg"));
        background.setOpaque(true);
        background.setBounds(0, -10, 800, 600);

        buttonStart.setBounds(200, 300, 200, 200);
        buttonStart.setIcon(new ImageIcon("image\\play.png"));
        buttonStart.setOpaque(false);
        buttonStart.setContentAreaFilled(false);
        buttonStart.setBorderPainted(false);
        buttonStart.setFocusable(false);

        buttonRanking.setBounds(400, 300, 200, 200);
        buttonRanking.setIcon(new ImageIcon("image\\rank.png"));
        buttonRanking.setOpaque(false);
        buttonRanking.setContentAreaFilled(false);
        buttonRanking.setBorderPainted(false);
        buttonRanking.setFocusable(false);

        buttonStart.addActionListener(e -> {
            while (check[0] < 0) {
                nickname = JOptionPane.showInputDialog(null, "Enter nickname");
                if (nickname == null)
                    return;
                if (nickname.length() > 0) {
                    check[0]++;
                } else {
                    JOptionPane.showMessageDialog(null, "You didn't enter a nickname");
                }

            }

            rankingMap.put(nickname, 0);
            loop = false;
            player.close();
            musicAttack();
            dispose();
            new GameWindow().setVisible(true);
        });

        buttonRanking.addActionListener(e -> {
            int row = 0;
            JDialog jDialog = new JDialog(this, "Ranking");
            Map<String, Integer> sort = rankingMap.entrySet().stream().sorted(((o1, o2) -> o2.getValue().compareTo(o1.getValue()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o1, LinkedHashMap::new));
            JTable jTable = new JTable(sort.size(), 2);
            jTable.setEnabled(false);
            for (String key : sort.keySet()) {
                jTable.setValueAt(key, row, 0);
                jTable.setValueAt(sort.get(key), row, 1);
                row++;
            }
            jDialog.setIconImage(new ImageIcon("image\\rank1.png").getImage());
            jDialog.add(jTable);
            jDialog.setSize(300, 500);
            jDialog.setLocationRelativeTo(null);
            jDialog.setVisible(true);
        });


        add(buttonStart);
        add(buttonRanking);
        add(background);
    }

    private void music() {
        try {
            do {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("music\\ImperialMarch.mp3"));
                player = new Player(bufferedInputStream);
                player.play();
            } while (loop);
        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    private void musicAttack() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\chicken2.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException s) {
            s.printStackTrace();
        }
    }

}

