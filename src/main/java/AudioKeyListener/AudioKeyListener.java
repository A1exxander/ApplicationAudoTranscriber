package AudioKeyListener;

import AudioRecorder.iAudioRecorder;
import AudioTranscriber.iAudioTranscriber;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.sound.sampled.LineUnavailableException;

public class AudioKeyListener implements NativeKeyListener {

    private Integer recordToggleKeyCode;
    private iAudioRecorder audioRecorder;
    private iAudioTranscriber audioTranscriber;

    public AudioKeyListener(int recordToggleKeyCode, iAudioRecorder audioRecorder, iAudioTranscriber audioTranscriber) {
        this.recordToggleKeyCode = recordToggleKeyCode;
        this.audioRecorder = audioRecorder;
        this.audioTranscriber = audioTranscriber;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeEvent) { return; } // Unnecessary

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {

        if (nativeEvent.getKeyCode() == recordToggleKeyCode && !audioRecorder.isRecording()) {
            System.out.println("\nRecording audio...");
            try {
                audioRecorder.record();
            } catch (LineUnavailableException e) {
                System.err.println("\nFailed to begin recording!");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {

        if (nativeEvent.getKeyCode() == recordToggleKeyCode && audioRecorder.isRecording()) {
            System.out.println("\nStopping audio recording...");
            byte[] recordedAudio = audioRecorder.stopRecording();
        }

    }

}
