import music.Music;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class StartWidok extends JFrame {
    static Map<String, Integer> rankingMap = new HashMap<>();
    static String nickname;

    private JButton buttonStart;
    private JButton buttonRanking;


    public StartWidok() {
        ConnectionJDBC.con();
        final int[] check = {-1};
        setLayout(null);
        setUndecorated(true);
        initMenuBar();
        setVisible(true);
        setLocation(500, 250);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("image\\chicken0.png").getImage());

        CompletableFuture.runAsync(Music::music);
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
                if (rankingMap.entrySet().stream().anyMatch(key -> key.getKey().equals(nickname))){
                    JOptionPane.showMessageDialog(null, "Nickname is already used!");
                } else if (nickname.length() > 0) {
                    check[0]++;
                } else {
                    JOptionPane.showMessageDialog(null, "You didn't enter a nickname");
                }


            }

            rankingMap.put(nickname, 0);
            Music.setLoop(false);
            Music.getPlayer().close();
            Music.musicAttack();
            dispose();
            new GameWindow();
        });

        buttonRanking.addActionListener(e -> {
            ConnectionJDBC.showRanking();
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

    public void initMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        JButton iconified = new JButton();
        JButton frame = new JButton();
        JButton close = new JButton();
        JMenu jMenu = new JMenu();

        JMenuItem github = new JMenuItem("GIT");
        JMenuItem exit = new JMenuItem("Exit");
        github.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/dawwik/Chicken-Invaders"));
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        });
        exit.addActionListener(e -> System.exit(0));

        jMenu.setText("Chickens Invaders");
        jMenu.setIcon(new ImageIcon("image\\chicken.png"));
        jMenu.add(github);
        jMenu.add(exit);

        jMenuBar.setPreferredSize(new Dimension(getWidth(), 30));
        jMenuBar.add(jMenu);
        jMenuBar.add(Box.createGlue());

        iconified.setIcon(new ImageIcon("image\\minimize.png"));
        iconified.setOpaque(false);
        iconified.setContentAreaFilled(false);
        iconified.setBorderPainted(false);
        iconified.addActionListener(e -> setState(Frame.ICONIFIED));

        frame.setIcon(new ImageIcon("image\\window.png"));
        frame.setOpaque(false);
        frame.setContentAreaFilled(false);
        frame.setBorderPainted(false);
        frame.setEnabled(false);

        close.setIcon(new ImageIcon("image\\close.png"));
        close.setOpaque(false);
        close.setContentAreaFilled(false);
        close.setBorderPainted(false);
        close.addActionListener(e -> System.exit(0));


        jMenuBar.add(iconified);
        jMenuBar.add(frame);
        jMenuBar.add(close);
        setJMenuBar(jMenuBar);
    }

}

