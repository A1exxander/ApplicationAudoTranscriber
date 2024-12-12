package Utilities;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilsTest {

    @Nested
    class CopyToClipboardTests {

        @Test
        void works() throws IOException, UnsupportedFlavorException {

            String textToCopy = "test";

            Utils.copyToClipboard(textToCopy);

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String clipboardContents = (String) clipboard.getData(DataFlavor.stringFlavor);
            assertEquals(textToCopy, clipboardContents, "Clipboard content should match the copied text");

        }

    }

    @Nested
    class GetVCodeTests {

        private static Stream<Object[]> provideKeyCodes() {
            return Stream.of(
                    new Object[]{"F1", NativeKeyEvent.VC_F1},
                    new Object[]{"F2", NativeKeyEvent.VC_F2},
                    new Object[]{"F3", NativeKeyEvent.VC_F3},
                    new Object[]{"F4", NativeKeyEvent.VC_F4},
                    new Object[]{"F12", NativeKeyEvent.VC_F12},
                    new Object[]{"A", NativeKeyEvent.VC_A},
                    new Object[]{"B", NativeKeyEvent.VC_B},
                    new Object[]{"C", NativeKeyEvent.VC_C},
                    new Object[]{"SPACE", NativeKeyEvent.VC_SPACE},
                    new Object[]{"ENTER", NativeKeyEvent.VC_ENTER},
                    new Object[]{"ESCAPE", NativeKeyEvent.VC_ESCAPE},
                    new Object[]{"ALT", NativeKeyEvent.VC_ALT}
            );
        }
        @ParameterizedTest
        @MethodSource("provideKeyCodes")
        void returnsCorrectVCodeForValidKeys(String keyName, int expectedCode) {
            int actualCode = Utils.getVCode(keyName);
            assertEquals(expectedCode, actualCode);
        }

        @Test
        void throwsWhenNullVCode(){
            assertThrows(NullPointerException.class, ()-> Utils.getVCode(null));
        }

        @Test
        void providesDefaultWhenInvalidKeyNamePassed() {
            int defaultVCode = Utils.getVCode("invalid-key-name");
            int expectedDefaultVCode = NativeKeyEvent.VC_F2;
            assertEquals(defaultVCode, expectedDefaultVCode);
        }

    }

}