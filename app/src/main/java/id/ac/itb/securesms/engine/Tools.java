package id.ac.itb.securesms.engine;

import android.util.Log;

/**
 * Created by Andre on 4/21/2016.
 */
public class Tools {

    public static void printBytes(byte data[], String caption){
        StringBuffer sb = new StringBuffer();

        for (byte b : data){
            sb.append(String.format("%02X ", b));
        }

        Log.d(caption, sb.toString());
    }
}
