package id.ac.itb.securesms.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigInteger;

import id.ac.itb.securesms.R;
import id.ac.itb.securesms.engine.ECC;
import id.ac.itb.securesms.engine.TreeCipher;
import id.ac.itb.securesms.obj.Coordinate;
import id.ac.itb.securesms.obj.TreeCipherBlock;

public class MessageDetailActivity extends AppCompatActivity {

    public static final String SENDER = "sender";
    public static final String BODY = "body";
    public static final String IS_OUTBOX = "is_outbox";

    private TextView sender, message, smsAddress;
    private Button verify, decrypt;
    private boolean verified;

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
        String content = message.getText().toString();
        final String[] splitted = content.split(Sms.DELIMITER);
        if(splitted.length==1)
            verify.setEnabled(false);
        else
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
                            String publicKeyStr = input.getText().toString();
                            String[] coorPoint = publicKeyStr.split(",");
                            Coordinate publicKey = new Coordinate(new BigInteger(coorPoint[0]),new BigInteger(coorPoint[1]));
                            ECC ecc = new ECC();
                            verified = ecc.verify(Base64.decode(splitted[0],Base64.DEFAULT),Base64.decode(splitted[1],Base64.DEFAULT),publicKey);
                            if(verified)
                                Toast.makeText(getApplicationContext(), "Verification succeeded", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(), "Verification failed!", Toast.LENGTH_SHORT).show();
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
                        try {
                            // MULAI DEKRIPSI PESAN
                            byte[] bkey = Base64.decode(input.getText().toString(),Base64.DEFAULT);
                            byte[] cipher = Base64.decode(message.getText().toString(), Base64.DEFAULT);
                            TreeCipherBlock key = new TreeCipherBlock(bkey);
                            TreeCipher cip = new TreeCipher(key);
                            TreeCipherBlock dataBlocks [] = TreeCipherBlock.build(cipher);
                            cip.decrypt(dataBlocks);
                            byte[] decrypt = TreeCipherBlock.toBytes(dataBlocks);
                            message.setText(new String(decrypt));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
