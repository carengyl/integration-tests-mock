package functions;

import java.util.function.Function;


public class TrigonometricFunctions {
    private final Function<Double, Double> sinImplementation;

    public TrigonometricFunctions (Function<Double, Double> sinImplementation) {
        this.sinImplementation = sinImplementation;
    }

    public double sin(double x) {
        return sinImplementation.apply(x);
    }

    public double cos(double x) {
        return sin(x + Math.PI);
    }

    public double tan(double x) {
        return sin(x)/cos(x);
    }

    public double cot(double x) {
        return 1/tan(x);
    }

    public double csc(double x) {
        return 1/sin(x);
    }

    public double sec(double x) {
        return 1/cos(x);
    }
}
