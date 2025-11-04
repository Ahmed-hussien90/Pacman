package Movement;

import App.Pacman;
import App.Points;


public class MoveDown implements MoveCommand {
    public MoveDown() {
    }

    @Override
    public void execute(Pacman pacman, double speed) {
        int direction = Points.PointsList.get(pacman.index).getBottom();

        pacman.isMoving = false;
        if(direction != -1){
            if (Math.abs(Points.PointsList.get(direction).getY() - pacman.getY()) <= speed) {
                pacman.index = direction;
                pacman.setY(Points.PointsList.get(direction).getY());
            } else {
                pacman.setY(pacman.getY() -speed);
                pacman.setFace(9);
                pacman.isMoving = true;
            }
        }
    }
}
