public class points {
    private double x ;
    private double y;
    private int top;
    private int left;
    private int bottom;
    private int right;

    public points(double x, double y, int top, int bottom, int left, int right) {
        this.x = x;
        this.y = y;
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
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

    public void setTop(int top) {
        this.top = top;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
