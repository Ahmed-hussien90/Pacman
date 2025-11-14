package App;

import DataSources.KeyCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import DataSources.Textures;
import static DataSources.Textures.Fruit;
import static DataSources.Textures.Point;

public class Points {
    @Getter
    private final Textures texture;
    @Getter
    private final double x, y;
    @Getter
    private final int top, left, bottom, right;
    @Setter @Getter
    private boolean isEaten;

    public static Map<Integer, Points> PointsList = Map.<Integer, Points>ofEntries(
            Map.entry(1 , new Points(Point,45    , 38.75 , -1    , -1    , 50    , 2     , false)),
            Map.entry(2 , new Points(Point,61.25 , 38.75 , 3     , 67    , 1     , -1    , false)),
            Map.entry(3 , new Points(Point,61.25 , 48    , 4     , 2     , -1    , 17    , false)),
            Map.entry(4 , new Points(Point,61.25 , 58    , -1    , 3     , 5     , -1    , false)),
            Map.entry(5 , new Points(Point,50.5  , 58    , 6     , -1    , 81    , 4     , false)),
            Map.entry(6 , new Points(Point,50.5  , 68    , -1    , 5     , -1    , 7     , false)),
            Map.entry(7 , new Points(Point,61.25 , 68    , 8     , -1    , 6     , -1    , false)),
            Map.entry(8 , new Points(Point,61.25 , 77.5  , -1    , 7     , 9     , 16    , false)),
            Map.entry(9 , new Points(Point,50.5  , 77.5  , 10    , -1    , 57    , 8     , false)),
            Map.entry(10, new Points(Point,50.5  , 90.5  , -1    , 9     , -1    , 11    , false)),
            Map.entry(11, new Points(Point,71.5  , 90.5  , -1    , 16    , 10    , 12    , false)),
            Map.entry(12, new Points(Point,90    , 90.5  , -1    , 13    , 11    , -1    , false)),
            Map.entry(13, new Points(Point,90    , 77.5  , 12    , 14    , 16    , -1    , false)),
            Map.entry(14, new Points(Point,90    , 68    , 13    , -1    , 15    , -1    , false)),
            Map.entry(15, new Points(Point,71.5  , 68    , 16    , 71    , -1    , 14    , false)),
            Map.entry(16, new Points(Point,71.5  , 77.5  , 11    , 15    , 8     , 13    , false)),
            Map.entry(17, new Points(Point,71.5  , 48    , 71    , 19    , 3     , 18    , false)),
            Map.entry(18, new Points(Point,90    , 48    , -1    , -1    , 17    , -2    , false)),
            Map.entry(19, new Points(Point,71.5  , 29    , 17    , 30    , 67    , 20    , false)),
            Map.entry(20, new Points(Point,90    , 29    , -1    , 21    , 19    , -1    , false)),
            Map.entry(21, new Points(Point,90    , 19    , 20    , -1    , 22    , -1    , false)),
            Map.entry(22, new Points(Point,82    , 19    , -1    , 23    , -1    , 21    , false)),
            Map.entry(23, new Points(Point,82    , 10    , 22    , -1    , 31    , 24    , false)),
            Map.entry(24, new Points(Point,90    , 10    , -1    , 25    , 23    , -1    , false)),
            Map.entry(25, new Points(Point,90    , 0     , 24    , -1    , 69    , -1    , false)),
            Map.entry(26, new Points(Point,50.5  , 0     , 27    , -1    , 38    , 69    , false)),
            Map.entry(27, new Points(Point,50.5  , 10    , -1    , 26    , -1    , 28    , false)),
            Map.entry(28, new Points(Point,61.25 , 10    , 29    , -1    , 27    , -1    , false)),
            Map.entry(29, new Points(Point,61.25 , 19    , -1    , 28    , 33    , 30    , false)),
            Map.entry(30, new Points(Point,71.5  , 19    , 19    , 31    , 29    , -1    , false)),
            Map.entry(31, new Points(Point,71.5  , 10    , 30    , -1    , -1    , 23    , false)),
            Map.entry(32, new Points(Point,50.5  , 29    , -1    , 33    , -1    , 67    , false)),
            Map.entry(33, new Points(Point,50.5  , 19    , 32    , -1    , 34    , 29    , false)),
            Map.entry(34, new Points(Point,39.5  , 19    , 49    , -1    , 35    , 33    , false)),
            Map.entry(35, new Points(Point,29    , 19    , -1    , 36    , 46    , 34    , false)),
            Map.entry(36, new Points(Point,29    , 10    , 35    , -1    , -1    , 37    , false)),
            Map.entry(37, new Points(Point,39.5  , 10    , -1    , 38    , 36    , -1    , false)),
            Map.entry(38, new Points(Point,39.5  , 0     , 37    , -1    , 68    , 26    , false)),
            Map.entry(39, new Points(Point,0     , 0     , 40    , -1    , -1    , 68    , false)),
            Map.entry(40, new Points(Point,0     , 10    , -1    , 39    , -1    , 41    , false)),
            Map.entry(41, new Points(Point,8     , 10    , 42    , -1    , 40    , 47    , false)),
            Map.entry(42, new Points(Point,8     , 19    , -1    , 41    , 43    , -1    , false)),
            Map.entry(43, new Points(Point,0     , 19    , 44    , -1    , -1    , 42    , false)),
            Map.entry(44, new Points(Point,0     , 29    , -1    , 43    , -1    , 45    , false)),
            Map.entry(45, new Points(Point,18    , 29    , 65    , 46    , 44    , 48    , false)),
            Map.entry(46, new Points(Point,18    , 19    , 45    , 47    , -1    , 35    , false)),
            Map.entry(47, new Points(Point,18    , 10    , 46    , -1    , 41    , -1    , false)),
            Map.entry(48, new Points(Point,29    , 29    , 50  , -1    , 45    , 49    , false)),
            Map.entry(49, new Points(Point,39.5  , 29    , -1    , 34    , 48    , -1    , false)),
            Map.entry(50, new Points(Point,29    , 38.75 , 51    , 48    , -1    , 1     , false)),
            Map.entry(51, new Points(Point,29    , 48    , 52    , 50    , 65    , -1    , false)),
            Map.entry(52, new Points(Point,29    , 58    , -1    , 51    , -1    , 53    , false)),
            Map.entry(53, new Points(Point,39.5  , 58    , 54    , -1    , 52    , 81    , false)),
            Map.entry(54, new Points(Point,39.5  , 68    , -1    , 53    , 55    , -1    , false)),
            Map.entry(55, new Points(Point,29    , 68    , 56    , -1    , -1    , 54    , false)),
            Map.entry(56, new Points(Point,29    , 77.5  , -1    , 55    , 64    , 57    , false)),
            Map.entry(57, new Points(Point,39.5  , 77.5  , 58    , -1    , 56    , 9     , false)),
            Map.entry(58, new Points(Point,39.5  , 90.5  , -1    , 57    , 72    , -1    , false)),
            Map.entry(59, new Points(Point,18    , 90.5  , -1    , 64    , 60    , 72    , false)),
            Map.entry(60, new Points(Point,0     , 90.5  , -1    , 61    , -1    , 59    , false)),
            Map.entry(61, new Points(Point,0     , 77.5  , 60    , 62    , -1    , 64    , false)),
            Map.entry(62, new Points(Point,0     , 68    , 61    , -1    , -1    , 63    , false)),
            Map.entry(63, new Points(Point,18    , 68    , 64    , 70    , 62    , -1    , false)),
            Map.entry(64, new Points(Point,18    , 77.5  , 59    , 63    , 61    , 56    , false)),
            Map.entry(65, new Points(Point,18    , 48    , 70    , 45    , 66    , 51    , false)),
            Map.entry(66, new Points(Point,0     , 48    , -1    , -1    , -2    , 65    , false)),
            Map.entry(67, new Points(Point,61.25 , 29    , 2     , -1    , 32    , 19    , false)),
            Map.entry(68, new Points(Fruit,18    , 0     , -1    , -1    , 39    , 38    , false)),
            Map.entry(69, new Points(Fruit,71.5  , 0     , -1    , -1    , 26    , 25    , false)),
            Map.entry(70, new Points(Fruit,18    , 58    , 63    , 65    , -1    , -1    , false)),
            Map.entry(71, new Points(Fruit,71.5  , 58    , 15    , 17    , -1    , -1    , false)),
            Map.entry(72, new Points(Fruit,29    , 90.5  , -1    , -1    , 59    , 58    , false)),
            Map.entry(81, new Points(Point,45    , 58    , -1    , 80    , 53    , 5     , false)),
            Map.entry(80, new Points(Point,45    , 48    , 81    , -1    , 83    , 82    , false)),
            Map.entry(82, new Points(Point,50.5  , 48    , -1    , -1    , 80    , -1    , false)),
            Map.entry(83, new Points(Point,39.5  , 48    , -1    , -1    , -1    , 80    , false))
    );

    private Points(Textures texture, double x, double y, int top, int bottom, int left, int right, boolean isEaten) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.isEaten = isEaten;
    }

    public double[] getPositionView() {
        return new double[]{
                this.x / (100 / 2.0) - 0.9,
                this.y / (100 / 2.0) - 0.9
        };
    }

    public Map<Integer, Integer> getAvailableDirections() {
        Map<Integer, Integer> dir = new HashMap<>();

        if(this.top != -1) dir.put(this.top, (int)Math.abs(this.y - PointsList.get(this.top).getY()));
        if(this.bottom != -1) dir.put(this.bottom, (int)Math.abs(this.y - PointsList.get(this.bottom).getY()));
        if(this.left != -1 && this.left != -2) dir.put(this.left, (int)Math.abs(this.x - PointsList.get(this.left).getX()));
        if(this.right != -1 && this.right != -2) dir.put(this.right, (int)Math.abs(this.x - PointsList.get(this.right).getX()));

        return dir;
    }

    public int getTargetIndex(KeyCode keyCode) {
        return switch (keyCode) {
            case UP -> this.top;
            case DOWN -> this.bottom;
            case RIGHT -> this.right;
            case LEFT -> this.left;
        };
    }
}
