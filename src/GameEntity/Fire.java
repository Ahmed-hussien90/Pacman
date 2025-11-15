package GameEntity;

import DataSources.KeyCode;
import DataSources.Textures;
import lombok.Getter;

import static DataSources.Textures.Fire;

public class Fire extends GameEntity {
    @Getter
    private final KeyCode faceDirection;

    public Fire(Textures texture, Pacman pacman, KeyCode faceDirection) {
        super(texture.getIndex(faceDirection), pacman.getIndex(), pacman.speed * 3);
        this.x = pacman.getX();
        this.y = pacman.getY();
        this.texture = Fire;
        this.faceDirection = faceDirection;
    }
}
