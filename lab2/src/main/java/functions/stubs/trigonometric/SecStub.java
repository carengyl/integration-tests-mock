package functions.stubs.trigonometric;

import static functions.stubs.trigonometric.CosStub.cos;;

public class SecStub {
    public static double sec(double x) {
        return 1/cos(x);
    }
}
