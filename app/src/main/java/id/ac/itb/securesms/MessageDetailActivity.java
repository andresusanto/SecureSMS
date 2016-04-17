package id.ac.itb.securesms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MessageDetailActivity extends AppCompatActivity {

    public static final String SENDER = "sender";
    public static final String BODY = "body";

    private TextView sender, message;
    private Button verify, decrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        Intent intent = getIntent();
        sender = (TextView) findViewById(R.id.senderTextView);
        sender.setText(intent.getStringExtra(SENDER));
        message = (TextView) findViewById(R.id.messageTextView);
        message.setText(intent.getStringExtra(BODY));
        verify = (Button) findViewById(R.id.verifyButton);
        decrypt = (Button) findViewById(R.id.decryptButton);
    }
}
