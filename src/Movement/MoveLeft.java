package Movement;

import GameEntity.GameEntity;
import GameEntity.Pacman;

import static App.Points.PointsList;
import static DataSources.KeyCode.*;

public class MoveLeft implements MoveCommand {
    @Override
    public void execute(GameEntity entity) {
        int direction = PointsList.get(entity.getIndex()).getLeft();

        entity.setMoving(false);
        if (direction != -1) {
            if (direction == -2) {
                entity.setIndex(18);
                entity.setX(PointsList.get(entity.getIndex()).getX());
                entity.setY(PointsList.get(entity.getIndex()).getY());
                entity.setMoving(true);
                return;
            }

            if (Math.abs(PointsList.get(direction).getX() - entity.getX()) <= entity.getSpeed()) {
                entity.setIndex(direction);
                entity.setX(PointsList.get(direction).getX());
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
        return PointsList.get(entity.getIndex()).getLeft();
    }
}
