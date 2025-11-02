package Movement;

import App.PacObject;
import App.Points;

import java.util.ArrayList;

public class MoveUp implements MoveCommand {
    public MoveUp() {
    }

    @Override
    public void execute(PacObject pacman, ArrayList<Points> pointsList, double speed) {
        int direction = pointsList.get(pacman.index).getTop();

        pacman.isMoving = false;
        if (direction != -1) {
            if (Math.abs(pointsList.get(direction).getY() - pacman.y) <= speed) {
                pacman.index = direction;
                pacman.y = pointsList.get(direction).getY();
            } else {
                pacman.y += speed;
                pacman.face = 6;
                pacman.isMoving = true;
            }
        }
    }
}
