package App;

import DataSources.KeyCode;
import static DataSources.Textures.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

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
    private boolean isMoving, isDead;

    @Setter @Getter
    private double speed;

    @Getter
    private KeyCode faceDirection;

    @Getter
    private int health = 100;

    @Getter
    private LinkedList<Integer> homePath = new LinkedList<>();

    Pacman(int texture, int index, double speed) {
        this.texture = texture;
        this.index = index;
        this.x = Points.PointsList.get(this.index).getX();
        this.y = Points.PointsList.get(this.index).getY();
        this.random = 37 + (int) (Math.random() * 4);
        this.speed = speed;
        this.faceDirection = KeyCode.RIGHT;
    }

    public void setFace(KeyCode direction) {
        this.faceDirection = direction;
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

    public void setHomePath(int to) {
        this.homePath = ShortestPath.findPath(this.index, to);
    }

    public KeyCode getTargetKeyCode(int targetIndex) {
        if(Points.PointsList.get(this.index).getTop() == targetIndex) return KeyCode.UP;

        else if(Points.PointsList.get(this.index).getBottom() == targetIndex) return KeyCode.DOWN;

        else if(Points.PointsList.get(this.index).getLeft() == targetIndex) return KeyCode.LEFT;

        return KeyCode.RIGHT;
    }

    public void decreaseHealth(int points, double speed) {
        if(this.health <= 0) return;

        this.health -= points;
        if (this.health <= 0) {
            this.health = 0;
            this.speed = speed;
            this.texture = Ghost.getIndex(3);
            this.isDead = true;
            this.setHomePath(82);
        }
    }
}
