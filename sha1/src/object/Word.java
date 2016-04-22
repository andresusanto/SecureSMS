/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import main.Tools;

/**
 *
 * @author akhfa
 */
public class Word {
    private boolean [] word;
    public static final int WORD_SIZE = 32;
    
    public Word(boolean [] _word)
    {
        this.word = _word;
    }
    
    public boolean [] getWordData()
    {
        return word;
    }
    
    public Word xor(Word anotherWord)
    {
//        Word result = new Word(WORD_SIZE);
        boolean [] wordData = new boolean[WORD_SIZE];
        boolean [] anotherWordData = anotherWord.getWordData();
        for(int i = 0; i < word.length; i++)
        {
            wordData[i] = this.word[i] ^ anotherWordData[i];
        }
        
        return new Word(wordData);
    }
    
    public Word rotateLeft(int count)
    {
        return new Word(Tools.shiftLeft(word, count));
    }
}
