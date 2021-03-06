package id.ac.itb.securesms.obj;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author power
 */
public class TreeCipherBlock {
    public static final int HALF_LEFT = 1;
    public static final int HALF_RIGHT = 0;

    public static final int BLOCK_SIZE = 128; // dalam satuan bit
    public boolean content[];

    private TreeCipherBlock parent; // untuk keperluan membentuk pohon enkripsi


    public TreeCipherBlock(byte data[]){
        byte input[];
        if (data.length % (BLOCK_SIZE/8) != 0){
            int bytestoPad = (BLOCK_SIZE/8) - data.length % (BLOCK_SIZE/8);
            input = new byte[data.length + bytestoPad];
            System.arraycopy(data, 0, input, 0, data.length);
        }else{
            input = data;
        }

        this.content = new boolean[BLOCK_SIZE];

        for (int i = 0; i < BLOCK_SIZE/8; i++){
            for (int j = 0; j < 8; j++){
                this.content[i * 8 + j] = (((input[i] >> j) & 1) == 1);
            }
        }
    }

    public TreeCipherBlock(byte data[], int start){
        this.content = new boolean[BLOCK_SIZE];

        for (int i = 0; i < BLOCK_SIZE/8; i++){
            for (int j = 0; j < 8; j++){
                this.content[i * 8 + j] = (((data[i + start] >> j) & 1) == 1);
            }
        }
    }

    private static byte[] bitPadding(byte[] data){
        if (data.length % (BLOCK_SIZE/8) != 0){
            int bytestoPad = (BLOCK_SIZE/8) - data.length % (BLOCK_SIZE/8);
            byte result[] = new byte[data.length + bytestoPad];
            System.arraycopy(data, 0, result, 0, data.length);
            return result;
        }else{
            return data;
        }
    }

    public static TreeCipherBlock[] build(byte data[]) throws IOException{
        byte input[] = bitPadding(data);
        TreeCipherBlock[] result = new TreeCipherBlock[input.length / (BLOCK_SIZE/8)];
        for(int i = 0; i < input.length; i += (BLOCK_SIZE/8)){
            result[i / (BLOCK_SIZE/8)] = new TreeCipherBlock(input, i);
        }
        return result;
    }

    public static byte[] toBytes(TreeCipherBlock[] data){
        byte bytesData[] = new byte[data.length * (BLOCK_SIZE/8)];
        for (int i = 0; i < data.length; i++){
            byte curBytesData[] = data[i].getBytes();
            for (int j = 0; j < (BLOCK_SIZE/8); j++){
                bytesData[i * (BLOCK_SIZE/8) + j] = curBytesData[j];
            }
        }
        return bytesData;
    }

    public byte[] getBytes(){
        byte result[] = new byte[BLOCK_SIZE/8];

        for (int i = 0; i < BLOCK_SIZE/8; i++){
            byte data = 0;
            for (int j = 0; j < 8; j++){
                if (this.content[i * 8 + j]){
                    data |= 1 << j;
                }
            }
            result[i] = data;
        }
        return result;
    }

    public TreeCipherBlock(TreeCipherBlock other){
        this.content = new boolean[other.content.length];
        System.arraycopy(other.content, 0, this.content, 0, other.content.length);
    }

    private List<TreeCipherBlock> childs = new LinkedList<>();

    public void setParent(TreeCipherBlock parent){
        this.parent = parent;
        parent.childs.add(this);
    }

    public TreeCipherBlock getParent(){
        return this.parent;
    }

    public void push(TreeCipherBlock element){
        if (parent != null){
            parent.push(this);
        }
        this.content = element.content;
    }

    public void printChilds(){
        for(TreeCipherBlock child : childs){
            child.printData();
        }
    }

    public void pad(byte other){
        rotaryShiftLeft(8); // geser 8 bits
        for (int j = 0; j < 8; j++){
            this.content[j] = (((other >> j) & 1) == 1);
        }
    }

    public void xor(TreeCipherBlock other){
        for (int i = 0; i < BLOCK_SIZE; i++){
            this.content[i] = other.content[i] ^ this.content[i];
        }
    }

    public void halfXor(TreeCipherBlock other, int originalHalf, int otherHalf){
        int padOriginal = 0; int padOther = 0;
        if (originalHalf == HALF_LEFT) padOriginal = BLOCK_SIZE/2;
        if (otherHalf == HALF_LEFT) padOther = BLOCK_SIZE/2;

        for (int i = 0; i < BLOCK_SIZE/2; i++){
            this.content[i + padOriginal] = other.content[i + padOther] ^ this.content[i + padOriginal];
        }
    }

    public void cutShuffle(int k){
        boolean result[] = new boolean[BLOCK_SIZE];
        System.arraycopy(this.content, k, result, 0, this.content.length - k);
        for (int i = 0; i < k; i++){
            result[this.content.length - k + i] = this.content[i];
        }
        this.content = result;
    }

    public void rotaryShiftRight(int k){
        boolean result[] = new boolean[BLOCK_SIZE];

        for (int i = 0; i < k; i++){
            result[BLOCK_SIZE - k + i] = this.content[i];
        }

        for (int i = k; i < BLOCK_SIZE; i++){
            result[i - k] = this.content[i];
        }

        this.content = result;
    }

    public void rotaryShiftLeft(int k){
        boolean result[] = new boolean[BLOCK_SIZE];

        for (int i = BLOCK_SIZE; i < BLOCK_SIZE - k; i--){
            result[BLOCK_SIZE - i] = this.content[i];
        }

        for (int i = 0; i < BLOCK_SIZE - k; i++){
            result[k + i] = this.content[i];
        }

        this.content = result;
    }

    public void printData(){
        for (int i = BLOCK_SIZE - 1 ; i >= 0; i--){
            System.out.print(content[i] ? "1" : "0");
        }
        System.out.println();
    }

    public int sumBits(){
        int sum = 0;
        for (int i = 0; i < BLOCK_SIZE; i++){
            sum += this.content[i] ? 1 : 0;
        }
        return sum;
    }
}
