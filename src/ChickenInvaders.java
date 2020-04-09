import java.awt.*;

public class ChickenInvaders {
    public static void main(String[] args) {
        EventQueue.invokeLater(StartWidok::new);
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    StartWidok frame = new StartWidok();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}