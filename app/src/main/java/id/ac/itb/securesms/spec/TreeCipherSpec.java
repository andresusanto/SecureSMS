package id.ac.itb.securesms.spec;

import android.util.Log;

import java.io.IOException;
import java.util.Random;

import id.ac.itb.securesms.engine.Tools;
import id.ac.itb.securesms.engine.TreeCipher;
import id.ac.itb.securesms.obj.TreeCipherBlock;

/**
 * Created by Andre on 4/21/2016.
 */
public class TreeCipherSpec {
    public static void testTreeCipher() throws IOException {
        Log.d("SPEC", "================================== TESTING TREE CIPHER =============================");
        // buat test key, karena block size = 128, maka key = 16 bytes
        byte[] bkey = new byte[16];
        new Random().nextBytes(bkey);
        TreeCipherBlock key = new TreeCipherBlock(bkey);

        Tools.printBytes(bkey, "KEY: ");

        // tree cipher
        TreeCipher cip = new TreeCipher(key);

        // buat data plain text
        byte[] plain = new byte[256]; // 256 = 16 blok
        new Random().nextBytes(plain);

        TreeCipherBlock dataBlocks [] = TreeCipherBlock.build(plain); // convert: byte[] -> block

        Tools.printBytes(plain, "PLAIN: ");

        cip.encrypt(dataBlocks); // proses enkripsi. Data langsung di enkrip pada variabel yang sama

        byte cipher[] = TreeCipherBlock.toBytes(dataBlocks); // convert: block -> byte[]
        Tools.printBytes(cipher, "CIPHER: ");

        // PENTING: harus inisiasi tree cipher baru lagi setiap tree cipher habis digunakan
        // karena tree cipher memodifikasi tree internal
        cip = new TreeCipher(key);
        cip.decrypt(dataBlocks);

        byte[] decrypt = TreeCipherBlock.toBytes(dataBlocks);
        Tools.printBytes(decrypt, "Decrypt: ");
    }

}
