package GameEntity;

import App.ShortestPath;
import DataSources.KeyCode;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Random;

import static DataSources.Textures.Ghost;

public class Enemy extends GameEntity {
    @Getter
    private KeyCode random;

    @Setter @Getter
    private int health;

    @Getter
    private boolean isDead;

    @Getter
    private LinkedList<Integer> homePath;

    public Enemy(int texture, int index, double speed) {
        super(texture, index, speed);
        this.setRandom();
        this.texture = Ghost;
        this.health = 100;
        this.homePath = new LinkedList<>();
    }

    public void setHomePath(int to) {
        this.homePath = ShortestPath.findPath(this.index, to);
    }

    public void setRandom() {
        KeyCode[] keyCodes = KeyCode.values();
        this.random = keyCodes[new Random().nextInt(keyCodes.length)];
    }

    public void decreaseHealth(int points) {
        if(this.health <= 0) return;

        this.health -= points;

        if (this.health <= 0) {
            this.setDead(true);
        }
    }

    public void setDead(boolean b) {
        this.isDead = b;

        if(this.isDead) {
            this.setHealth(0);
            this.setSpeed(speed * 3);

            int[] choices = {80, 82, 83};
            this.setHomePath(choices[new Random().nextInt(choices.length)]);
        }else {
            this.setHealth(100);
            this.setSpeed(speed / 3);
        }
    }
}
