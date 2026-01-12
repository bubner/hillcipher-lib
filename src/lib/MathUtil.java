package lib;

/**
 * Math utilities.
 */
public class MathUtil {
    /**
     * Extended Euclidean Algorithm
     * <p>
     * <code>gcd(a, b) = ax + by</code>
     *
     * @param a first coefficient
     * @param b second coefficient
     * @return array of <code>[gcd(a,b), x, y]</code> such that ax + by = gcd(a, b)
     */
    public static int[] extendedEuclideanAlgorithm(double a, double b) {
        if (a % 1 != 0)
            throw new IllegalArgumentException("a cannot be a fractional value");
        if (b % 1 != 0)
            throw new IllegalArgumentException("b cannot be a fractional value");
        int argA = (int) a;
        int argB = (int) b;
        if (argB == 0) {
            // Base case, gcd(a,b) = ax + by = a*1 + b*0 = a
            return new int[]{argA, 1, 0};
        }
        // Recursively find the GCD by the modulo property
        int[] ret = extendedEuclideanAlgorithm(argB, posMod(argA, argB));
        // Calculate the new x and y used to calculate the GCD
        int tmp = ret[1] - ret[2] * (argA / argB);
        ret[1] = ret[2];
        ret[2] = tmp;
        return ret;
    }

    /**
     * Get the modulus value in the positive domain.
     * <p>
     * Also see: {@link Math#floorMod(int, int)}.
     *
     * @param x   the number
     * @param mod modulus value
     * @return x>0 modulus
     */
    public static double posMod(double x, int mod) {
        return (x % mod + mod) % mod;
    }
}
