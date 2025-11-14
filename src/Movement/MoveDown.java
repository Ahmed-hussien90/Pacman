package Movement;

import App.Pacman;
import App.Points;
import static DataSources.KeyCode.*;

public class MoveDown implements MoveCommand {
    @Override
    public void execute(Pacman pacman) {
        int direction = Points.PointsList.get(pacman.getIndex()).getBottom();

        pacman.setMoving(false);
        if(direction != -1){
            if (Math.abs(Points.PointsList.get(direction).getY() - pacman.getY()) <= pacman.getSpeed()) {
                pacman.setIndex(direction);
                pacman.setY(Points.PointsList.get(direction).getY());
            } else {
                pacman.setY(pacman.getY() -pacman.getSpeed());
                pacman.setFace(DOWN);
                pacman.setMoving(true);
            }
        }
    }

    public int getTarget(Pacman pacman){
        return Points.PointsList.get(pacman.getIndex()).getBottom();
    }
}
