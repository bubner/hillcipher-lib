package lib;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Immutable matrix.
 */
public class Matrix {
    private final double[][] data;

    public final int rows;
    public final int cols;

    /**
     * Create a new matrix.
     *
     * @param data the data of the matrix
     */
    public Matrix(double[][] data) {
        this.data = data;
        this.rows = data.length;
        this.cols = data[0].length;
    }

    /**
     * Get the value at a specific row and column.
     *
     * @param row the row
     * @param col the column
     * @return the value at the specified row and column
     */
    public double get(int row, int col) {
        return data[row][col];
    }

    /**
     * Map a function over the matrix.
     *
     * @param f the function to map
     * @return mapped matrix
     */
    public Matrix map(Function<Double, Double> f) {
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Apply each cell to the function
                result[i][j] = f.apply(data[i][j]);
            }
        }
        return new Matrix(result);
    }

    /**
     * Add two matrices.
     *
     * @param other the other matrix
     * @return the sum of the two matrices
     */
    public Matrix plus(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions");
        }

        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // For every cell in our matrix, add the corresponding cell in the other matrix
                result[i][j] = this.get(i, j) + other.get(i, j);
            }
        }

        return new Matrix(result);
    }

    /**
     * Subtract two matrices.
     *
     * @param other the other matrix
     * @return the difference of the two matrices
     */
    public Matrix minus(Matrix other) {
        // Subtracting a matrix is the same as adding the negative of the matrix
        return this.plus(other.map(x -> -x));
    }

    /**
     * Multiply two matrices.
     *
     * @param other the other matrix
     * @return the product of the two matrices
     */
    public Matrix times(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second matrix");
        }

        // New matrix has dimensions m1.r, m2.c
        double[][] result = new double[this.rows][other.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                // Summation
                double sum = 0;
                for (int k = 0; k < this.cols; k++) {
                    // Dot product of across row on m1, down row on m2
                    sum += this.get(i, k) * other.get(k, j);
                }
                result[i][j] = sum;
            }
        }

        return new Matrix(result);
    }

    /**
     * Calculate the determinant of the matrix.
     *
     * @return the determinant of the matrix
     */
    public double determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("lib.Matrix must be square");
        }
        if (rows == 1) {
            // det 1x1 = a
            return data[0][0];
        }
        if (rows == 2) {
            // det 2x2 = ad-bc
            return data[0][0] * data[1][1] - data[0][1] * data[1][0];
        }
        // Summation
        double det = 0;
        for (int i = 0; i < cols; i++) {
            // Laplace expansion where the first row is used, any row can be used as it is a constant factor
            // Recursive call to the minor determinant which will eventually reach the 2x2 ad-bc case
            det += Math.pow(-1, i) * data[0][i] * this.minor(0, i).determinant();
        }
        return det;
    }

    /**
     * Invert this matrix.
     *
     * @return the inverted matrix
     */
    public Matrix invert() {
        double det = this.determinant();
        if (det == 0) {
            // Division by zero
            throw new IllegalArgumentException("lib.Matrix must be invertible");
        }
        // Adjugate matrix with values divided by the determinant is the inverse
        return this.adjugate().map(v -> v / det);
    }

    /**
     * Get the adjugate matrix from this matrix.
     *
     * @return the adjugate matrix
     */
    public Matrix adjugate() {
        if (rows != cols) {
            throw new IllegalArgumentException("lib.Matrix must be square");
        }
        double[][] cofactorMatrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // The cofactor matrix is the matrix of minors with alternating signs
                cofactorMatrix[i][j] = Math.pow(-1, i + j) * this.minor(i, j).determinant();
            }
        }
        // Transposing the cofactor matrix gives the adjugate matrix
        return new Matrix(cofactorMatrix).transpose();
    }

    /**
     * Transpose the matrix.
     *
     * @return the transposed matrix
     */
    public Matrix transpose() {
        double[][] result = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Swap the row and column
                result[j][i] = data[i][j];
            }
        }
        return new Matrix(result);
    }

    /**
     * Get the minor of the matrix.
     *
     * @param row the row
     * @param col the column
     * @return the minor of the matrix
     */
    public Matrix minor(int row, int col) {
        // The new matrix will be of 1 row and column removed from what it was
        double[][] result = new double[rows - 1][cols - 1];
        // i, i2 are the row indices, j, j2 are the column indices
        for (int i = 0, i2 = 0; i < rows; i++) {
            // Skip the row we're removing
            if (i == row) {
                continue;
            }
            for (int j = 0, j2 = 0; j < cols; j++) {
                // Skip the column we're removing
                if (j == col) {
                    continue;
                }
                // Copy the unaffected cell
                result[i2][j2] = data[i][j];
                j2++;
            }
            i2++;
        }
        // result array is now the minor
        return new Matrix(result);
    }

    /**
     * Print the matrix.
     */
    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                toPrint.append(data[i][j]).append(" ");
            }
            toPrint.append("\n");
        }
        return toPrint.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Matrix) {
            return Arrays.deepEquals(data, (((Matrix) other).data));
        }
        return false;
    }
}
