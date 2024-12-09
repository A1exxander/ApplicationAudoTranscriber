package AudioKeyListener;
import AudioRecorder.iAudioRecorder;
import AudioTranscriber.iAudioTranscriber;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class AudioKeyListener implements NativeKeyListener {

    iAudioRecorder audioRecorder;
    iAudioTranscriber audioTranscriber;

    public AudioKeyListener() {

    }

    public AudioKeyListener(iAudioRecorder audioRecorder, iAudioTranscriber audioTranscriber) {
        this.audioRecorder = audioRecorder;
        this.audioTranscriber = audioTranscriber;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {

    }

}
