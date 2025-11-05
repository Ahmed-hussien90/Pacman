package Movement;

import App.Pacman;

public interface MoveCommand {
    void execute(Pacman pacman, double speed);

    int getTarget(Pacman pacman);
}
