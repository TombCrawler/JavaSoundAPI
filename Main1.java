import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

// for DJ
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Main1 {

    private static Clip clip;
    private static float volume = 1.0f; // Declare variable as Default volume
    private static float balance = 0.0f; // Default balance

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
        Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledImageIcon);
        backgroundLabel.setBounds(0, -90, frame.getWidth(), frame.getHeight());
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

        // volume slider
        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (volume * 100));
        volumeSlider.setBounds(50, 280, 300, 30);
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    volume = value / 100.0f;
                    adjustVolume();
                }
            }
        });
        // Create a label for the volume slider
        JLabel volumeLabel = new JLabel("Volume");
        volumeLabel.setBounds(50, 270, 100, 20);

        // Balance slider
        JSlider balanceSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, (int) (balance * 100));
        balanceSlider.setBounds(50, 230, 300, 30);
        balanceSlider.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    balance = value / 100.0f;
                    adjustBalance();
                }
            }
        });
        // Create a label for the balance slider
        JLabel balanceLabel = new JLabel("Pan");
        balanceLabel.setBounds(50, 220, 100, 20);


        frame.add(playButton);
        frame.add(stopButton);
        frame.add(resetButton);
        frame.add(volumeSlider);
        frame.add(volumeLabel);
        frame.add(balanceSlider);
        frame.add(balanceLabel);

        frame.setVisible(true);
    }

    private static void play() {
        if (!clip.isRunning()) {
            adjustVolume();
            adjustBalance();
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

private static void adjustVolume() {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

private static void adjustBalance() {
        if (clip != null) {
            FloatControl balanceControl = (FloatControl) clip.getControl(FloatControl.Type.BALANCE);
            balanceControl.setValue(balance);
        }
    }
}