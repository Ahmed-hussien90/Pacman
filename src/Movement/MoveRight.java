package Movement;

import App.Pacman;
import App.Points;


public class MoveRight implements MoveCommand {
    public MoveRight() {
    }

    @Override
    public void execute(Pacman pacman, double speed) {
        int direction = Points.PointsList.get(pacman.index).getRight();

        pacman.isMoving = false;
        if (direction != -1) {
            if (direction == -2) {
                pacman.index = 66;
                pacman.setX(Points.PointsList.get(pacman.index).getX());
                pacman.setY(Points.PointsList.get(pacman.index).getY());
                return;
            }

            if (Math.abs(Points.PointsList.get(direction).getX() - pacman.getX()) <= speed) {
                pacman.index = direction;
                pacman.setX(Points.PointsList.get(direction).getX());
            } else {
                pacman.setX(pacman.getX() +speed);
                pacman.setFace(0);
                pacman.isMoving = true;
            }
        }
    }
}
