package DataSources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@AllArgsConstructor
public enum Textures {
    PacmanRight(
            new String[]{"pacman/r1.png", "pacman/r2.png", "pacman/r3.png"},
            new double[]{0.05, 0.05}
    ),

    PacmanLeft(
            new String[]{"pacman/l1.png", "pacman/l2.png", "pacman/l3.png"},
            new double[]{0.05, 0.05}
    ),

    PacmanTop(
            new String[]{"pacman/t1.png", "pacman/t2.png", "pacman/t3.png"},
            new double[]{0.05, 0.05}
    ),

    PacmanBottom(
            new String[]{"pacman/b1.png", "pacman/b2.png", "pacman/b3.png"},
            new double[]{0.05, 0.05}
    ),

    Ghost(
            new String[]{"ghosts/blinky.png", "ghosts/pinky.png", "ghosts/clyde.png", "ghosts/blue_ghost.png"},
            new double[]{0.05, 0.05}
    ),

    Fire(
            new String[]{"fire/t.png", "fire/b.png", "fire/l.png", "fire/r.png"},
            new double[]{0.075, 0.075}
    ),

    Dot(
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
    private final double[] scale;
    @Getter
    private double[] position;
    @Getter
    private static final int total;

    private static final Map<Textures, Integer> startIndexMap = new HashMap<>();

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
    public int getIndex(KeyCode keyCode) {
        return startIndexMap.get(this) + keyCode.ordinal();
    }
}
