package AudioKeyListener;
import AudioRecorder.iAudioRecorder;
import AudioTranscriber.iAudioTranscriber;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class AudioKeyListener implements NativeKeyListener {

    private Integer recordToggleKeyCode;
    private boolean isRecording = false;
    private iAudioRecorder audioRecorder;
    private iAudioTranscriber audioTranscriber;

    public AudioKeyListener() {

    }

    public AudioKeyListener(int recordToggleKeyCode, iAudioRecorder audioRecorder, iAudioTranscriber audioTranscriber) {
        this.recordToggleKeyCode = recordToggleKeyCode;
        this.audioRecorder = audioRecorder;
        this.audioTranscriber = audioTranscriber;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) { return; } // Unnecessary

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {

        if (nativeEvent.getKeyCode() == recordToggleKeyCode) {

        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {

        if (nativeEvent.getKeyCode() == recordToggleKeyCode) {

        }

    }

}
