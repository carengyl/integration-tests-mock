package functions.stubs.trigonometric;

public class Normalizer {
    public static double normalizeAngle(double x) {
        x = x % (2 * Math.PI);
        if (x < 0) {
            x += 2 * Math.PI;
        }
        return x;
    }
}
