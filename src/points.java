public class points {
    private int index;
    private double x ;
    private double y;
    private int top;
    private int left;
    private int bottom;
    private int right;
    private boolean isChecked;

    public points(int index,double x, double y, int top, int bottom, int left, int right,boolean isChecked) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.isChecked = isChecked;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getTop() {
        return top;
    }
    public int getLeft() {
        return left;
    }
    public int getBottom() {
        return bottom;
    }
    public int getRight() {
        return right;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public int getIndex() {
        return index;
    }
    public boolean isChecked() {
        return isChecked;
    }
}
