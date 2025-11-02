package Movement;

import App.PacObject;
import App.Points;

import java.util.ArrayList;

public class MoveLeft implements MoveCommand {
    public MoveLeft() {
    }

    @Override
    public void execute(PacObject pacman, ArrayList<Points> pointsList, double speed) {
        int direction = pointsList.get(pacman.index).getLeft();

        pacman.isMoving = false;
        if (direction != -1) {
            if (direction == -2) {
                pacman.index = 18;
                pacman.x = pointsList.get(pacman.index).getX();
                pacman.y = pointsList.get(pacman.index).getY();
                pacman.isMoving = true;
                return;
            }

            if (Math.abs(pointsList.get(direction).getX() - pacman.x) <= speed) {
                pacman.index = direction;
                pacman.x = pointsList.get(direction).getX();
            } else {
                pacman.x -= speed;
                pacman.face = 3;
                pacman.isMoving = true;
            }
        }
    }
}
