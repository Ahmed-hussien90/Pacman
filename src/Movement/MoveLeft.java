package Movement;

import App.Pacman;
import App.Points;
import static DataSources.KeyCode.*;

public class MoveLeft implements MoveCommand {
    @Override
    public void execute(Pacman pacman) {
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

            if (Math.abs(Points.PointsList.get(direction).getX() - pacman.getX()) <= pacman.getSpeed()) {
                pacman.setIndex(direction);
                pacman.setX(Points.PointsList.get(direction).getX());
            } else {
                pacman.setX(pacman.getX() -pacman.getSpeed());
                pacman.setFace(LEFT);
                pacman.setMoving(true);
            }
        }
    }

    public int getTarget(Pacman pacman){
        return Points.PointsList.get(pacman.getIndex()).getLeft();
    }
}
