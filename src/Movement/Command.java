package Movement;

import App.Point;
import DataSources.KeyCode;
import GameEntity.GameEntity;

import java.util.Map;

import static DataSources.KeyCode.*;
import static DataSources.KeyCode.RIGHT;

public interface Command {
    Map<KeyCode, Command> movements = Map.of(
        UP   , new MovementCommand(1 , Point::getTop),
        DOWN , new MovementCommand(-1, Point::getBottom),
        LEFT , new MovementCommand(-1, Point::getLeft),
        RIGHT, new MovementCommand(1 , Point::getRight)
    );

    void execute(GameEntity entity);

    int getTarget(GameEntity entity);
}
