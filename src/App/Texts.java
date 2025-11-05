package App;

import lombok.Getter;
import lombok.Setter;

public class Texts {
    @Getter
    private final int index;

    @Setter @Getter
    private boolean isAppear;

    public Texts(int index, boolean isAppear) {
        this.index = index;
        this.isAppear = isAppear;
    }

    public double[] getPositionView() {
        return new double[]{0, 0.07};
    }

    public double[] getScale() {
        return new double[]{0.17, 0.13};
    }
}
