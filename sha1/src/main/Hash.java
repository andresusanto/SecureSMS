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
public class Hash {
    private static final int h0 = 0x67452301;
    private static final int h1 = 0xEFCDAB89;
    private static final int h2 = 0x98BADCFE;
    private static final int h3 = 0x10325476;
    private static final int h4 = 0xC3D2E1F0;
    
    private boolean [] message;
    
    private boolean [] message_mod512;
    public Hash(String _message)
    {
        byte [] message_byte = _message.getBytes();
        this.message = Tools.convertToBoolArray(message_byte);
    }
    
    private void initialize()
    {
        this.message_mod512 = new boolean[this.message.length / 512 + 1];
        for (int i = 0; i < this.message_mod512.length; i++) {
            this.message_mod512[i] = false;
        }
    }
    
    
}
