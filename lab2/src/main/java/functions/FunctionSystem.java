package functions;

import static functions.stubs.logarithmic.Logarithm.log;
import static functions.stubs.trigonometric.CosStub.cos;
import static functions.stubs.trigonometric.CotStub.cot;
import static functions.stubs.trigonometric.CscStub.csc;
import static functions.stubs.trigonometric.SecStub.sec;
import static functions.stubs.trigonometric.SinStub.sin;
import static functions.stubs.trigonometric.TanStub.tan;
import static java.lang.Math.pow;

public class FunctionSystem {
    public static double calculate(double x) {
        if (x <= 0) {
            return ((((((cot(x) / cos(x)) - csc(x)) * cos(x)) * (cot(x) + (pow(cot(x) / cot(x), 2)))) * (pow(tan(x) + tan(x), 3)))
                    / (sin(x) + pow((sec(x) / sin(x)), 2))); //minor change to not get constant inf
        } else {
            return ((pow((pow(log(2, x) * log(2, x),2)) * (log(3, x) + log(10, x)), 2)) / ((log(10, x) / log(5, x)) / log(10, x)));
        }
    }
}
