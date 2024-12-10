import AudioKeyListener.AudioKeyListener;
import AudioRecorder.AudioRecorder;
import AudioTranscriber.VoskAudioTranscriber;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;

import java.io.IOException;

public class ApplicationAudioTranscriber {

    public static void main(String[] args) throws NativeHookException, IOException {
        final int recordToggleKeyCode = (args.length == 0) ? NativeKeyEvent.VC_F2 : Utils.getVCCode(args[0]);
        final String audioTranscriptionModelPath = "C:\\Users\\Alex\\IdeaProjects\\ApplicationAudioTranscriber\\src\\main\\java\\AudioTranscriber\\model\\vosk-model-small-en-us-0.15";
        LibVosk.setLogLevel(LogLevel.WARNINGS);
        AudioKeyListener audioKeyListener = new AudioKeyListener(recordToggleKeyCode, new AudioRecorder(), new VoskAudioTranscriber(new Model(audioTranscriptionModelPath)));
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(audioKeyListener);
        System.out.println("Successfully initialized transcriber!");
    }

    /*
        TODO: 1. Add transcription code to KeyListener handler
              2. Add Util method to copy to clipboard
              3. Update to use system wide audio as input instead of microphone
              4. Dockerize application
              5. Create test cases & write unit tests
     */

}