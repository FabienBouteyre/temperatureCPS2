package cps2.project.temperature.Encryption;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private int bitlength = 256;
    private Random r;

    public RSA() {

        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
        {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);

    }

    public BigInteger getPublicKey(){
        return e;
    }

    public BigInteger getPrivateKey(){
        return d;
    }

    public BigInteger getMode(){
        return N;
    }

    public static String bytesToString(byte[] encrypted)
    {
        String str = "";
        for (byte b : encrypted)
        {
            str += Byte.toString(b);
        }
        return str;
    }

    // Encrypt message
    public byte[] encrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    public static byte[] encryptCustom(byte[] message, BigInteger e, BigInteger N)
    {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypt message
    public byte[] decrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }

    public static byte[] decryptCustom(byte[] message, BigInteger d, BigInteger N)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }

}
