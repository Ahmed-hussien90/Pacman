package DataSources;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum Textures {
    PacmanRight(new String[]{"pacman/r1.png", "pacman/r2.png", "pacman/r3.png"}),

    PacmanLeft(new String[]{"pacman/l1.png", "pacman/l2.png", "pacman/l3.png"}),

    PacmanTop(new String[]{"pacman/t1.png", "pacman/t2.png", "pacman/t3.png"}),

    PacmanBottom(new String[]{"pacman/b1.png", "pacman/b2.png", "pacman/b3.png"}),

    Ghost(new String[]{"ghosts/blinky.png", "ghosts/pinky.png", "ghosts/clyde.png"}),

    Point(new String[]{"extra/dot.png"}),

    Fruits(new String[]{"extra/strawberry.png"}),

    Texts(new String[]{"Ready.png", "GameOver.png", "Win.png"}),

    Menu(new String[]{"menu.jpg"}),

    Levels(new String[]{ "levels.png"}),

    Background(new String[]{"background.jpeg"});

    @Getter
    private final String[] path;
    private static final Map<Textures, Integer> startIndexMap = new HashMap<>();


    Textures(String[] path) {
        this.path = path;
    }

    static {
        int index = 0;
        for (Textures t : Textures.values()) {
            startIndexMap.put(t, index);
            index += t.path.length;
        }
    }

    public int getIndex(int index) {
        return startIndexMap.get(this) + index;
    }
}
