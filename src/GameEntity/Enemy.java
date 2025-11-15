package GameEntity;

import App.Points;
import App.ShortestPath;
import App.SoundPlayer;
import DataSources.KeyCode;
import static DataSources.Sound.*;
import static App.Points.PointsList;

import Movement.MoveCommand;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

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

    public void setHomePath(int end) {
        int size = PointsList.keySet().stream().max(Integer::compare).get() + 1;

        this.homePath = ShortestPath.findPath(this.index, end, size, (current) -> {
            Points p = PointsList.get(current.node());

            List<ShortestPath.Node> dir = new ArrayList<>();

            addIfValid(dir, p, p.getTop(),    false);
            addIfValid(dir, p, p.getBottom(), false);
            addIfValid(dir, p, p.getLeft(),   true);
            addIfValid(dir, p, p.getRight(),  true);

            return dir;
        });

        if(this.isMoving) {
            this.index = MoveCommand.movements.get(this.getRandom()).getTarget(this);
        }
    }

    private void addIfValid(List<ShortestPath.Node> dir, Points p, int next, boolean useX) {
        if (next >= 0) {
            Points np = PointsList.get(next);
            dir.add(new ShortestPath.Node(next, Math.abs(useX ? p.getX() - np.getX() : p.getY() - np.getY())));
        }
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
            SoundPlayer.playAsync(EnemyKilled, null);
        }
    }

    public void setDead(boolean b) {
        this.isDead = b;

        if(this.isDead) {
            this.setHealth(0);
            int[] choices = {80, 82, 83};
            this.setHomePath(choices[new Random().nextInt(choices.length)]);
        }else {
            this.setHealth(100);
        }
    }
}
