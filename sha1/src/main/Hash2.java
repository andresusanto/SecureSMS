/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author akhfa
 */
public class Hash2 {
    private int h0 = 0x67452301;
    private int h1 = 0xEFCDAB89;
    private int h2 = 0x98BADCFE;
    private int h3 = 0x10325476;
    private int h4 = 0xC3D2E1F0;
    
    private static final int K0 = 0x5A827999;
    private static final int K1 = 0x6ED9EBA1;
    private static final int K2 = 0x8F1BBCDC;
    private static final int K3 = 0xCA62C1D6;
    
    int A, B, C, D, E, F;
    
    public String sha1sum(byte [] data)
    {
        byte [] data_64_byte = this.paddingData(data);
        int chunkCounter = data_64_byte.length / 64;
        byte [] chunk = new byte[64];
        
        for(int i = 0; i < chunkCounter; i++)
        {
            System.arraycopy(data_64_byte, 64 * i, chunk, 0, 64);
            this.blockProcessing(chunk);
        }
        
        return integerToHexString(h0) + integerToHexString(h1) + integerToHexString(h2) + integerToHexString(h3) + integerToHexString(h4);
    }
    
    private byte [] paddingData(byte [] data)
    {
        int panjangData = data.length;
        int panjangSisa = data.length % 64;
        
        int panjangPadding;
        if ((64 - panjangSisa >= 9)) {
            panjangPadding = 64 - panjangSisa;
        }
        else{
            panjangPadding = 128 - panjangSisa;
        }
        
        byte [] dataPadding = new byte[panjangPadding];
        dataPadding[0] = (byte) 0x80;
        
        long panjangBit = panjangData * 8;
        
        for(int i = 0; i < 8; i++)
        {
            dataPadding[dataPadding.length - 1 - i] = (byte) ((panjangBit >> (8 * i)) & 0x00000000000000FF);
        }
        
        byte [] hasil = new byte[panjangData + panjangPadding];
        System.arraycopy(data, 0, hasil, 0, panjangData);
        System.arraycopy(dataPadding, 0, hasil, panjangData, panjangPadding);
        System.err.println(this.bytesToHexString(dataPadding));
        return hasil;
    }
    
    private void blockProcessing(byte [] chunk)
    {
        int [] words = new int[80];
        System.err.println("chunk[] = " + bytesToHexString(chunk));
        
        // pecah 1 chunk jadi 16 word dari word 0 - 15
        for(int i = 0; i < 16; i++)
        {
            int temp = 0;
            for(int j = 0; j < 4; j++)
            {
                temp = (chunk[i * 4 + j] & 0x000000FF) << (24 - j * 8);
                words[i] = words[i] | temp;
            }
//            System.err.println("words [" + i + "] = " + Integer.toHexString(words[i]));
        }
        
        // Buat word dari 16 - 79
        for(int i = 16; i < 80; i++)
        {
            words[i] = Tools.shiftLeft(words[i - 3] ^ words[i - 8] ^ words[i - 14] ^ words[i - 16], 1);
        }
        
        A = h0;
        B = h1;
        C = h2;
        D = h3;
        E = h4;
        
        for(int i = 0; i < 80; i++)
        {
            int temp = Tools.shiftLeft(A, 5) + E + words[i] + 
            ((i < 20) ?
                K0 + ((B & C) | ((~B) & D)): 
            (i < 40) ? 
                K1 + (B ^ C ^ D):
            (i < 60)?
                K2 + ((B & C) | (B & D) | (C & D)):
                K3 + (B ^ C ^ D));
            
            E = D;
            D = C;
            C = Tools.shiftLeft(B, 30);
            B = A;
            A = temp;
        }
        
        h0 += A;
        h1 += B;
        h2 += C;
        h3 += D;
        h4 += E;
        
        System.out.println("H0:" + Integer.toHexString(h0));
        System.out.println("H1:" + Integer.toHexString(h1));
        System.out.println("H2:" + Integer.toHexString(h2));
        System.out.println("H3:" + Integer.toHexString(h3));
        System.out.println("H4:" + Integer.toHexString(h4));
    }
    
    public static void main(String[] args) {
        Hash2 hash = new Hash2();
//        System.err.println(hash.sha1sum("a".getBytes()));
        System.err.println(hash.sha1sum("abcdefgh1jklmnopqrstuvwxyzabcdefgh1jklmnopqrstuvwxyzabcdefgh1jklmnopqrstuvwxyzabcdefgh1jklmnopqrstuvwxyzabcdefgh1jklmnopqrstuvwxyzabcdefgh1jklmnopqrstuvwxyzabcdefgh1jklmnopqrstuvwxyzabcdefgh1jklmnopqrstuvwxyzabcdefgh1jklmnopqrstuvwxyzabcdefgh1jklmnopqrstuvwxyz".getBytes()));
    }
    
    public String bytesToHexString(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    private String integerToHexString(int data)
    {
        String temp = Integer.toHexString(data);
        switch(temp.length())
        {
            case 1:
                temp = "0000000" + temp;
                break;
            case 2:
                temp = "000000" + temp;
                break;
            case 3:
                temp = "00000" + temp;
                break;
            case 4:
                temp = "0000" + temp;
                break;
            case 5:
                temp = "000" + temp;
                break;
            case 6:
                temp = "00" + temp;
                break;
            case 7:
                temp = "0" + temp;
                break;
            default:
                temp = temp;
        }
        return temp;
    }
}
