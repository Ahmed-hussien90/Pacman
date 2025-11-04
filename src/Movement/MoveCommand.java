package Movement;

import App.Pacman;
import App.Points;

import java.util.List;


public interface MoveCommand {
    void execute(Pacman pacman, double speed);
}
