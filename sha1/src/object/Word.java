/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

/**
 *
 * @author akhfa
 */
public class Word {
    private boolean [] word;
    public Word(boolean [] _word)
    {
        this.word = _word;
    }
    
    public boolean [] getWordData()
    {
        return word;
    }
}
