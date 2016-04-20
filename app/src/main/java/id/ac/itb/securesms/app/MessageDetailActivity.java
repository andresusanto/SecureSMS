package id.ac.itb.securesms.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import id.ac.itb.securesms.R;

public class MessageDetailActivity extends AppCompatActivity {

    public static final String SENDER = "sender";
    public static final String BODY = "body";
    public static final String IS_OUTBOX = "is_outbox";

    private TextView sender, message, smsAddress;
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
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Insert public key");

                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // MULAI VERIFIKASI DIGITAL SIGNATURE
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        decrypt = (Button) findViewById(R.id.decryptButton);
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Insert encryption key");

                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // MULAI DEKRIPSI PESAN
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        smsAddress = (TextView) findViewById(R.id.addressTextView);
        if(intent.getBooleanExtra(IS_OUTBOX,false)) {
            smsAddress.setText("To :");
        }
        getSupportActionBar().setTitle("Message - "+intent.getStringExtra(SENDER));
    }
}
