package App;

import lombok.Getter;
import lombok.Setter;

public class Texts {
    @Getter
    private int index;

    @Setter @Getter
    private boolean isAppear;

    public Texts(int index, boolean isAppear) {
        this.index = index;
        this.isAppear = isAppear;
    }
}
