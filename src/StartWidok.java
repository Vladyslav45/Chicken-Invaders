import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StartWidok extends JFrame {
    Map<String, Integer> rankingMap = new HashMap<>();
    private JLabel jLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JButton buttonStart;
    private JButton buttonRanking;

    public StartWidok(){
        setLayout(null);

        music();
        Font font = new Font("Verdana", Font.BOLD,24);
        jLabel = new JLabel("CHICKEN");
        jLabel1 = new JLabel("INVADERS");
        jLabel2 = new JLabel("chicken strike back");
        buttonStart = new JButton("Start");
        buttonRanking = new JButton("Ranking");


        jLabel.setBounds(350,100,150,20);
        jLabel.setFont(font);
        jLabel1.setBounds(350,120,70,20);
        jLabel2.setBounds(350,200,120,10);
        buttonStart.setBounds(350,250,80,20);
        buttonRanking.setBounds(350, 300,100,20);

        buttonStart.addActionListener(e -> {
            String nickname = JOptionPane.showInputDialog("Enter nickname");
            rankingMap.put(nickname, 0);
            new GameWidok();
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
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("imperial_march.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
