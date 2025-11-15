package Movement;

import App.Points;
import GameEntity.GameEntity;
import GameEntity.Pacman;

import static DataSources.KeyCode.*;

public class MoveUp implements MoveCommand {
    @Override
    public void execute(GameEntity entity) {
        int direction = Points.PointsList.get(entity.getIndex()).getTop();

        entity.setMoving(false);
        if (direction != -1) {
            if (Math.abs(Points.PointsList.get(direction).getY() - entity.getY()) <= entity.getSpeed()) {
                entity.setIndex(direction);
                entity.setY(Points.PointsList.get(direction).getY());
            } else {
                entity.setY(entity.getY() +entity.getSpeed());
                if(entity instanceof Pacman) {
                    ((Pacman) entity).setTexture(UP);
                }
                entity.setMoving(true);
            }
        }
    }

    public int getTarget(GameEntity entity){
        return Points.PointsList.get(entity.getIndex()).getTop();
    }
}
