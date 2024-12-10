package AudioRecorder;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public interface iAudioRecorder {
    public void record() throws LineUnavailableException;
    public byte[] stopRecording();
    public boolean isRecording();
}
