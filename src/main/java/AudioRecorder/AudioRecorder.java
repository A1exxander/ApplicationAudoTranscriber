package AudioRecorder;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioRecorder implements iAudioRecorder {

    private TargetDataLine audioInputLine;
    private AudioFormat audioFormat;
    private ByteArrayOutputStream outputAudioStream;
    private ExecutorService recordAudioExecutor;
    private boolean isRecording;

    public AudioRecorder() {

        audioFormat = new AudioFormat(
                16000,  // Sample rate
                16,     // Sample size in bits
                1,      // Channels (mono)
                true,   // Signed
                false   // Little-endian
        );

        recordAudioExecutor = Executors.newSingleThreadExecutor();
        isRecording = false;

    }

    @Override
    public void record() throws LineUnavailableException {

        if (isRecording) {
            throw new IllegalStateException("Cannot begin recording audio! Audio is already being recorded.");
        }

        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

        if (!AudioSystem.isLineSupported(dataLineInfo)) {
            throw new LineUnavailableException("System audio line not supported");
        }

        outputAudioStream = new ByteArrayOutputStream();
        audioInputLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        audioInputLine.open(audioFormat);
        audioInputLine.start();
        isRecording = true;

        recordAudioExecutor.execute(()-> {
            byte[] buffer = new byte[4096];
            while (isRecording) {
                int bytesRead = audioInputLine.read(buffer, 0, buffer.length);
                if (bytesRead > 0) {
                    outputAudioStream.write(buffer, 0, bytesRead);
                }
            }
        });

    }

    @Override
    public byte[] stopRecording() {

        if (!isRecording) {
            throw new IllegalStateException("No recording in progress");
        }

        audioInputLine.stop();
        audioInputLine.close();
        isRecording = false;

        return outputAudioStream.toByteArray();

    }

    @Override
    public boolean isRecording() { return isRecording; }

}
