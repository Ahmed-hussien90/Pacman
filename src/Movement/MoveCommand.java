package Movement;

import DataSources.KeyCode;
import GameEntity.GameEntity;

import java.util.Map;

import static DataSources.KeyCode.*;
import static DataSources.KeyCode.RIGHT;

public interface MoveCommand {
    Map<KeyCode, MoveCommand> movements = Map.of(
            UP, new MoveUp(),
            DOWN, new MoveDown(),
            LEFT, new MoveLeft(),
            RIGHT, new MoveRight()
    );

    void execute(GameEntity entity);

    int getTarget(GameEntity entity);
}
