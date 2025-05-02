package functions.stubs;

import java.util.HashMap;
import java.util.Map;

public class LnStub {
    private static final Map<Double, Double> LN_TABLE = createLnTable();

    private static Map<Double, Double> createLnTable() {
        Map<Double, Double> table = new HashMap<>();
        table.put(0.1, -2.3026);
        table.put(0.2, -1.6094);
        table.put(0.5, -0.6931);
        table.put(1.0, 0.0);
        table.put(2.0, 0.6931);
        table.put(2.71828, 1.0); // e
        table.put(3.0, 1.0986);
        table.put(5.0, 1.6094);
        table.put(10.0, 2.3026);
        return table;
    }

    public double ln(double x) {
        // Logarithm only defined for positive numbers
        if (x <= 0) {
            return Double.NaN;
        }

        for (Map.Entry<Double, Double> entry : LN_TABLE.entrySet()) {
            if (Math.abs(x - entry.getKey()) < 0.0001) {
                return entry.getValue();
            }
        }

        // Fall back to built-in function
        return Math.log(x);
    }
}