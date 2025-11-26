package GameEntity;

import static App.Point.PointsList;
import DataSources.Textures;
import lombok.Getter;
import lombok.Setter;

public class GameEntity {
    @Getter
    protected int textureId;

    @Getter @Setter
    protected Textures texture;

    @Setter @Getter
    protected int index;

    @Setter @Getter
    protected double speed;

    @Setter @Getter
    protected double x, y;

    @Setter @Getter
    protected boolean isMoving;

    @Getter
    private final int HEIGHT = 100, WIDTH = 100;

    public GameEntity(int textureId, int index, double speed) {
        this.textureId = textureId;
        this.setPosition(index);
        this.speed = speed;
    }

    public void setPosition(int index) {
        this.index = index;
        this.x = PointsList.get(this.index).getX();
        this.y = PointsList.get(this.index).getY();
    }

    public double getXView() {
        return this.x / (WIDTH / 2.0) - 0.9;
    }
    public double getYView() {
        return this.y / (HEIGHT / 2.0) - 0.9;
    }

    public void increaseX(double x) {
        this.x += x;
    }
    public void increaseY(double y) {
        this.y += y;
    }

    public boolean isTouched(double x, double y) {
        return Math.abs(this.y - y) <= 2 && Math.abs(this.x - x) <= 2;
    }

    public boolean isPointTouched(double x, double y) {
        return Math.abs(this.y - y) <= this.speed && Math.abs(this.x - x) <= this.speed;
    }
}
