package App;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Points {
    @Getter
    private final int index;
    @Getter
    private final double x, y;
    @Getter
    private final int top, left, bottom, right;
    @Setter @Getter
    private boolean isEaten;

    public static List<Points> PointsList = List.of(
            new Points(0     , 0     , 0     , 0     , 0     , 0     , 0     , false),
            new Points(1     , 45    , 38.75 , -1    , -1    , 50    , 2     , false),
            new Points(2     , 61.25 , 38.75 , 3     , 67    , 1     , -1    , false),
            new Points(3     , 61.25 , 48    , 4     , 2     , -1    , 17    , false),
            new Points(4     , 61.25 , 58    , -1    , 3     , 5     , -1    , false),
            new Points(5     , 50.75 , 58    , 6     , -1    , 53    , 4     , false),
            new Points(6     , 50.75 , 68    , -1    , 5     , -1    , 7     , false),
            new Points(7     , 61.75 , 68    , 8     , -1    , 6     , -1    , false),
            new Points(8     , 61.75 , 77.5  , -1    , 7     , 9     , 16    , false),
            new Points(9     , 51    , 77.5  , 10    , -1    , 57    , 8     , false),
            new Points(10    , 51    , 90.5  , -1    , 9     , -1    , 11    , false),
            new Points(11    , 71.5  , 90.5  , -1    , 16    , 10    , 12    , false),
            new Points(12    , 89.25 , 90.5  , -1    , 13    , 11    , -1    , false),
            new Points(13    , 89.25 , 77.5  , 12    , 14    , 16    , -1    , false),
            new Points(14    , 89.25 , 67.75 , 13    , -1    , 15    , -1    , false),
            new Points(15    , 71.5  , 67.75 , 16    , 17    , -1    , 14    , false),
            new Points(16    , 71.5  , 77.5  , 11    , 15    , 8     , 13    , false),
            new Points(17    , 71.5  , 48    , 15    , 19    , 3     , 18    , false),
            new Points(18    , 90    , 48    , -1    , -1    , 17    , -2    , false),
            new Points(19    , 71.5  , 29    , 17    , 30    , 67    , 20    , false),
            new Points(20    , 90    , 29    , -1    , 21    , 19    , -1    , false),
            new Points(21    , 90    , 19    , 20    , -1    , 22    , -1    , false),
            new Points(22    , 82    , 19    , -1    , 23    , -1    , 21    , false),
            new Points(23    , 82    , 10    , 22    , -1    , 31    , 24    , false),
            new Points(24    , 90    , 10    , -1    , 25    , 23    , -1    , false),
            new Points(25    , 90    , 0     , 24    , -1    , 26    , -1    , false),
            new Points(26    , 50.5  , 0     , 27    , -1    , 38    , 25    , false),
            new Points(27    , 50.5  , 10    , -1    , 26    , -1    , 28    , false),
            new Points(28    , 61.5  , 10    , 29    , -1    , 27    , -1    , false),
            new Points(29    , 61.5  , 19    , -1    , 28    , 33    , 30    , false),
            new Points(30    , 71.5  , 19    , 19    , 31    , 29    , -1    , false),
            new Points(31    , 71.5  , 10    , 30    , -1    , -1    , 23    , false),
            new Points(32    , 51    , 29    , -1    , 33    , -1    , 67    , false),
            new Points(33    , 51    , 19    , 32    , -1    , 34    , 29    , false),
            new Points(34    , 40    , 19    , 49    , -1    , 35    , 33    , false),
            new Points(35    , 29    , 19    , -1    , 36    , 46    , 34    , false),
            new Points(36    , 29    , 10    , 35    , -1    , -1    , 37    , false),
            new Points(37    , 40    , 10    , -1    , 38    , 36    , -1    , false),
            new Points(38    , 40    , 0     , 37    , -1    , 39    , 26    , false),
            new Points(39    , 0     , 0     , 40    , -1    , -1    , 38    , false),
            new Points(40    , 0     , 10    , -1    , 39    , -1    , 41    , false),
            new Points(41    , 8     , 10    , 42    , -1    , 40    , 47    , false),
            new Points(42    , 8     , 19    , -1    , 41    , 43    , -1    , false),
            new Points(43    , 0     , 19    , 44    , -1    , -1    , 42    , false),
            new Points(44    , 0     , 29    , -1    , 43    , -1    , 45    , false),
            new Points(45    , 18    , 29    , 65    , 46    , 44    , 48    , false),
            new Points(46    , 18    , 19    , 45    , 47    , -1    , 35    , false),
            new Points(47    , 18    , 10    , 46    , -1    , 41    , -1    , false),
            new Points(48    , 29    , 29    , 50    , -1    , 45    , 49    , false),
            new Points(49    , 40    , 29    , -1    , 34    , 48    , -1    , false),
            new Points(50    , 29    , 38.75 , 51    , 48    , -1    , 1     , false),
            new Points(51    , 29    , 48    , 52    , 50    , 65    , -1    , false),
            new Points(52    , 29    , 58    , -1    , 51    , -1    , 53    , false),
            new Points(53    , 40    , 58    , 54    , -1    , 52    , 5     , false),
            new Points(54    , 40    , 68    , -1    , 53    , 55    , -1    , false),
            new Points(55    , 29    , 68    , 56    , -1    , -1    , 54    , false),
            new Points(56    , 29    , 77.5  , -1    , 55    , 64    , 57    , false),
            new Points(57    , 40    , 77.5  , 58    , -1    , 56    , 9     , false),
            new Points(58    , 40    , 90.5  , -1    , 57    , 59    , -1    , false),
            new Points(59    , 18    , 90.5  , -1    , 64    , 60    , 58    , false),
            new Points(60    , 0     , 90.5  , -1    , 61    , -1    , 59    , false),
            new Points(61    , 0     , 77.5  , 60    , 62    , -1    , 64    , false),
            new Points(62    , 0     , 68    , 61    , -1    , -1    , 63    , false),
            new Points(63    , 18    , 68    , 64    , 65    , 62    , -1    , false),
            new Points(64    , 18    , 77.5  , 59    , 63    , 61    , 56    , false),
            new Points(65    , 18    , 48    , 63    , 45    , 66    , 51    , false),
            new Points(66    , 0     , 48    , -1    , -1    , -2    , 65    , false),
            new Points(67    , 61.25 , 29    , 2     , -1    , 32    , 19    , false)
    );


    public static List<Points> FruitsList = List.of(
            new Points(78    , 20    , 0     , -1, -1, -1, -1, false),
            new Points(79    , 69    , 0     , -1, -1, -1, -1, false),
            new Points(80    , 18    , 59    , -1, -1, -1, -1, false),
            new Points(81    , 71.5  , 59    , -1, -1, -1, -1, false),
            new Points(82    , 29    , 90.5  , -1, -1, -1, -1, false)
    );

    private Points(int index, double x, double y, int top, int bottom, int left, int right, boolean isEaten) {
        this.index = index;
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
}
