package functions.stubs;

import java.util.HashMap;
import java.util.Map;

public class SinStub {
    private static final Map<Double, Double> SIN_TABLE = createSinTable();

    private static Map<Double, Double> createSinTable() {
        Map<Double, Double> table = new HashMap<>();
        table.put(0.0, 0.0);
        table.put(Math.PI / 6, 0.5);
        table.put(Math.PI / 4, 0.7071);
        table.put(Math.PI / 3, 0.866);
        table.put(Math.PI / 2, 1.0);
        table.put((2 * Math.PI) / 3, 0.866);
        table.put((3 * Math.PI) / 4, 0.7071);
        table.put((5 * Math.PI) / 6, 0.5);
        table.put(Math.PI, 0.0);
        table.put((7 * Math.PI) / 6, -0.5);
        table.put((5 * Math.PI) / 4, -0.7071);
        table.put((4 * Math.PI) / 3, -0.866);
        table.put((3 * Math.PI) / 2, -1.0);
        table.put((5 * Math.PI) / 3, -0.866);
        table.put((7 * Math.PI) / 4, -0.7071);
        table.put((11 * Math.PI) / 6, -0.5);
        table.put(2 * Math.PI, 0.0);
        return table;
    }

    public double sin(double x) {
        double normalized = ((x % (2 * Math.PI)) + (2 * Math.PI)) % (2 * Math.PI);

        for (Map.Entry<Double, Double> entry : SIN_TABLE.entrySet()) {
            if (Math.abs(normalized - entry.getKey()) < 0.0001) {
                return entry.getValue();
            }
        }

        // Fall back to built-in function
        return Math.sin(x);
    }
}