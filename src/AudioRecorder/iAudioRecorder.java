package AudioRecorder;

import javax.sound.sampled.LineUnavailableException;

public interface iAudioRecorder {
    public void record() throws LineUnavailableException;
    public byte[] stopRecording();
    public boolean isRecording();
}
