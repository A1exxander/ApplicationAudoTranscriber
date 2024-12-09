import java.awt.event.KeyEvent;

public class Utils {

    public static int parseVKKey(String keyName) throws NoSuchFieldException, IllegalAccessException {
        String vkKey = keyName.startsWith("VK_") ? keyName : "VK_" + keyName.toUpperCase();
        return KeyEvent.class.getField(vkKey).getInt(null);
    }

}
