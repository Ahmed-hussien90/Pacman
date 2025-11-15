package Movement;

import GameEntity.GameEntity;

public interface MoveCommand {
    void execute(GameEntity entity);

    int getTarget(GameEntity entity);
}
