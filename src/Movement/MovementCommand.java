package Movement;

import App.Point;
import GameEntity.GameEntity;
import lombok.AllArgsConstructor;
import java.util.function.ToIntFunction;
import static App.Point.PointsList;

@AllArgsConstructor
public class MovementCommand implements Command {
    private final int signDirection;
    private final ToIntFunction<Point> direction;

    @Override
    public void execute(GameEntity entity) {
        int dir = getTarget(entity);
        entity.setMoving(false);
        if(dir != -1){
            if (entity.isPointTouched(PointsList.get(dir).getX(), PointsList.get(dir).getY())) {
                entity.setPosition(dir);
                if((entity.getIndex() == 18 && getTarget(entity) == 66) || (entity.getIndex() == 66 && getTarget(entity) == 18)){
                    entity.setPosition(getTarget(entity));
                    entity.setMoving(true);
                }
            } else {
                if(entity.getX() == PointsList.get(dir).getX()){
                    entity.increaseY(signDirection * entity.getSpeed());
                }else if(entity.getY() == PointsList.get(dir).getY()){
                    entity.increaseX(signDirection * entity.getSpeed());
                }
                entity.setMoving(true);
            }
        }
    }

    public int getTarget(GameEntity entity){
        return direction.applyAsInt(PointsList.get(entity.getIndex()));
    }
}
