package Resource;

import java.math.BigInteger;
import java.util.Random;

public class Key {
    static final Random R = new Random(1);
    static final BigInteger p = BigInteger.probablePrime(4, R);
    static final BigInteger q = BigInteger.probablePrime(4, R);
}
