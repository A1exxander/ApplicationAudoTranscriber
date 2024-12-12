package AudioKeyListener;

import AudioRecorder.iAudioRecorder;
import AudioTranscriber.iAudioTranscriber;
import Utilities.Utils;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class AudioKeyListener implements NativeKeyListener {

    private final Integer recordToggleKeyCode;
    private final iAudioRecorder audioRecorder;
    private final iAudioTranscriber audioTranscriber;

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
                System.err.println("Failed to begin recording!");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {

        if (nativeEvent.getKeyCode() == recordToggleKeyCode && audioRecorder.isRecording()) {
            System.out.println("Stopping audio recording...");
            byte[] recordedAudio = audioRecorder.stopRecording();
            System.out.println("Transcribing audio...");
            try {
                String transcribedText = audioTranscriber.transcribe(recordedAudio);
                Utils.copyToClipboard(transcribedText);
                System.out.println("Audio successfully transcribed & copied to clipboard!");
            } catch (IOException e) {
                System.err.println("Failed to transcribe audio!");
                e.printStackTrace();
            }
        }

    }

}
