/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

/**
 * 1 chunk ada 80 Words
 * @author akhfa
 */
public class Chunk {
    private boolean [] chunk;
    public Chunk (boolean [] _chunk)
    {
        this.chunk = _chunk;
    }
    
    public boolean [] getChunkData()
    {
        return chunk;
    }
}
