package AudioKeyListener;
import AudioRecorder.iAudioRecorder;
import AudioTranscriber.iAudioTranscriber;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class AudioKeyListener implements NativeKeyListener {

    Integer recordToggleKeyCode;
    boolean isRecordingAudio;
    iAudioRecorder audioRecorder;
    iAudioTranscriber audioTranscriber;

    public AudioKeyListener() {

    }

    public AudioKeyListener(int recordToggleKeyCode, iAudioRecorder audioRecorder, iAudioTranscriber audioTranscriber) {
        this.recordToggleKeyCode = recordToggleKeyCode;
        this.audioRecorder = audioRecorder;
        this.audioTranscriber = audioTranscriber;
        System.out.println(recordToggleKeyCode);
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
