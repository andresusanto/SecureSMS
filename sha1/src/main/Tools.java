/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tools {
    
    public static String bytesToHexString(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    public static String integerToHexString(int data)
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
        }
        return temp;
    }
    
    public static byte [] getShuffled(String strSeed, byte [] data)
    {
        // bentuk seed untuk random
        long seed = 0;
        for (int i = 0; i < strSeed.length(); i++)
            seed += (long)strSeed.charAt(i);
        
        List <Boolean> arrToShuffle = Tools.byteToBool(data);
        Collections.shuffle(arrToShuffle, new Random(seed));
        return Tools.boolToByte(arrToShuffle);
    }
    
    /**
     * Melakukan bit shift terhadap array of byte
     * @param input array of byte yang akan di shift
     * @param count banyaknya jumlah shift yang dilakukan
     * @return 
     */
    public static byte[] shiftLeft(byte [] input, int count){
        boolean [] inputbit = Tools.convertToBoolArray(input);
        boolean [] left = new boolean[count];
        
        // simpan bit kiri yang akan dipindahkan ke paling belakang
        System.arraycopy(inputbit, 0, left, 0, count);
        
        // geser bit ke kiri sebanyak count
        int idx = 0;
        for(int i = count; i < inputbit.length; i++)
        {
            inputbit[idx] = inputbit[i];
            idx++;
        }
        System.arraycopy(left, 0, inputbit, inputbit.length - count, left.length);
        return Tools.convertToByte(inputbit);
    }
    
    public static boolean [] shiftLeft(boolean [] inputbit, int count){
        boolean [] left = new boolean[count];
        
        // simpan bit kiri yang akan dipindahkan ke paling belakang
        System.arraycopy(inputbit, 0, left, 0, count);
        
        // geser bit ke kiri sebanyak count
        int idx = 0;
        for(int i = count; i < inputbit.length; i++)
        {
            inputbit[idx] = inputbit[i];
            idx++;
        }
        System.arraycopy(left, 0, inputbit, inputbit.length - count, left.length);
        return inputbit;
    }
    
    public static int shiftLeft(int value, int bits) {
        int q = (value << bits) | (value >>> (32 - bits));
        return q;
    }
    
    /**
     * Melakukan bit shift terhadap array of byte
     * @param input array of byte yang akan di shift
     * @param count banyaknya jumlah shift yang dilakukan
     * @return 
     */
    public static byte[] shiftRight(byte [] input, int count){
        boolean [] inputbit = Tools.convertToBoolArray(input);
        boolean [] Right = new boolean[count];
        
        // simpan bit kanan yang akan dipindahkan ke paling belakang
        System.arraycopy(inputbit, inputbit.length - count, Right, 0, count);
        
        // geser bit ke kanan sebanyak count, yang digeser inputbit.length - count
        int idx = count;
        for(int i = inputbit.length - 1; i >= count; i--)
        {
            inputbit[i] = inputbit[i - idx];
        }
        
        System.arraycopy(Right, 0, inputbit, 0, Right.length);
        return Tools.convertToByte(inputbit);
    }
    
    /**
     * Mendapatkan value random dari seed yang berupa string tertentu
     * @param strSeed
     * @param min
     * @param max
     * @return 
     */
    public static int[] getShuffledInts(String strSeed, int min, int max){
        // bentuk seed untuk random
        long seed = 0;
        for (int i = 0; i < strSeed.length(); i++)
            seed += (long)strSeed.charAt(i);
        
        // buat array berisi angka dari min sd max
        List<Integer> arrayToShuffle = new ArrayList<Integer>();
        for (int i = min; i <= max; i++)
            arrayToShuffle.add(i); 
        
        // acak-acak dengan seed yang diberikan
        Collections.shuffle(arrayToShuffle, new Random(seed));
        
        // convert ke primitive
        int shuffledInts[] = new int[max - min + 1];
        for (int i = 0; i < shuffledInts.length; i++){
            shuffledInts[i] = arrayToShuffle.get(i);
        }
        
        return shuffledInts;
    }
    
    // Konversi Boolean >< Byte
    public static boolean[] convertToBoolArray(byte[] bytes) {
        return Tools.convert(bytes, bytes.length * 8);
    }
    
    public static List<Boolean> byteToBool(byte [] key) {
        List<Boolean> bList = new ArrayList<>();
        for(int x=0; x<key.length; x++) {
            boolean bit;
            byte c= key[x];
            for(int i=0; i<8; i++) {
                bit = (c & (1 << 7-i))!=0;
                bList.add(bit);
            }
        }
        return bList;
    }
    
    public static byte [] boolToByte(List <Boolean> bool)
    {
        byte[] data = new byte[(int)bool.size()/8];
        for(int i=0; i<data.length; i++) {
            data[i] = 0;
            for(int j=0; j<8; j++)
                data[i] += ((bool.get(i*8+j)? 1:0) << (7-j));
        }
        return data;
    }
    
    public static byte [] boolToByte(boolean[] bool)
    {
        byte[] data = new byte[(int)bool.length/8];
        for(int i=0; i<data.length; i++) {
            data[i] = 0;
            for(int j=0; j<8; j++)
                data[i] += ((bool[i*8+j]? 1:0) << (7-j));
        }
        return data;
    }
    
    public static byte[] convertToByte(boolean[] booleanOfData) {
        int length = booleanOfData.length / 8;
        int mod = booleanOfData.length % 8;
        if(mod != 0){
                ++length;
        }
        byte[] retVal = new byte[length];
        int boolIndex = 0;
        for (int byteIndex = 0; byteIndex < retVal.length; ++byteIndex) {
                for (int bitIndex = 7; bitIndex >= 0; --bitIndex) {
                        // Another bad idea
                        if (boolIndex >= booleanOfData.length) {
                                return retVal;
                        }
                        if (booleanOfData[boolIndex++]) {
                                retVal[byteIndex] |= (byte) (1 << bitIndex);
                        }
                }
        }
        return retVal;
    }
    private static boolean[] convert(byte[] bits, int significantBits) {
        boolean[] retVal = new boolean[significantBits];
        int boolIndex = 0;
        for (int byteIndex = 0; byteIndex < bits.length; ++byteIndex) {
                for (int bitIndex = 7; bitIndex >= 0; --bitIndex) {
                        if (boolIndex >= significantBits) {
                                // Bad to return within a loop, but it's the easiest way
                                return retVal;
                        }

                        retVal[boolIndex++] = (bits[byteIndex] >> bitIndex & 0x01) == 1 ? true
                                        : false;
                }
        }
        return retVal;
    }
    
    public static void printMatriks(boolean [] matriks)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                System.err.print(matriks[i * 8 + j] == true? 1: 0);
            }
            System.err.println("");
        }
    }
    
    public static void printArray(boolean[] array)
    {
        for(boolean b : array)
            System.err.print(b? 1:0);
        System.err.println("");
    }
    
    public static byte[] intToBytes(int value) {
    return new byte[] {
            (byte)(value >> 24),
            (byte)(value >> 16),
            (byte)(value >> 8),
            (byte)value};
    }
    
    public static int bytesToInt(byte[] bytes) {
        return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }
    
    public static String bytesToString(byte [] bytes) {
        return new String(bytes);
    }
    
    public static byte [] stringToBytes(String string)
    {
        return string.getBytes();
    }
    
    public static byte [] floatToByte(float value)
    {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }
    
    public static float bytesToFloat(byte [] value)
    {
        return ByteBuffer.wrap(value).getFloat();
    }
    
    public static int oneByteToInt(boolean [] value)
    {
        int result = 0;
        for(int i = value.length - 1; i >= 0; i--)
        {
            if(value[i])
                result += Math.pow(2, 7 - i);
        }
        return result;
    }
}
