package Movement;

import GameEntity.GameEntity;
import GameEntity.Pacman;

import static App.Points.PointsList;
import static DataSources.KeyCode.*;

public class MoveDown implements MoveCommand {
    @Override
    public void execute(GameEntity entity) {
        int direction = PointsList.get(entity.getIndex()).getBottom();

        entity.setMoving(false);
        if(direction != -1){
            if (Math.abs(PointsList.get(direction).getY() - entity.getY()) <= entity.getSpeed()) {
                entity.setIndex(direction);
                entity.setY(PointsList.get(direction).getY());
            } else {
                entity.setY(entity.getY() -entity.getSpeed());
                if(entity instanceof Pacman) {
                    ((Pacman) entity).setTexture(DOWN);
                }
                entity.setMoving(true);
            }
        }
    }

    public int getTarget(GameEntity entity){
        return PointsList.get(entity.getIndex()).getBottom();
    }
}
