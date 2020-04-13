package music;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Music {

    private static Clip clipGameWin;
    private static Clip clipGameLose;
    private static Player player;
    private static boolean loop = true;

    public static void setLoop(boolean loop) {
        Music.loop = loop;
    }

    public static Player getPlayer() {
        return player;
    }

    public static Clip getClipGameWin() {
        return clipGameWin;
    }

    public static Clip getClipGameLose() {
        return clipGameLose;
    }


    public static void musicShootChicken2() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\shotChicken.wav").getAbsoluteFile());
            Clip clipShoot = AudioSystem.getClip();
            clipShoot.open(audioInputStream);
            FloatControl floatControl = (FloatControl) clipShoot.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(-2f);
            clipShoot.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException h) {
            h.printStackTrace();
        }
    }

    public static void musicShoot() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\shoot.wav").getAbsoluteFile());
            Clip clipShoot = AudioSystem.getClip();
            clipShoot.open(audioInputStream);
            FloatControl floatControl = (FloatControl) clipShoot.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(-5f);
            clipShoot.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException h) {
            h.printStackTrace();
        }
    }

    public static void musicShootChicken() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\shoot.wav").getAbsoluteFile());
            Clip clipShoot = AudioSystem.getClip();
            clipShoot.open(audioInputStream);
            FloatControl floatControl = (FloatControl) clipShoot.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(-17f);
            clipShoot.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException h) {
            h.printStackTrace();
        }
    }
    public static void musicOfTheGame() {
        try {
            do {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("music\\gamemusic.mp3"));
                player = new Player(bufferedInputStream);
                player.play();
            } while (loop);
        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public static void musicGameWin() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\GameWin.wav").getAbsoluteFile());
            clipGameWin = AudioSystem.getClip();
            clipGameWin.open(audioInputStream);
            FloatControl floatControl = (FloatControl) clipGameWin.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(-10f);
            clipGameWin.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException h) {
            h.printStackTrace();
        }
    }
    public static void musicExplosion() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\explosion.wav").getAbsoluteFile());
            clipGameWin = AudioSystem.getClip();
            clipGameWin.open(audioInputStream);
            FloatControl floatControl = (FloatControl) clipGameWin.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(-10f);
            clipGameWin.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException h) {
            h.printStackTrace();
        }
    }
    public static void musicGameOver() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music\\GameOver.wav").getAbsoluteFile());
            clipGameLose = AudioSystem.getClip();
            clipGameLose.open(audioInputStream);
            FloatControl floatControl = (FloatControl) clipGameLose.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(-10f);
            clipGameLose.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException h) {
            h.printStackTrace();
        }
    }
    }
