package DataSources;

import lombok.Getter;

@Getter
public enum KeyCode {
    UP(38),
    DOWN(40),
    LEFT(37),
    RIGHT(39);

    private final int code;
    private KeyCode opposite;

    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
    }

    KeyCode(int code) {
        this.code = code;
    }

    public static KeyCode getKeyCode(int code) {
        for (KeyCode keyCode : KeyCode.values()) {
            if (keyCode.code == code) {
                return keyCode;
            }
        }

        return null;
    }

    public static boolean isOpposite(KeyCode k1, KeyCode k2) {
        return k2 == k1.opposite;
    }
}
