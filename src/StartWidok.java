import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StartWidok extends JFrame {
    static Map<String, Integer> rankingMap = new HashMap<>();
    static String nickname;
    private JLabel jLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JButton buttonStart;
    private JButton buttonRanking;
    private Clip clip;


    public StartWidok(){
        setLayout(null);
        setVisible(true);
        setLocation(500,250);
        setSize(800,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        music();
        Font font = new Font("Verdana", Font.BOLD,24);
        jLabel = new JLabel("CHICKEN");
        jLabel1 = new JLabel("INVADERS");
        jLabel2 = new JLabel("chicken strike back");
        buttonStart = new JButton();
        buttonRanking = new JButton();


        jLabel.setBounds(300,30,150,20);
        jLabel.setFont(font);

        jLabel1.setBounds(300,60,70,20);

        jLabel2.setBounds(300,100,120,10);

        buttonStart.setBounds(200,150,200,200);
        buttonStart.setIcon(new ImageIcon("image\\play.png"));
        buttonStart.setOpaque(false);
        buttonStart.setContentAreaFilled(false);
        buttonStart.setBorderPainted(false);

        buttonRanking.setBounds(400, 150,200,200);
        buttonRanking.setIcon(new ImageIcon("image\\rank.png"));
        buttonRanking.setOpaque(false);
        buttonRanking.setContentAreaFilled(false);
        buttonRanking.setBorderPainted(false);

        buttonStart.addActionListener(e -> {
            nickname = JOptionPane.showInputDialog("Enter nickname");
            rankingMap.put(nickname, 0);
            dispose();
            clip.stop();
            new GameWidok().setVisible(true);

        });

        buttonRanking.addActionListener(e -> {
            int row = 0;
            JDialog jDialog = new JDialog(this, "Ranking");
            Map<String, Integer> sort = rankingMap.entrySet().stream().sorted(((o1, o2) -> o2.getValue().compareTo(o1.getValue()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1,o2) -> o1, LinkedHashMap::new));
            JTable jTable = new JTable(sort.size(),2);
            for (String key : sort.keySet()){
                jTable.setValueAt(key, row, 0);
                jTable.setValueAt(sort.get(key), row, 1);
                row++;
            }
            jDialog.add(jTable);
            jDialog.setSize(300,500);
            jDialog.setLocation(1000,250);
            jDialog.setVisible(true);
        });

        add(jLabel);
        add(jLabel1);
        add(jLabel2);
        add(buttonStart);
        add(buttonRanking);
    }

    private void music(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\imperial_march.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
