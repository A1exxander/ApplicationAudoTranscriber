package AudioRecorder;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
                44100,  // Sample rate
                16,     // Sample size in bits
                2,      // Channels (stereo)
                true,   // Signed
                false   // Big endian
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

        audioInputLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        audioInputLine.open(audioFormat);
        audioInputLine.start();

        outputAudioStream = new ByteArrayOutputStream();
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

        // Stop recording
        isRecording = false;
        audioInputLine.stop();
        audioInputLine.close();

        // Return recorded audio as byte array
        return outputAudioStream.toByteArray();

    }

    public void saveRecording(byte[] audioData, String filePath) throws IOException {
        // Save recording to a WAV file
        AudioInputStream audioInputStream = new AudioInputStream(
                new java.io.ByteArrayInputStream(audioData),
                audioFormat,
                audioData.length / audioFormat.getFrameSize()
        );

        AudioSystem.write(
                audioInputStream,
                AudioFileFormat.Type.WAVE,
                new File(filePath)
        );

    }

    @Override
    public boolean isRecording() { return isRecording; }

}
