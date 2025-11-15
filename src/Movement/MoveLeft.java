package Movement;

import GameEntity.GameEntity;
import GameEntity.Pacman;
import App.Points;
import static DataSources.KeyCode.*;

public class MoveLeft implements MoveCommand {
    @Override
    public void execute(GameEntity entity) {
        int direction = Points.PointsList.get(entity.getIndex()).getLeft();

        entity.setMoving(false);
        if (direction != -1) {
            if (direction == -2) {
                entity.setIndex(18);
                entity.setX(Points.PointsList.get(entity.getIndex()).getX());
                entity.setY(Points.PointsList.get(entity.getIndex()).getY());
                entity.setMoving(true);
                return;
            }

            if (Math.abs(Points.PointsList.get(direction).getX() - entity.getX()) <= entity.getSpeed()) {
                entity.setIndex(direction);
                entity.setX(Points.PointsList.get(direction).getX());
            } else {
                entity.setX(entity.getX() -entity.getSpeed());
                if(entity instanceof Pacman) {
                    ((Pacman) entity).setTexture(LEFT);
                }
                entity.setMoving(true);
            }
        }
    }

    public int getTarget(GameEntity entity){
        return Points.PointsList.get(entity.getIndex()).getLeft();
    }
}
