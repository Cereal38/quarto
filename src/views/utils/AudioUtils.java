package src.views.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioUtils {
    private Clip clip;

    public void loadAudio(String filePath) {
        try {
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
            if (inputStream != null) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

                AudioFormat baseFormat = audioInputStream.getFormat();
                AudioFormat decodedFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        baseFormat.getSampleRate(),
                        16,
                        baseFormat.getChannels(),
                        baseFormat.getChannels() * 2,
                        baseFormat.getSampleRate(),
                        false
                );

                AudioInputStream decodedAudioInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);

                clip = AudioSystem.getClip();
                clip.open(decodedAudioInputStream);
            } else {
                System.err.println("Unable to load audio file: " + filePath);
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {

            clip.start();
            //reduce volume
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
        } else {
            System.err.println("Audio clip is null. Make sure to load audio before playing.");
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}
