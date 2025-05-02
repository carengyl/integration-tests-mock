package util;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

public class CsvWriter {
    private final String delimiter;

    public CsvWriter() {
        this(",");
    }

    public CsvWriter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void writeToCsv(String filePath, Function<Double, Double> function,
                           double start, double end, double step) throws IOException {
        if (step <= 0) {
            throw new IllegalArgumentException("Step must be positive.");
        }

        try (Writer writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write("x" + delimiter + "result");
            writer.write(System.lineSeparator());

            for (double x = start; x <= end; x += step) {
                double result = function.apply(x);
                writer.write(x + delimiter + result);
                writer.write(System.lineSeparator());
            }
        }
    }
}
