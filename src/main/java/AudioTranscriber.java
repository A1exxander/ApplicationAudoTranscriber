import AudioKeyListener.AudioKeyListener;
import AudioRecorder.AudioRecorder;
import AudioTranscriber.VoskAudioTranscriber;
import Utilities.Utils;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.nio.file.Paths;

public class AudioTranscriber {

    public static void main(String[] args) throws NativeHookException, IOException, LineUnavailableException {

        final int recordToggleKeyCode = (args.length == 0) ? NativeKeyEvent.VC_F2 : Utils.getVCode(args[0]);
        final String audioTranscriptionModelPath = Paths.get("src","main", "resources", "vosk-model-small-en-us-0.15").toAbsolutePath().toString();
        final AudioFormat audioFormat = new AudioFormat(16000, 16, 1,true,  false);
        LibVosk.setLogLevel(LogLevel.WARNINGS);
        AudioKeyListener audioKeyListener = new AudioKeyListener(recordToggleKeyCode, new AudioRecorder(audioFormat), new VoskAudioTranscriber(audioFormat, new Model(audioTranscriptionModelPath)));
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(audioKeyListener);
        System.out.println("\nSuccessfully initialized transcriber!");

    }

    /*
        TODO: 1. Minor refactorings
              2. Update to use system wide audio as input instead of microphone - Seems to be bugged w loopback device
              3. Dockerize application
     */

}