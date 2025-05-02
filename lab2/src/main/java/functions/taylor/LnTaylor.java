package functions.taylor;

public class LnTaylor {
    private double epsilon;

    public LnTaylor(double epsilon) {
        this.epsilon = epsilon;
    }

    public double ln(double x) {
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
