package functions.stubs.trigonometric;


import static functions.stubs.trigonometric.Normalizer.normalizeAngle;
import static functions.stubs.trigonometric.SinStub.sin;

public class CosStub {
    public static double cos(double x) {
        x = normalizeAngle(x);

        double sinX = sin(x);

        double cosValue = Math.sqrt(1 - sinX * sinX);

        if ((x > Math.PI / 2 && x < 3 * Math.PI / 2)) {
            cosValue = -cosValue;
        }

        return cosValue;
    }
}
