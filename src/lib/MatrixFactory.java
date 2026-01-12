package lib;

import java.util.ArrayList;
import java.util.List;

/**
 * lib.Matrix construction factory.
 */
public class MatrixFactory {
    /**
     * Create a new lib.Matrix builder.
     *
     * @return builder
     */
    public static Builder newMatrix() {
        return new MatrixFactory.Builder();
    }

    /**
     * lib.Matrix utility builder.
     */
    public static class Builder {
        private final List<double[]> data = new ArrayList<>();
        private int cols = 0;

        /**
         * Add a row with this column data.
         *
         * @param colData the data to add
         * @return this
         */
        public Builder add(double... colData) {
            if (cols == 0) {
                cols = colData.length;
            } else if (colData.length != cols) {
                throw new IllegalArgumentException("All rows must have the same number of columns");
            }
            data.add(colData);
            return this;
        }

        /**
         * Build the matrix.
         *
         * @return the built immutable matrix instance
         */
        public Matrix build() {
            double[][] arrayData = new double[data.size()][cols];
            for (int i = 0; i < data.size(); i++) {
                arrayData[i] = data.get(i);
            }
            return new Matrix(arrayData);
        }
    }
}
