package Movement;

import App.Pacman;
import App.Points;

public class MoveUp implements MoveCommand {
    public MoveUp() {
    }

    @Override
    public void execute(Pacman pacman, double speed) {
        int direction = Points.PointsList.get(pacman.getIndex()).getTop();

        pacman.setMoving(false);
        if (direction != -1) {
            if (Math.abs(Points.PointsList.get(direction).getY() - pacman.getY()) <= speed) {
                pacman.setIndex(direction);
                pacman.setY(Points.PointsList.get(direction).getY());
            } else {
                pacman.setY(pacman.getY() +speed);
                pacman.setFace(6);
                pacman.setMoving(true);

            }
        }
    }
}
