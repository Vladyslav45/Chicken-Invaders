import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StartWidok extends JFrame {
    static Map<String, Integer> rankingMap = new HashMap<>();
    static String nickname;

    private JButton buttonStart;
    private JButton buttonRanking;
    private Clip clip;

    public StartWidok() {
        setLayout(null);
        setVisible(true);
        setLocation(500, 250);
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("image\\chicken.png").getImage());

        music();

        buttonStart = new JButton();
        buttonRanking = new JButton();

        JLabel background = new JLabel(new ImageIcon("image\\tlo.jpg"));
        background.setOpaque(true);
        background.setBounds(0, -10, 800, 600);
        add(background);

        buttonStart.setBounds(200, 300, 200, 200);
        buttonStart.setIcon(new ImageIcon("image\\play.png"));
        buttonStart.setOpaque(false);
        buttonStart.setContentAreaFilled(false);
        buttonStart.setBorderPainted(false);

        buttonRanking.setBounds(400, 300, 200, 200);
        buttonRanking.setIcon(new ImageIcon("image\\rank.png"));
        buttonRanking.setOpaque(false);
        buttonRanking.setContentAreaFilled(false);
        buttonRanking.setBorderPainted(false);

        buttonStart.addActionListener(e -> {
            nickname = JOptionPane.showInputDialog("Enter nickname");
            rankingMap.put(nickname, 0);
            clip.stop();
            musicAttack();
            dispose();
            new GameWidok().setVisible(true);

        });

        buttonRanking.addActionListener(e -> {
            int row = 0;
            JDialog jDialog = new JDialog(this, "Ranking");
            Map<String, Integer> sort = rankingMap.entrySet().stream().sorted(((o1, o2) -> o2.getValue().compareTo(o1.getValue()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o1, LinkedHashMap::new));
            JTable jTable = new JTable(sort.size(), 2);
            for (String key : sort.keySet()) {
                jTable.setValueAt(key, row, 0);
                jTable.setValueAt(sort.get(key), row, 1);
                row++;
            }
            jDialog.setIconImage(new ImageIcon("image\\rank1.png").getImage());
            jDialog.add(jTable);
            jDialog.setSize(300, 500);
            jDialog.setLocation(1000, 250);
            jDialog.setVisible(true);
        });


        add(buttonStart);
        add(buttonRanking);
        add(background);
    }

    private void music() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\imperial_march.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void musicAttack() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\chicken2.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException s) {
            s.printStackTrace();
        }
    }
}
