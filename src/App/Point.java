package App;

import DataSources.KeyCode;
import DataSources.Sound;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import DataSources.Textures;

import static DataSources.KeyCode.*;
import static DataSources.Sound.*;
import static DataSources.Textures.Fruit;
import static DataSources.Textures.Dot;

@Getter
public class Point {
    private final Textures texture;

    private final Sound sound;

    private final double x, y;

    private final int top, left, bottom, right;

    private final boolean defaultView;

    private final int HEIGHT = 100, WIDTH = 100;

    @Setter
    private boolean isEaten;

    @Getter @Setter
    private static int noOfViewedPoints;

    public static Map<Integer, Point> PointsList = Map.<Integer, Point>ofEntries(
        Map.entry(1 , new Point(Dot  , 45.00, 38.75, -1, -1, 50, 2 , true )),
        Map.entry(2 , new Point(Dot  , 61.25, 38.75, 3 , 67, 1 , -1, false)),
        Map.entry(3 , new Point(Dot  , 61.25, 48.00, 4 , 2 , -1, 17, false)),
        Map.entry(4 , new Point(Dot  , 61.25, 58.00, -1, 3 , 5 , -1, false)),
        Map.entry(5 , new Point(Dot  , 50.50, 58.00, 6 , -1, 81, 4 , false)),
        Map.entry(6 , new Point(Dot  , 50.50, 68.00, -1, 5 , -1, 7 , false)),
        Map.entry(7 , new Point(Dot  , 61.25, 68.00, 8 , -1, 6 , -1, false)),
        Map.entry(8 , new Point(Dot  , 61.25, 77.50, -1, 7 , 9 , 16, false)),
        Map.entry(9 , new Point(Dot  , 50.50, 77.50, 10, -1, 57, 8 , false)),
        Map.entry(10, new Point(Dot  , 50.50, 90.50, -1, 9 , -1, 11, false)),
        Map.entry(11, new Point(Dot  , 71.50, 90.50, -1, 16, 10, 12, false)),
        Map.entry(12, new Point(Dot  , 90.00, 90.50, -1, 13, 11, -1, false)),
        Map.entry(13, new Point(Dot  , 90.00, 77.50, 12, 14, 16, -1, false)),
        Map.entry(14, new Point(Dot  , 90.00, 68.00, 13, -1, 15, -1, false)),
        Map.entry(15, new Point(Dot  , 71.50, 68.00, 16, 71, -1, 14, false)),
        Map.entry(16, new Point(Dot  , 71.50, 77.50, 11, 15, 8 , 13, false)),
        Map.entry(17, new Point(Dot  , 71.50, 48.00, 71, 19, 3 , 18, false)),
        Map.entry(18, new Point(Dot  , 90.00, 48.00, -1, -1, 17, 66, false)),
        Map.entry(19, new Point(Dot  , 71.50, 29.00, 17, 30, 67, 20, false)),
        Map.entry(20, new Point(Dot  , 90.00, 29.00, -1, 21, 19, -1, false)),
        Map.entry(21, new Point(Dot  , 90.00, 19.00, 20, -1, 22, -1, false)),
        Map.entry(22, new Point(Dot  , 82.00, 19.00, -1, 23, -1, 21, false)),
        Map.entry(23, new Point(Dot  , 82.00, 10.00, 22, -1, 31, 24, false)),
        Map.entry(24, new Point(Dot  , 90.00, 10.00, -1, 25, 23, -1, false)),
        Map.entry(25, new Point(Dot  , 90.00, 00.00, 24, -1, 69, -1, false)),
        Map.entry(26, new Point(Dot  , 50.50, 00.00, 27, -1, 38, 69, false)),
        Map.entry(27, new Point(Dot  , 50.50, 10.00, -1, 26, -1, 28, false)),
        Map.entry(28, new Point(Dot  , 61.25, 10.00, 29, -1, 27, -1, false)),
        Map.entry(29, new Point(Dot  , 61.25, 19.00, -1, 28, 33, 30, false)),
        Map.entry(30, new Point(Dot  , 71.50, 19.00, 19, 31, 29, -1, false)),
        Map.entry(31, new Point(Dot  , 71.50, 10.00, 30, -1, -1, 23, false)),
        Map.entry(32, new Point(Dot  , 50.50, 29.00, -1, 33, -1, 67, false)),
        Map.entry(33, new Point(Dot  , 50.50, 19.00, 32, -1, 34, 29, false)),
        Map.entry(34, new Point(Dot  , 39.50, 19.00, 49, -1, 35, 33, false)),
        Map.entry(35, new Point(Dot  , 29.00, 19.00, -1, 36, 46, 34, false)),
        Map.entry(36, new Point(Dot  , 29.00, 10.00, 35, -1, -1, 37, false)),
        Map.entry(37, new Point(Dot  , 39.50, 10.00, -1, 38, 36, -1, false)),
        Map.entry(38, new Point(Dot  , 39.50, 00.00, 37, -1, 68, 26, false)),
        Map.entry(39, new Point(Dot  , 00.00, 00.00, 40, -1, -1, 68, false)),
        Map.entry(40, new Point(Dot  , 00.00, 10.00, -1, 39, -1, 41, false)),
        Map.entry(41, new Point(Dot  , 08.00, 10.00, 42, -1, 40, 47, false)),
        Map.entry(42, new Point(Dot  , 08.00, 19.00, -1, 41, 43, -1, false)),
        Map.entry(43, new Point(Dot  , 00.00, 19.00, 44, -1, -1, 42, false)),
        Map.entry(44, new Point(Dot  , 00.00, 29.00, -1, 43, -1, 45, false)),
        Map.entry(45, new Point(Dot  , 18.00, 29.00, 65, 46, 44, 48, false)),
        Map.entry(46, new Point(Dot  , 18.00, 19.00, 45, 47, -1, 35, false)),
        Map.entry(47, new Point(Dot  , 18.00, 10.00, 46, -1, 41, -1, false)),
        Map.entry(48, new Point(Dot  , 29.00, 29.00, 50, -1, 45, 49, false)),
        Map.entry(49, new Point(Dot  , 39.50, 29.00, -1, 34, 48, -1, false)),
        Map.entry(50, new Point(Dot  , 29.00, 38.75, 51, 48, -1, 1 , false)),
        Map.entry(51, new Point(Dot  , 29.00, 48.00, 52, 50, 65, -1, false)),
        Map.entry(52, new Point(Dot  , 29.00, 58.00, -1, 51, -1, 53, false)),
        Map.entry(53, new Point(Dot  , 39.50, 58.00, 54, -1, 52, 81, false)),
        Map.entry(54, new Point(Dot  , 39.50, 68.00, -1, 53, 55, -1, false)),
        Map.entry(55, new Point(Dot  , 29.00, 68.00, 56, -1, -1, 54, false)),
        Map.entry(56, new Point(Dot  , 29.00, 77.50, -1, 55, 64, 57, false)),
        Map.entry(57, new Point(Dot  , 39.50, 77.50, 58, -1, 56, 9 , false)),
        Map.entry(58, new Point(Dot  , 39.50, 90.50, -1, 57, 72, -1, false)),
        Map.entry(59, new Point(Dot  , 18.00, 90.50, -1, 64, 60, 72, false)),
        Map.entry(60, new Point(Dot  , 00.00, 90.50, -1, 61, -1, 59, false)),
        Map.entry(61, new Point(Dot  , 00.00, 77.50, 60, 62, -1, 64, false)),
        Map.entry(62, new Point(Dot  , 00.00, 68.00, 61, -1, -1, 63, false)),
        Map.entry(63, new Point(Dot  , 18.00, 68.00, 64, 70, 62, -1, false)),
        Map.entry(64, new Point(Dot  , 18.00, 77.50, 59, 63, 61, 56, false)),
        Map.entry(65, new Point(Dot  , 18.00, 48.00, 70, 45, 66, 51, false)),
        Map.entry(66, new Point(Dot  , 00.00, 48.00, -1, -1, 18, 65, false)),
        Map.entry(67, new Point(Dot  , 61.25, 29.00, 2 , -1, 32, 19, false)),
        Map.entry(68, new Point(Fruit, 18.00, 00.00, -1, -1, 39, 38, false)),
        Map.entry(69, new Point(Fruit, 71.50, 00.00, -1, -1, 26, 25, false)),
        Map.entry(70, new Point(Fruit, 18.00, 58.00, 63, 65, -1, -1, false)),
        Map.entry(71, new Point(Fruit, 71.50, 58.00, 15, 17, -1, -1, false)),
        Map.entry(72, new Point(Fruit, 29.00, 90.50, -1, -1, 59, 58, false)),
        Map.entry(81, new Point(Dot  , 45.00, 58.00, -1, 80, 53, 5 , true )),
        Map.entry(80, new Point(Dot  , 45.00, 48.00, 81, -1, 83, 82, true )),
        Map.entry(82, new Point(Dot  , 50.50, 48.00, -1, -1, 80, -1, true )),
        Map.entry(83, new Point(Dot  , 39.50, 48.00, -1, -1, -1, 80, true ))
    );

    public static void resetNoOfViewedPoints() {
        Point.noOfViewedPoints = (int) PointsList.values().stream().filter(p -> !p.defaultView).count();
    }

    private Point(Textures texture, double x, double y, int top, int bottom, int left, int right, boolean defaultView) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.defaultView = defaultView;
        this.isEaten = defaultView;

        this.sound = switch (texture) {
            case Fruit -> FruitEaten;
            case Dot -> PointEaten;
            default -> null;
        };
    }

    public double getXView() {
        return this.x / (WIDTH / 2.0) - 0.9;
    }
    public double getYView() {
        return this.y / (HEIGHT / 2.0) - 0.9;
    }

    public int getTargetIndex(KeyCode keyCode) {
        return switch (keyCode) {
            case UP -> this.top;
            case DOWN -> this.bottom;
            case RIGHT -> this.right;
            case LEFT -> this.left;
        };
    }

    public KeyCode getTargetKeyCode(int index) {
        if (index == top) return UP;
        if (index == bottom) return DOWN;
        if (index == right) return RIGHT;
        if (index == left) return LEFT;
        return null;
    }
}
