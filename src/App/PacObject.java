package App;

public class PacObject {
    double x, y;
    int texture;
    int index, random;

    PacObject(int texture,int index) {
        this.texture = texture;
        this.index = index;
    }
    PacObject(int texture, int index, int random) {
        this.texture = texture;
        this.index = index;
        this.random = random;
    }
}
