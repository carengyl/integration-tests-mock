package functions.stubs.trigonometric;

import static functions.stubs.trigonometric.CosStub.cos;
import static functions.stubs.trigonometric.SinStub.sin;

public class TanStub {
    public static double tan(double x) {
        return sin(x)/cos(x);
    }
}
