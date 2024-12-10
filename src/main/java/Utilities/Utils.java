package Utilities;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class Utils {

    public static Integer getVCCode(String keyName) {

        if (keyName == null) {
            throw new NullPointerException("Key name provided was null!");
        }

        switch (keyName.toUpperCase()) {
            case "F1":
                return NativeKeyEvent.VC_F1;
            case "F2":
                return NativeKeyEvent.VC_F2;
            case "F3":
                return NativeKeyEvent.VC_F3;
            case "F4":
                return NativeKeyEvent.VC_F4;
            case "F12":
                return NativeKeyEvent.VC_F12;
            case "A":
                return NativeKeyEvent.VC_A;
            case "B":
                return NativeKeyEvent.VC_B;
            case "C":
                return NativeKeyEvent.VC_C;
            case "SPACE":
                return NativeKeyEvent.VC_SPACE;
            case "ENTER":
                return NativeKeyEvent.VC_ENTER;
            case "ESCAPE":
                return NativeKeyEvent.VC_ESCAPE;
            case "ALT":
                return NativeKeyEvent.VC_ALT;
            default:
                System.err.println("\nInvalid recording toggle key detected. Defaulting to F2's key code.");
                return NativeKeyEvent.VC_F2;
        }

    }

    public static void copyToClipboard(String text) {
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new StringSelection(text),
                        null
                );
    }

}
