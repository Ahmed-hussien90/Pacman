package Movement;

import App.PacObject;
import App.Points;

import java.util.ArrayList;


public interface MoveCommand {
    void execute(PacObject pacman, ArrayList<Points> pointsList, double speed);
}
