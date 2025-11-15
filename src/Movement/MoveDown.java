package Movement;

import GameEntity.GameEntity;
import GameEntity.Pacman;
import App.Points;
import static DataSources.KeyCode.*;

public class MoveDown implements MoveCommand {
    @Override
    public void execute(GameEntity entity) {
        int direction = Points.PointsList.get(entity.getIndex()).getBottom();

        entity.setMoving(false);
        if(direction != -1){
            if (Math.abs(Points.PointsList.get(direction).getY() - entity.getY()) <= entity.getSpeed()) {
                entity.setIndex(direction);
                entity.setY(Points.PointsList.get(direction).getY());
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
        return Points.PointsList.get(entity.getIndex()).getBottom();
    }
}
