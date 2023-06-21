import javax.sound.sampled.*;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main1 {

    private static Clip clip;

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        // Use command line argument to take the audio file
        if (args.length != 1) {
            System.out.println("Usage: java Main Path/to/AudioFIleName.wav");
            return;
        }

        String audioFilePath = args[0];
        File file = new File(audioFilePath);

        if (!file.exists()) {
            System.out.println("File does not exist: " + audioFilePath);
            return;
        }

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);

        createGUI();
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Tomb's Boombox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);

        // Load background image
        ImageIcon imageIcon = new ImageIcon("boombox.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledImageIcon);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.getContentPane().add(backgroundLabel, 0); //add as the bottom layer



        frame.setLocationRelativeTo(null);
        JButton playButton = new JButton("Play");
        JButton stopButton = new JButton("Stop");
        JButton resetButton = new JButton("Reset");

        playButton.setBounds(50, 330, 100, 30);
        stopButton.setBounds(150, 330, 100, 30);
        resetButton.setBounds(250, 330, 100, 30);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        frame.add(playButton);
        frame.add(stopButton);
        frame.add(resetButton);

        frame.setVisible(true);
    }

    private static void play() {
        if (!clip.isRunning()) {
            clip.start();
        }
    }

    private static void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    private static void reset() {
        clip.setMicrosecondPosition(0);
    }
}
