package functions.stubs.trigonometric;

import static functions.stubs.trigonometric.SinStub.sin;

public class CscStub {
    public static double csc(double x) {
        return 1/sin(x);
    }
}
