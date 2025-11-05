package App;

import DataSources.KeyCode;
import static DataSources.Textures.*;
import lombok.Getter;
import lombok.Setter;

public class Pacman {
    @Setter @Getter
    private double x, y;
    @Getter
    private int face, faceAnimated;
    @Setter @Getter
    private int texture;

    @Setter @Getter
    private int index;

    private int random;
    @Setter @Getter
    private boolean isMoving;

    Pacman(int index) {
        this.index = index;
    }

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

    public void setFace(KeyCode direction) {
        this.face = switch (direction) {
            case RIGHT -> PacmanRight.getIndex(0);
            case LEFT -> PacmanLeft.getIndex(0);
            case UP -> PacmanTop.getIndex(0);
            case DOWN -> PacmanBottom.getIndex(0);
        };
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

    public double[] getScale() {
        return new double[]{0.05, 0.05};
    }

    public void setRandom() {
        this.random = 37 + (int) (Math.random() * 4);
    }

    public KeyCode getRandom() {
        return KeyCode.getKeyCode(this.random);
    }
}
