package id.ac.itb.securesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Rakhmatullah Yoga S on 12/04/2016.
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            final SmsMessage[] messages = new SmsMessage[sms.length];
            StringBuffer content = new StringBuffer();
            String smsBody, smsSender = "";
            for (int i = 0; i < sms.length; ++i) {
                messages[i] = SmsMessage.createFromPdu((byte[]) sms[i]);
                content.append(messages[i].getMessageBody());
                if(i==0)
                    smsSender = messages[i].getOriginatingAddress();
            }
            smsBody = content.toString();
            Toast.makeText(context, "New message received!", Toast.LENGTH_SHORT).show();

            //this will update the UI with message
            if(messages.length!=0) {
                MainActivity inst = MainActivity.instance();
                inst.updateList(smsSender, smsBody);
            }
        }
    }
}
