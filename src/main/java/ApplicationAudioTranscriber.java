import AudioKeyListener.AudioKeyListener;
import AudioRecorder.AudioRecorder;
import AudioTranscriber.AudioTranscriber;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class ApplicationAudioTranscriber {

    public static void main(String[] args) throws NativeHookException {
        int recordToggleKeyCode = (args.length == 0) ? NativeKeyEvent.VC_F2 : Utils.getVCCode(args[0]);
        AudioKeyListener audioKeyListener = new AudioKeyListener(recordToggleKeyCode, new AudioRecorder(), new AudioTranscriber());
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(audioKeyListener);
    }

    /*
        TODO: 1. Refactor Utils to a Utils class
              2. Refactor our getVCCode method to use switch case
              3. Implement AudioTranscription using Vosk
              4. Add transcription code to KeyListener handler
              5. Add Util method to copy to clipboard
              6. Update to use system wide audio as input instead of microphone
              7. Dockerize
     */

}