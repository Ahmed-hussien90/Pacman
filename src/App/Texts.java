package App;

public class Texts {
    int index;
    boolean isAppear;

    public Texts(int index, boolean isAppear) {
        this.index = index;
        this.isAppear = isAppear;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isAppear() {
        return isAppear;
    }

    public void setAppear(boolean appear) {
        isAppear = appear;
    }
}
