package lib;

/**
 * Hill cipher implementation.
 */
public class HillCipher {
    /**
     * Encrypt a matrix.
     *
     * @param message the message
     * @param key the key to use
     * @param modulo the modulus to use
     * @return encrypted matrix
     */
    public static Matrix encrypt(Matrix message, Matrix key, int modulo) {
        if (Math.abs(MathUtil.extendedEuclideanAlgorithm(key.determinant(), modulo)[0]) != 1) {
            throw new IllegalArgumentException("Determinant " + key.determinant() + " is a factor of modulo " + modulo + ".");
        }
        return key.times(message).map(v -> MathUtil.posMod(v, modulo));
    }

    /**
     * Decrypt a matrix.
     *
     * @param encrypted the encrypted matrix
     * @param encryptionKey the encryption (not decryption) key
     * @param modulo the modulus to use
     * @return decrypted matrix
     */
    public static Matrix decrypt(Matrix encrypted, Matrix encryptionKey, int modulo) {
        double x = MathUtil.posMod(MathUtil.extendedEuclideanAlgorithm(encryptionKey.determinant(), modulo)[1], modulo);
        Matrix kInverse = encryptionKey.adjugate()
                .map(v -> v * x)
                .map(v -> MathUtil.posMod(v, modulo));
        return kInverse.times(encrypted).map(v -> MathUtil.posMod(v, modulo));
    }
}
