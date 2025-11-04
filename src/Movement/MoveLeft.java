package Movement;

import App.Pacman;
import App.Points;


public class MoveLeft implements MoveCommand {
    public MoveLeft() {
    }

    @Override
    public void execute(Pacman pacman, double speed) {
        int direction = Points.PointsList.get(pacman.getIndex()).getLeft();

        pacman.setMoving(false);
        if (direction != -1) {
            if (direction == -2) {
                pacman.setIndex(18);
                pacman.setX(Points.PointsList.get(pacman.getIndex()).getX());
                pacman.setY(Points.PointsList.get(pacman.getIndex()).getY());
                pacman.setMoving(true);
                return;
            }

            if (Math.abs(Points.PointsList.get(direction).getX() - pacman.getX()) <= speed) {
                pacman.setIndex(direction);
                pacman.setX(Points.PointsList.get(direction).getX());
            } else {
                pacman.setX(pacman.getX() -speed);
                pacman.setFace(3);
                pacman.setMoving(true);
            }
        }
    }
}
