package cps2.project.temperature.Encryption;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class ExmRSA {

    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private int        bitlength = 256;
    private Random     r;

    public ExmRSA() {
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

    public ExmRSA(BigInteger n, BigInteger e, BigInteger d) {
        this.N = n;
        this.e = e;
        this.d = d;
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException
    {
        ExmRSA rsa = new ExmRSA(new BigInteger("6948827810132572059417603982164864013364137666889258116204903471022329557266351641918523780902956309164479093969842570129674384287589318830765510625548061")
                , new BigInteger("212863422706047133233307035026833589747"),
                new BigInteger("6370991045547577689485566444489004250444781266798669819839856051086935342404687579534648649494529795411098858643962663282220903332658033163126721588798083")
                );
        DataInputStream in = new DataInputStream(System.in);
        String teststring;

        System.out.println("Enter the plain text:");
        teststring = in.readLine();
        System.out.println("Encrypting String: " + teststring);
        System.out.println("String in Bytes: "
                + bytesToString(teststring.getBytes()));
        // encrypt
        byte[] encrypted = rsa.encrypt(teststring.getBytes());
        // decrypt
        byte[] decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypting Bytes: " + bytesToString(decrypted));
        System.out.println("Decrypted String: " + new String(decrypted));
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
        System.out.println("e: " + e);
        System.out.println("d: " + d);
        System.out.println("n: " + N);
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypt message
    public byte[] decrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }

}
