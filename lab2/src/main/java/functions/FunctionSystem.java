package functions;

import static java.lang.Math.pow;

public class FunctionSystem {
    private final TrigonometricFunctions trigs;
    private final LogarithmFunction logs;

    public FunctionSystem(TrigonometricFunctions trigonometricFunctions, LogarithmFunction logarithmFunction) {
        this.trigs = trigonometricFunctions;
        this.logs = logarithmFunction;
    }

    public double calculate(double x) {
        if (x <= 0) {
            return ((((((trigs.cot(x) / trigs.cos(x)) - trigs.csc(x)) *
                        trigs.cos(x)) * (trigs.cot(x) + (pow(trigs.cot(x) / trigs.cot(x), 2)))) *
                        (pow(trigs.tan(x) + trigs.tan(x), 3)))
                    / (trigs.sin(x) + pow((trigs.sec(x) / trigs.sin(x)), 2))); //minor change to not get constant inf
        } else {
            return ((pow((pow(logs.log(2, x) * logs.log(2, x),2)) * (logs.log(3, x) + logs.log(10, x)), 2))
                    / ((logs.log(10, x) / logs.log(5, x)) / logs.log(10, x)));
        }
    }
}
