package DataSources;

import lombok.Getter;

public enum Sound {
    Begin("pacman_beginning.wav"),
    PointEaten("pacman_chomp.wav"),
    FruitEaten("pacman_eatfruit.wav"),
    Victory("Victory.wav"),
    Death("pacman_death.wav"),
    ;



    @Getter
    private final String sound;
    Sound(String sound) {
        this.sound = sound;
    }
}
