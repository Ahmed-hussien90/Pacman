package App;

public class PacObject {
    public double x, y;
    public int texture, index, random;
    public int face = 0;
    public boolean isMoving = false;

    PacObject(int texture,int index) {
        this.texture = texture;
        this.index = index;
//        this.x = PointsList.get(this.index).getX();
//        this.y = PointsList.get(this.index).getY();
    }
    PacObject(int texture, int index, int random) {
        this.texture = texture;
        this.index = index;
        this.random = random;
//        this.x = PointsList.get(this.index).getX();
//        this.y = PointsList.get(this.index).getY();
    }
}
