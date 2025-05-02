package functions.stubs.logarithmic;

import static functions.stubs.logarithmic.LnStub.ln;

public class Logarithm {
    public static double log(double a, double b) {
        return ln(a)/ln(b);
    }
}
