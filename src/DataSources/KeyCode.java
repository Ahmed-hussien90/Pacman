package DataSources;

public enum KeyCode {
    UP(38),
    DOWN(40),
    LEFT(37),
    RIGHT(39);

    private final int code;

    KeyCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
