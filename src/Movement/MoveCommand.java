package Movement;

import App.Pacman;

public interface MoveCommand {
    void execute(Pacman pacman);

    int getTarget(Pacman pacman);
}
