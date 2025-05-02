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
        x = normalizeAngle(x);

        double sinX = this.sin(x);

        double cosValue = Math.sqrt(1 - sinX * sinX);

        if ((x > Math.PI / 2 && x < 3 * Math.PI / 2)) {
            cosValue = -cosValue;
        }

        return cosValue;
    }

    public double tan(double x) {
        return this.sin(x)/this.cos(x);
    }

    public double cot(double x) {
        return 1/tan(x);
    }

    public static double normalizeAngle(double x) {
        x = x % (2 * Math.PI);
        if (x < 0) {
            x += 2 * Math.PI;
        }
        return x;
    }

    public double csc(double x) {
        return 1/sin(x);
    }

    public double sec(double x) {
        return 1/cos(x);
    }
}
