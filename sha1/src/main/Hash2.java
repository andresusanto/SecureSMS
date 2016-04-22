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
    private static final int h0 = 0x67452301;
    private static final int h1 = 0xEFCDAB89;
    private static final int h2 = 0x98BADCFE;
    private static final int h3 = 0x10325476;
    private static final int h4 = 0xC3D2E1F0;
    
    private static final int k0 = 0x5A827999;
    private static final int k1 = 0x6ED9EBA1;
    private static final int k2 = 0x8F1BBCDC;
    private static final int k3 = 0xCA62C1D6;
    
    private byte [] paddingData(byte [] data)
    {
        int panjangData = data.length;
        int panjangSisa = data.length % 64;
        
        int panjangPadding = 0;
        if ((64 - panjangSisa >= 9)) {
            panjangPadding = 64 - panjangSisa;
        }
        else{
            panjangPadding = 128 - panjangSisa;
        }
        
        byte [] dataPadding = new byte[panjangPadding];
        dataPadding[0] = (byte) 0x80;
        
        int panjangBit = panjangData * 8;
        
        for(int i = 0; i < 8; i++)
        {
            dataPadding[dataPadding.length - 1 - i] = (byte) ((panjangBit >> (8 * i)) & 0x00000000000000FF);
        }
        
        byte [] hasil = new byte[panjangData + panjangPadding];
        System.arraycopy(data, 0, hasil, 0, panjangData);
        System.arraycopy(dataPadding, 0, hasil, panjangData, panjangPadding);
        System.err.println(this.bytesToHexString(data));
        return hasil;
    }
    public static void main(String[] args) {
        Hash2 hash = new Hash2();
        hash.paddingData("a".getBytes());
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
}
