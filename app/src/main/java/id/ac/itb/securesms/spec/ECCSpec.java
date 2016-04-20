package id.ac.itb.securesms.spec;

import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.util.Random;

import id.ac.itb.securesms.engine.ECC;
import id.ac.itb.securesms.engine.Tools;
import id.ac.itb.securesms.obj.Coordinate;
import id.ac.itb.securesms.obj.Curve;

/**
 * Created by Andre on 4/21/2016.
 */
public class ECCSpec {
    // contoh konfigurasi kurva
    public static BigInteger a = new BigInteger("DB7C2ABF62E35E668076BEAD2088", 16);
    public static BigInteger b = new BigInteger("659EF8BA043916EEDE8911702B22", 16);
    public static BigInteger p = new BigInteger("DB7C2ABF62E35E668076BEAD208B", 16);
    public static BigInteger n = new BigInteger("DB7C2ABF62E35E7628DFAC6561C5", 16);

    public static Coordinate base = new Coordinate(new BigInteger("09487239995A5EE76B55F9C2F098", 16), new BigInteger("A89CE5AF8724C0A23E0E0FF77500", 16));

    // pvt key relatif bebas diubah2, tidak dependen dengan variabel2 diatas
    public static BigInteger privateKey = new BigInteger("DBCABF2E3E7E35E66806BEAD208B", 16);


    public static void testDSA(){
        Log.d("SPEC", "================================== TESTING ECDSA =============================");
        Curve curve = new Curve(a, b, p, n);
        ECC ecc = new ECC(curve, base, privateKey);

        Coordinate publicKey = ecc.generatePublic();

        // buat plain teks
        byte[] b = new byte[32];
        new Random().nextBytes(b);

        // tampilkan input
        Tools.printBytes(b, "Input");

        byte signature[] = ecc.sign(b); // input dari sign bisa data, atau HASH nya

        // tampilkan signature
        Tools.printBytes(signature, "Signature");
        String base64 = Base64.encodeToString(signature, Base64.DEFAULT);
        Log.d("Dalam Base64", base64);
        byte signdecode[] = Base64.decode(base64, Base64.DEFAULT);

        Tools.printBytes(signdecode, "Signature decode");

        // lakukan validasi signature
        boolean isValid = ecc.verify(b, signature, publicKey);
        Log.d("IsValid 1?", Boolean.toString(isValid));

        // sekarang ubah sedikit signature
        signature[1] = (byte)((signature[1] + 12) % 256);
        isValid = ecc.verify(b, signature, publicKey);
        Log.d("IsValid 2?", Boolean.toString(isValid));

        // kembalikan signature
        signature[1] = (byte)((signature[1] - 12) % 256);
        isValid = ecc.verify(b, signature, publicKey);
        Log.d("IsValid 3?", Boolean.toString(isValid));

        // sekarang ubah sedikit pesan
        b[1] = (byte)((b[1] - 12) % 256);
        isValid = ecc.verify(b, signature, publicKey);
        Log.d("IsValid 4?", Boolean.toString(isValid));

        // kembalikan pesan
        b[1] = (byte)((b[1] + 12) % 256);
        isValid = ecc.verify(b, signature, publicKey);
        Log.d("IsValid 5?", Boolean.toString(isValid));

    }
}
