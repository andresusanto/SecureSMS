package id.ac.itb.securesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewMessageActivity extends AppCompatActivity {

    private CheckBox encryptCheck, signatureCheck;
    private TextView msgLength;
    private EditText recepientText, messageText, keyText, privateKeyText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Compose message");
        setSupportActionBar(toolbar);

        encryptCheck = (CheckBox) findViewById(R.id.encryptCheckBox);
        signatureCheck = (CheckBox) findViewById(R.id.signatureCheckBox);
        msgLength = (TextView) findViewById(R.id.msgLength);
        recepientText = (EditText) findViewById(R.id.recepientText);
        messageText = (EditText) findViewById(R.id.messageText);
        messageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                msgLength.setText(String.valueOf(s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        keyText = (EditText) findViewById(R.id.keyText);
        privateKeyText = (EditText) findViewById(R.id.privateKeyText);
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageBody = messageText.getText().toString();
                String messageRecipient = recepientText.getText().toString();
                if(messageRecipient.length()==0) {
                    Toast.makeText(v.getContext(), "Fill the recipient number!", Toast.LENGTH_SHORT).show();
                }
                else if (messageBody.length()==0) {
                    Toast.makeText(v.getContext(), "The message is empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if((encryptCheck.isChecked()&&keyText.getText().toString().length()==0)||(signatureCheck.isChecked()&&privateKeyText.getText().toString().length()==0)) {
                        Toast.makeText(v.getContext(), "Fill the key field!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // PROSES ENKRIPSI DAN DIGITAL SIGNATURE
                    }
                    SmsManager sms = SmsManager.getDefault();
                    ArrayList<String> parts = sms.divideMessage(messageBody);
                    sms.sendMultipartTextMessage(messageRecipient, null, parts, null, null);
                    Toast.makeText(v.getContext(), "Message sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
