package App;

public class Points {
    private int index;
    private double x, y;
    private int top, left, bottom, right;
    private boolean isChecked;

    private final double[][] initPoints = {
        { 0     , 0     , 0     , 0     , 0     , 0     ,  0    },
        { 1     , 45    , 38.75 , -1    , -1    , 50    ,  2    },
        { 2     , 61.25 , 38.75 , 3     , 67    , 1     , -1    },
        { 3     , 61.25 , 48    , 4     , 2     , -1    , 17    },
        { 4     , 61.25 , 58    , -1    , 3     , 5     , -1    },
        { 5     , 50.75 , 58    , 6     , -1    , 53    , 4     },
        { 6     , 50.75 , 68    , -1    , 5     , -1    , 7     },
        { 7     , 61.75 , 68    , 8     , -1    , 6     , -1    },
        { 8     , 61.75 , 77.5  , -1    , 7     , 9     , 16    },
        { 9     , 51    , 77.5  , 10    , -1    , 57    , 8     },
        { 10    , 51    , 90.5  , -1    , 9     , -1    , 11    },
        { 11    , 71.5  , 90.5  , -1    , 16    , 10    , 12    },
        { 12    , 89.25 , 90.5  , -1    , 13    , 11    , -1    },
        { 13    , 89.25 , 77.5  , 12    , 14    , 16    , -1    },
        { 14    , 89.25 , 67.75 , 13    , -1    , 15    , -1    },
        { 15    , 71.5  , 67.75 , 16    , 17    , -1    , 14    },
        { 16    , 71.5  , 77.5  , 11    , 15    , 8     , 13    },
        { 17    , 71.5  , 48    , 15    , 19    , 3     , 18    },
        { 18    , 90    , 48    , -1    , -1    , 17    , -2    },
        { 19    , 71.5  , 29    , 17    , 30    , 67    , 20    },
        { 20    , 90    , 29    , -1    , 21    , 19    , -1    },
        { 21    , 90    , 19    , 20    , -1    , 22    , -1    },
        { 22    , 82    , 19    , -1    , 23    , -1    , 21    },
        { 23    , 82    , 10    , 22    , -1    , 31    , 24    },
        { 24    , 90    , 10    , -1    , 25    , 23    , -1    },
        { 25    , 90    , 0     , 24    , -1    , 26    , -1    },
        { 26    , 50.5  , 0     , 27    , -1    , 38    , 25    },
        { 27    , 50.5  , 10    , -1    , 26    , -1    , 28    },
        { 28    , 61.5  , 10    , 29    , -1    , 27    , -1    },
        { 29    , 61.5  , 19    , -1    , 28    , 33    , 30    },
        { 30    , 71.5  , 19    , 19    , 31    , 29    , -1    },
        { 31    , 71.5  , 10    , 30    , -1    , -1    , 23    },
        { 32    , 51    , 29    , -1    , 33    , -1    , 67    },
        { 33    , 51    , 19    , 32    , -1    , 34    , 29    },
        { 34    , 40    , 19    , 49    , -1    , 35    , 33    },
        { 35    , 29    , 19    , -1    , 36    , 46    , 34    },
        { 36    , 29    , 10    , 35    , -1    , -1    , 37    },
        { 37    , 40    , 10    , -1    , 38    , 36    , -1    },
        { 38    , 40    , 0     , 37    , -1    , 39    , 26    },
        { 39    , 0     , 0     , 40    , -1    , -1    , 38    },
        { 40    , 0     , 10    , -1    , 39    , -1    , 41    },
        { 41    , 8     , 10    , 42    , -1    , 40    , 47    },
        { 42    , 8     , 19    , -1    , 41    , 43    , -1    },
        { 43    , 0     , 19    , 44    , -1    , -1    , 42    },
        { 44    , 0     , 29    , -1    , 43    , -1    , 45    },
        { 45    , 18    , 29    , 65    , 46    , 44    , 48    },
        { 46    , 18    , 19    , 45    , 47    , -1    , 35    },
        { 47    , 18    , 10    , 46    , -1    , 41    , -1    },
        { 48    , 29    , 29    , 50    , -1    , 45    , 49    },
        { 49    , 40    , 29    , -1    , 34    , 48    , -1    },
        { 50    , 29    , 38.75 , 51    , 48    , -1    , 1     },
        { 51    , 29    , 48    , 52    , 50    , 65    , -1    },
        { 52    , 29    , 58    , -1    , 51    , -1    , 53    },
        { 53    , 40    , 58    , 54    , -1    , 52    , 5     },
        { 54    , 40    , 68    , -1    , 53    , 55    , -1    },
        { 55    , 29    , 68    , 56    , -1    , -1    , 54    },
        { 56    , 29    , 77.5  , -1    , 55    , 64    , 57    },
        { 57    , 40    , 77.5  , 58    , -1    , 56    , 9     },
        { 58    , 40    , 90.5  , -1    , 57    , 59    , -1    },
        { 59    , 18    , 90.5  , -1    , 64    , 60    , 58    },
        { 60    , 0     , 90.5  , -1    , 61    , -1    , 59    },
        { 61    , 0     , 77.5  , 60    , 62    , -1    , 64    },
        { 62    , 0     , 68    , 61    , -1    , -1    , 63    },
        { 63    , 18    , 68    , 64    , 65    , 62    , -1    },
        { 64    , 18    , 77.5  , 59    , 63    , 61    , 56    },
        { 65    , 18    , 48    , 63    , 45    , 66    , 51    },
        { 66    , 0     , 48    , -1    , -1    , -2    , 65    },
        { 67    , 61.25 , 29    , 2     , -1    , 32    , 19    },
    };
    private final double[][] Fruits = {
        { 78    , 20    , 0     },
        { 79    , 69    , 0     },
        { 80    , 18    , 59    },
        { 81    , 71.5  , 59    },
        { 82    , 29    , 90.5  },
    };
    public Points() {}
    public Points(int index, double x, double y, int top, int bottom, int left, int right, boolean isChecked) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.isChecked = isChecked;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getTop() {
        return top;
    }
    public int getLeft() {
        return left;
    }
    public int getBottom() {
        return bottom;
    }
    public int getRight() {
        return right;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public int getIndex() {
        return index;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public double[][] getInitPoints(){ return initPoints;}
    public double[][] getFruits(){ return Fruits;}
}