import javax.sound.sampled.*;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        
        Scanner scanner = new Scanner(System.in);

        File file = new File("Your_Audio_File_Name");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        String response = "";

        try {
            while(!response.equals("Q")){
                System.out.println("P = Play, S = Stop, R = Reset, Q = Quit");
                System.out.print("Enter your choice: ");

                response = scanner.next();
                response = response.toUpperCase(); // when user typed lower case

                switch(response){
                    case("P"): clip.start();
                    break;
                    case("S"): clip.stop();
                    break;
                    case("R"): clip.setMicrosecondPosition(0);
                    break;
                    case("Q"): clip.close();
                    break;
                    default: System.out.println("Not a valid response");

                }
            }
            System.out.println("Byeee!");
        } finally {
            scanner.close();
        }
    }
}
