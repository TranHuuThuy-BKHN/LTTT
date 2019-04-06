package Resource;

import java.io.*;
import java.math.BigInteger;

public class RSA {
    private static BigInteger PUBLIC_KEY;

    public RSA() {
        PUBLIC_KEY = publicKey();
    }

    private BigInteger Q() {
        return Key.p.subtract(BigInteger.ONE).multiply(Key.q.subtract(BigInteger.ONE));
    }

    //phương thức tính khóa công khai
    private BigInteger publicKey() {
        BigInteger res = new BigInteger(Key.R.nextInt(60_000) + "");
        BigInteger q = Q();
        while (res.gcd(q).equals(BigInteger.ONE) == false) {
            res = new BigInteger(Key.R.nextInt(60_000) + 30_000 + "");
        }
        return res;
    }

    public BigInteger getPublicKey() {
        return PUBLIC_KEY;
    }

    //phương thức tính khóa bí mật
    private BigInteger privateKey() {
        BigInteger qp = Q();
        BigInteger pr = qp.add(BigInteger.ONE).divide(PUBLIC_KEY);
        int n = 0;
        while (pr.multiply(PUBLIC_KEY).subtract(BigInteger.ONE).mod(qp).equals(BigInteger.ZERO) == false || n < 20) {
            pr = pr.add(BigInteger.ONE);
            n++;
        }
        return pr;
    }

    //phương thức tính a^b % c
    private int mod(int a, BigInteger b, BigInteger c) {
        return new BigInteger(a + "").modPow(b, c).intValue();
    }

    //phương thức mã hóa file, sử dụng thuật toán mã hóa RSA
    public void encoding(File file) throws IOException {
        if (file.exists()) {
            System.out.println("Encoding...");
            FileInputStream fileInputStream = new FileInputStream(file);
            int[] b = new int[(int) file.length()];
            for (int i = 0; i < b.length; i++) {
                b[i] = fileInputStream.read();
                b[i] = mod(b[i], PUBLIC_KEY, Key.p.multiply(Key.q));
            }
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (int i : b) {
                fileOutputStream.write(i);
            }
            fileOutputStream.close();
        }
    }

    //phương thức giải mã  file, sử dụng thuật toán mã hóa RSA
    public void decoding(File file) throws IOException {
        if (file.exists()) {
            System.out.println("Decoding...");
            FileInputStream fileInputStream = new FileInputStream(file);
            BigInteger prKey = privateKey();
            int[] b = new int[(int) file.length()];
            for (int i = 0; i < b.length; i++) {
                b[i] = fileInputStream.read();
                b[i] = mod(b[i], prKey, Key.p.multiply(Key.q));
            }
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (int i : b) {
                fileOutputStream.write(i);
            }
            fileOutputStream.close();
        }
    }

    public static void main(String[] args) {
       // double x = 1 / 0.0;
        double y = 1 / 0;

        System.out.println(y);

    }
}