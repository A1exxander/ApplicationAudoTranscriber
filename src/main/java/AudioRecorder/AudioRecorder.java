package AudioRecorder;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioRecorder implements iAudioRecorder {

    private final AudioFormat audioFormat;
    private TargetDataLine audioInputLine;
    private final ExecutorService recordAudioExecutor;
    private ByteArrayOutputStream outputAudioStream;
    private boolean isRecording;

    public AudioRecorder(AudioFormat audioFormat) throws LineUnavailableException {
        this.audioFormat = audioFormat;
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        audioInputLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        recordAudioExecutor = Executors.newSingleThreadExecutor();
        isRecording = false;
    }

    public AudioRecorder(AudioFormat audioFormat, TargetDataLine audioInputLine, ExecutorService recordAudioExecutor) throws LineUnavailableException {
        this.audioFormat = audioFormat;
        this.audioInputLine = audioInputLine;
        this.recordAudioExecutor = recordAudioExecutor;
        isRecording = false;
    }

    @Override
    public void record() throws LineUnavailableException {

        if (isRecording) {
            throw new IllegalStateException("Cannot begin recording audio! Audio is already being recorded.");
        }

        outputAudioStream = new ByteArrayOutputStream();
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

    public void setAudioInputLine(TargetDataLine audioInputLine) {

        if (isRecording || this.audioInputLine.isOpen()) {
            throw new IllegalStateException("Cannot change audio input line while recording is in progress.");
        }
        else if (audioInputLine == null) {
            throw new NullPointerException("Audio input line cannot be set to null.");
        }

        this.audioInputLine = audioInputLine;

    }


}