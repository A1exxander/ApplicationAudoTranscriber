import AudioKeyListener.AudioKeyListener;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ApplicationAudioTranscriber {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NativeHookException {
        int recordToggleVKKey = (args.length == 0) ? KeyEvent.class.getField("VK_F2").getInt(null) : parseRecordToggleVKKey(args[0]);
        NativeKeyListener audioKeyListener = new AudioKeyListener();
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(audioKeyListener);
    }

    private static int parseRecordToggleVKKey(String recordToggleKey) throws NoSuchFieldException, IllegalAccessException {
        int recordToggleKeyVK = 0;
        try {
            recordToggleKeyVK = Utils.parseVKKey(recordToggleKey);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("\nInvalid recording toggle key detected. Defaulting to F2.");
            recordToggleKeyVK = KeyEvent.class.getField("VK_RMENU").getInt(null); // If this throws an exception, it is critical & should crash
        }
        finally {
            return recordToggleKeyVK;
        }
    }

}