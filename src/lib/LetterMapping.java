package lib;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps numbers to characters to read and write matrices.
 */
public class LetterMapping {
    /**
     * Number of mappings.
     */
    public final int modulus;
    private final HashMap<Character, Integer> reverseMap = new HashMap<>();
    private final HashMap<Integer, Character> map;

    /**
     * Create a new lib.LetterMapping.
     * 
     * @param map The desired map to follow. See {{@link #newMapping()}} for a builder implementation.
     */
    public LetterMapping(HashMap<Integer, Character> map) {
        this.map = map;
        for (Map.Entry<Integer, Character> entry : map.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        modulus = map.size();
    }

    /**
     * Reads all elements from a matrix by rows and columns and maps them into a string by the mapping.
     * 
     * @param m the matrix to read
     * @return the parsed string
     */
    public String convertToString(Matrix m) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                builder.append(map.get((int) m.get(i, j)));
            }
        }
        return builder.toString();
    }

    /**
     * Converts a string into a mapped matrix, accounting for extra fill space to return a square matrix.
     * 
     * @param s the string to map
     * @param rows the number of rows to use
     * @param fillCell the value to pad into the matrix to ensure it is square
     * @return a matrix that contains the mapped equivalent of the string
     */
    public Matrix convertToMatrix(String s, int rows, int fillCell) {
        int cols = (int) Math.ceil((double) s.length() / rows);
        double[][] data = new double[rows][cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (index < s.length()) {
                    data[i][j] = reverseMap.get(s.charAt(index));
                    index++;
                } else {
                    data[i][j] = fillCell;
                }
            }
        }
        return new Matrix(data);
    }

    /**
     * Begins building a new letter mapping.
     * 
     * @return Letter mapping builder
     */
    public static Builder newMapping() {
        return new LetterMapping.Builder();
    }

    /**
     * Letter mapping builder
     */
    public static class Builder {
        private final HashMap<Integer, Character> map = new HashMap<>();

        /**
         * Maps an integer to character for conversion.
         * 
         * @param i the integer
         * @param out the character
         * @return this builder
         */
        public Builder add(int i, char out) {
            map.put(i, out);
            return this;
        }

        /**
         * Create the letter mapping.
         * 
         * @return a {@link LetterMapping}
         */
        public LetterMapping build() {
            return new LetterMapping(map);
        }
    }
}
