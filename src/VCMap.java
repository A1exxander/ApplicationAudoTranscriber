import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import java.util.Map;
import java.util.HashMap;

public class VCMap {

    private static final Map<String, Integer> VCMap = new HashMap<>();

    static {
        VCMap.put("F1", NativeKeyEvent.VC_F1);
        VCMap.put("F2", NativeKeyEvent.VC_F2);
        VCMap.put("F3", NativeKeyEvent.VC_F3);
        VCMap.put("F4", NativeKeyEvent.VC_F4);
        VCMap.put("F12", NativeKeyEvent.VC_F12);
        VCMap.put("A", NativeKeyEvent.VC_A);
        VCMap.put("B", NativeKeyEvent.VC_B);
        VCMap.put("C", NativeKeyEvent.VC_C);
        VCMap.put("SPACE", NativeKeyEvent.VC_SPACE);
        VCMap.put("ENTER", NativeKeyEvent.VC_ENTER);
        VCMap.put("ESCAPE", NativeKeyEvent.VC_ESCAPE);
        VCMap.put("ALT", NativeKeyEvent.VC_ALT);
    }

    public static Integer getVCCode(String keyName) {
        if (keyName == null) {
            throw new NullPointerException("Key name provided was null!");
        }

        Integer virtualKeyCode = VCMap.get(keyName.toUpperCase()); // Ensure case-insensitivity
        if (virtualKeyCode == null) {
            throw new IllegalArgumentException("Invalid key name provided: " + keyName);
        }

        return virtualKeyCode;
    }


}
