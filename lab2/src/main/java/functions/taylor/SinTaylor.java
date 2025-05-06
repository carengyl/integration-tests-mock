package functions.taylor;

public class SinTaylor {
    private double epsilon;

    public SinTaylor(double epsilon) {
        this.epsilon = epsilon;
    }

    public SinTaylor() {
        this(0.000001);
    }

    public double sin(double x) {
        double result = 0;
        double term = x;
        int n = 1;
        while (Math.abs(term) >= epsilon) {
            result += term;
            term *= -x * x / ((2 * n) * (2 * n + 1));
            n++;
        }
        return result;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
}
