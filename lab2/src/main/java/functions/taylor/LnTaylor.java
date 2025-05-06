package functions.taylor;

public class LnTaylor {
    private double epsilon;

    public LnTaylor(double epsilon) {
        this.epsilon = epsilon;
    }

    public LnTaylor() {
        this(0.0001);
    }

    public double ln(double x) {
        if (x <= 0) {
            return Double.NaN;
        }

        double result = 0;
        double term = (x - 1) / (x + 1);
        double temp = term;
        int n = 1;
        while (Math.abs(temp) >= epsilon) {
            result += temp;
            term *= ((x - 1) * (x - 1)) / ((x + 1) * (x + 1));
            temp = term / (2 * n + 1);
            n++;
        }
        return 2 * result;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
}
