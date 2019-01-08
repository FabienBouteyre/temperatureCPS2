package cps2.project.temperature.Encryption;

import java.math.BigInteger;

public class Keys {

    public static BigInteger e;
    public static BigInteger d;
    public static BigInteger N;

    public static BigInteger getE() {
        return e;
    }

    public static void setE(BigInteger e) {
        Keys.e = e;
    }

    public static BigInteger getD() {
        return d;
    }

    public static void setD(BigInteger d) {
        Keys.d = d;
    }

    public static BigInteger getN() {
        return N;
    }

    public static void setN(BigInteger n) {
        N = n;
    }
}
