package App;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.CompletableFuture;

public class SoundPlayer {

    private static final int BUFFER_SIZE = 4096;

    public static void playAsync(String path, Runnable onEnd) {
        CompletableFuture.runAsync(() -> {
            try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path))) {
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                try (SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info)) {
                    audioLine.open(format);
                    audioLine.start();

                    byte[] buffer = new byte[BUFFER_SIZE];
                    int bytesRead;

                    while ((bytesRead = audioStream.read(buffer)) != -1) {
                        audioLine.write(buffer, 0, bytesRead);
                    }

                    audioLine.drain();
                    audioLine.stop();

                    if (onEnd != null) onEnd.run();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
