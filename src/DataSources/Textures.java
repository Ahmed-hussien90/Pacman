package DataSources;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum Textures {
    PacmanRight(
            new String[]{"pacman/r1.png", "pacman/r2.png", "pacman/r3.png"}
    ),

    PacmanLeft(
            new String[]{"pacman/l1.png", "pacman/l2.png", "pacman/l3.png"}
    ),

    PacmanTop(
            new String[]{"pacman/t1.png", "pacman/t2.png", "pacman/t3.png"}
    ),

    PacmanBottom(
            new String[]{"pacman/b1.png", "pacman/b2.png", "pacman/b3.png"}
    ),

    Ghost(
            new String[]{"ghosts/blinky.png", "ghosts/pinky.png", "ghosts/clyde.png", "ghosts/blue_ghost.png"}
    ),

    Fire(
            new String[]{"fire/t.png", "fire/b.png", "fire/l.png", "fire/r.png"},
            new double[]{0.075, 0.075}
    ),

    Point(
            new String[]{"extra/dot.png"},
            new double[]{0.075, 0.075}
    ),

    Fruit(
            new String[]{"extra/strawberry.png"},
            new double[]{0.03, 0.03}
    ),

    Texts(
            new String[]{"Ready.png", "GameOver.png", "Win.png"},
            new double[]{0.17, 0.13},
            new double[]{0, 0.07}
    ),

    Menu(
            new String[]{"menu.jpg"},
            new double[]{1, 1},
            new double[]{0, 0}
    ),

    Levels(
            new String[]{"levels.png"},
            new double[]{0.3, 0.3},
            new double[]{0, -0.6}
    ),

    Background(
            new String[]{"background.jpeg"},
            new double[]{1, 1},
            new double[]{0, 0}
    );

    @Getter
    private final String[] path;
    @Getter
    private double[] scale;
    @Getter
    private double[] position;
    @Getter
    private static final int total;

    private static final Map<Textures, Integer> startIndexMap = new HashMap<>();

    Textures(String[] path) {
        this.path = path;
    }

    Textures(String[] path, double[] scale) {
        this.path = path;
        this.scale = scale;
    }

    Textures(String[] path, double[] scale, double[] position) {
        this.path = path;
        this.scale = scale;
        this.position = position;
    }

    static {
        int index = 0;
        for (Textures t : Textures.values()) {
            startIndexMap.put(t, index);
            index += t.path.length;
        }
        total = index ;
    }

    public int getIndex(int index) {
        return startIndexMap.get(this) + index;
    }
}
