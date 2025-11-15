package DataSources;

import lombok.Getter;

public enum Sound {
    Begin("pacman_beginning.wav"),
    PointEaten("pacman_eatpoint.wav"),
    FruitEaten("pacman_eatfruit.wav"),
    EnemyKilled("enemy_killed.wav"),
    Victory("Victory.wav"),
    Death("pacman_death.wav"),
    PacmanFire("pacman_fire.wav"),
    ;



    @Getter
    private final String sound;
    Sound(String sound) {
        this.sound = "Assets/sounds/" + sound;
    }
}
