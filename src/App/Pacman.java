package App;

import lombok.Getter;
import lombok.Setter;

public class Pacman {
    @Setter @Getter
    private double x, y;
    @Setter @Getter
    private int face, faceAnimated;
    @Setter @Getter
    private int texture;

    @Setter @Getter
    private int index, random;
    @Setter @Getter
    private boolean isMoving;

    Pacman(int texture, int index) {
        this.texture = texture;
        this.index = index;
        this.x = Points.PointsList.get(this.index).getX();
        this.y = Points.PointsList.get(this.index).getY();
    }
    Pacman(int texture, int index, int random) {
        this.texture = texture;
        this.index = index;
        this.random = random;
        this.x = Points.PointsList.get(this.index).getX();
        this.y = Points.PointsList.get(this.index).getY();
    }

    public int getFaceAnimated() {
        if(this.isMoving) {
            return (this.face + ((this.faceAnimated++) % 3) );
        }

        return this.face;
    }

    public double[] getPositionView() {
        return new double[]{this.x / (100 / 2.0) - 0.9, this.y / (100 / 2.0) - 0.9};
    }
}
