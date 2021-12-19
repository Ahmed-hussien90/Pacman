public class points {
    private double x ;
    private double y;
    private points top;
    private points left;
    private points bottom;
    private points right;

    public points(double x, double y, points top, points left, points bottom, points right) {
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

    public points getTop() {
        return top;
    }

    public points getLeft() {
        return left;
    }

    public points getBottom() {
        return bottom;
    }

    public points getRight() {
        return right;
    }
}
