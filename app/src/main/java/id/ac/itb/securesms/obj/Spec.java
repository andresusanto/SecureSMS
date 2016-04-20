package id.ac.itb.securesms.obj;

import java.math.BigInteger;

/**
 * Created by Andre on 4/21/2016.
 */
public class Spec {
    // contoh konfigurasi kurva
    public static BigInteger a = new BigInteger("DB7C2ABF62E35E668076BEAD2088", 16);
    public static BigInteger b = new BigInteger("659EF8BA043916EEDE8911702B22", 16);
    public static BigInteger p = new BigInteger("DB7C2ABF62E35E668076BEAD208B", 16);
    public static BigInteger n = new BigInteger("DB7C2ABF62E35E7628DFAC6561C5", 16);

    public static Coordinate base = new Coordinate(new BigInteger("09487239995A5EE76B55F9C2F098", 16), new BigInteger("A89CE5AF8724C0A23E0E0FF77500", 16));

    // pvt key relatif bebas diubah2, tidak dependen dengan variabel2 diatas
    public static BigInteger privateKey = new BigInteger("DBCABF2E3E7E35E66806BEAD208B", 16);
}
