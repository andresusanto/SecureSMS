/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import object.Chunk;
import object.Word;

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
    
    private static final int CHUNK_SIZE = 512;
    
    private boolean [] message;
    
    private boolean [] message_mod512;
    private boolean [] words_sejumlah_80;
    
    private ArrayList<Chunk> chunks;
    private ArrayList<Word> words;
    
    public Hash(String _message)
    {
        this.chunks = new ArrayList<>();
        this.words = new ArrayList<>();
        byte [] message_byte = _message.getBytes();
        this.message = Tools.convertToBoolArray(message_byte);
        this.initialize();
    }
    
    private void initialize()
    {
        int jumlahChunk = (this.message.length / CHUNK_SIZE) + 1;
        
        // init chunk dengan true
        this.message_mod512 = new boolean[jumlahChunk * CHUNK_SIZE];
        for (int i = 0; i < this.message_mod512.length; i++) {
            this.message_mod512[i] = true;
            System.err.println(message_mod512[i]? "true":"false" + i);
        }
        
        //init word_80 dengan false
        this.words_sejumlah_80 = new boolean[80 * jumlahChunk * Word.WORD_SIZE];
        for(int i = 0; i < this.words_sejumlah_80.length; i++)
        {
            this.words_sejumlah_80[i] = false;
        }
        
        // Bagi message_mod512 menjadi chunks
        for(int i = 0; i < this.message_mod512.length / CHUNK_SIZE ; i++)
        {
            boolean [] chunkData = new boolean[CHUNK_SIZE];
            System.arraycopy(this.message_mod512, i, chunkData, 0, CHUNK_SIZE);
            Chunk chunk = new Chunk(chunkData);
            this.chunks.add(chunk);
        }
    }
    
    public String getSHA1()
    {
        // untuk setiap chunk, bagi jadi 80 words @ 32 bit
        for (Chunk chunk : chunks) {
            boolean [] chunkData = chunk.getChunkData();
            
            // copy 1 chunk ke 16 word pertama
            for(int i = 0; i < 16; i++)
            {
                boolean [] wordData = new boolean[Word.WORD_SIZE];
                System.arraycopy(chunkData, i * Word.WORD_SIZE, wordData, 0, Word.WORD_SIZE);
                Word word = new Word(wordData);
                words.add(word);
            }
            
            // bikin words berikutnya hingga jumlah words 80
            for(int i = 16; i < 80; i++)
            {
                Word newWord = words.get(i - 3).xor(words.get(i - 8)).xor(words.get(i-14)).xor(words.get(i-16));
                words.add(newWord);
            }
            
        }
        
        // cara lain
        // untuk setiap chunk
//        for (int i = 0; i < jumlahChunk ; i++)
//        {
//            // extends jadi words_80
//            System.arraycopy(message_mod512, i, words_sejumlah_80, i, i);
//        }
        return "";
    }
    
    
}
