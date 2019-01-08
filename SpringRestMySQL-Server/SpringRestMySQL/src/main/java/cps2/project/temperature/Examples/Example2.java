package cps2.project.temperature.Examples;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class Example2 {

    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private int bitlength = 256;
    private Random r;


    public Example2()
    {
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

    public Example2(BigInteger e, BigInteger d, BigInteger N)
    {
        this.e = e;
        this.d = d;
        this.N = N;
    }
//    public Example2(BigInteger p, BigInteger q)
//    {
//        this.p = p;
//        this.q = q;
//        N = p.multiply(q);
//        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
//        e = BigInteger.valueOf(11);
//        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
//        {
//            e.add(BigInteger.ONE);
//        }
//        d = e.modInverse(phi);
//    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException
    {
//        DataInputStream in = new DataInputStream(System.in);
//        BigInteger pt = BigInteger.valueOf(in.readInt());
//        BigInteger qt = BigInteger.valueOf(in.readInt());
        Example2 rsa = new Example2();
        DataInputStream in = new DataInputStream(System.in);
        String teststring;
        System.out.println("Enter the plain text:");
        teststring = in.readLine();
        System.out.println("Encrypting String: " + teststring);
        System.out.println("String in Bytes: " + bytesToString(teststring.getBytes()));
        // encrypt
        byte[] encrypted = rsa.encrypt(teststring.getBytes());
        // decrypt
        byte[] decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypting Bytes: " + bytesToString(decrypted));
        System.out.println("Decrypted String: " + new String(decrypted));

        System.out.println("p: " + rsa.p);
        System.out.println("q: " + rsa.q);
        System.out.println("phi: " + rsa.phi);
        System.out.println("mod: " + rsa.N);
        System.out.println("e: " + rsa.e);
        System.out.println("d: " + rsa.d);
    }

    private static String bytesToString(byte[] encrypted)
    {
        String test = "";
        for (byte b : encrypted)
        {
            test += Byte.toString(b);
        }
        return test;
    }

    // Encrypt message
    public byte[] encrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypt message
    public byte[] decrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }




}
