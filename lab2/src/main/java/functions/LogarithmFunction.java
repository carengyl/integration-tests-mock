package functions;

import java.util.function.Function;

public class LogarithmFunction {
    private final Function<Double, Double> lnImplementation;

    public LogarithmFunction(Function<Double, Double> lnImplementation) {
        this.lnImplementation = lnImplementation;
    }

    public double log(double a, double b) {
        return lnImplementation.apply(a)/ lnImplementation.apply(b);
    }
}
