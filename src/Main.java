import lib.HillCipher;
import lib.LetterMapping;
import lib.Matrix;
import lib.MatrixFactory;

public class Main {
    public static void main(String[] args) {
        LetterMapping map = LetterMapping.newMapping()
                .add(1, 'M')
                .add(2, 'A')
                .add(3, 'Q')
                .add(4, '.')
                .add(5, 'K')
                .add(6, 'L')
                .add(7, 'X')
                .add(8, 'C')
                .add(9, 'V')
                .add(10, ' ')
                .add(11, 'N')
                .add(12, 'S')
                .add(13, 'D')
                .add(14, 'F')
                .add(15, 'G')
                .add(16, 'H')
                .add(17, 'U')
                .add(18, 'I')
                .add(19, 'O')
                .add(20, 'P')
                .add(21, 'Y')
                .add(22, 'T')
                .add(23, 'R')
                .add(24, 'E')
                .add(25, 'W')
                .add(26, 'B')
                .add(27, 'Z')
                .add(28, '!')
                .add(29, 'J')
                .build();

        Matrix k = MatrixFactory.newMatrix()
                // 7x7
                .add(7,  13, 23,  6,  15, 11, 12)
                .add(8,  29, 17,  1,  8,  18, 15)
                .add(19, 26, 6,   15, 24, 25, 18)
                .add(12, 16, 12,  21, 25, 28, 3)
                .add(14, 18, 19,  22, 20, 3,  24)
                .add(26, 22, 13,  14, 21, 6,  14)
                .add(27, 11, 14,  12, 4,  4,  20)
                .build();
        Matrix m = map.convertToMatrix("THIS IS AN ENCRYPTED MESSAGE. WE WILL ATTACK AT DAWN. DONT BE LATE", k.rows, 28);
        Matrix c = HillCipher.encrypt(m, k, map.modulus);
        Matrix md = HillCipher.decrypt(c, k, map.modulus);

        System.out.println("Message: " + map.convertToString(m));
        System.out.println("Key: " + map.convertToString(k));
        System.out.println("Encrypted: " + map.convertToString(c));
        System.out.println("Decrypted: " + map.convertToString(md));
    }
}