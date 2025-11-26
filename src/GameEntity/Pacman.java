package GameEntity;

import java.util.List;

import DataSources.KeyCode;

import static DataSources.Textures.*;

public class Pacman extends GameEntity {
    private int faceAnimated;

    public Pacman(int texture, int index, double speed) {
        super(texture, index, speed);
        this.texture = PacmanRight;
    }

    public void setTexture(KeyCode direction) {
        this.textureId = switch (direction) {
            case RIGHT -> PacmanRight.getIndex(0);
            case LEFT -> PacmanLeft.getIndex(0);
            case UP -> PacmanTop.getIndex(0);
            case DOWN -> PacmanBottom.getIndex(0);
        };
    }

    public int getTextureId() {
        this.faceAnimated = this.isMoving ? ++this.faceAnimated % 3 : 0;

        return this.textureId + this.faceAnimated;
    }

    public boolean isKilled(List<Enemy> enemies) {
        boolean result = false;

        for (Enemy e : enemies) {
            result |= !e.isDead() && this.isTouched(e.getX(), e.getY());
        }

        return result;
    }

    public boolean isWon() {
        return App.Point.getNoOfViewedPoints() == 0;
    }
}
