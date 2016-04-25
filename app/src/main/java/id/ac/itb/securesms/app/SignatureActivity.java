package id.ac.itb.securesms.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Random;

import id.ac.itb.securesms.R;
import id.ac.itb.securesms.engine.ECC;
import id.ac.itb.securesms.obj.Coordinate;

public class SignatureActivity extends AppCompatActivity {
    private TextView publicKeyText, privateKeyText;
    private Button generateKeyButton;
    private Coordinate publicKey;
    private ECC ecc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);

        SharedPreferences sp = getSharedPreferences("ECC_Key", Activity.MODE_PRIVATE);
        ecc = new ECC();
        publicKey = ecc.generatePublic();
        String publicStr = sp.getString("public",publicKey.X.toString() + "," + publicKey.Y.toString());
        String privateStr = sp.getString("private",ecc.getPrivateKey().toString());
        publicKeyText = (TextView) findViewById(R.id.publicKeyView);
        publicKeyText.setText(publicStr);
        privateKeyText = (TextView) findViewById(R.id.privateKeyView);
        privateKeyText.setText(privateStr);
        generateKeyButton = (Button) findViewById(R.id.generateKeyButton);
        generateKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PEMBANGKITAN PUBLIC KEY + PRIVATE KEY
                StringBuilder sb = new StringBuilder();
                Random random = new Random();
                BigInteger newPrivate;

                sb.append(random.nextInt(999999));
                sb.append(random.nextInt(999999));
                sb.append(random.nextInt(999999));
                sb.append(random.nextInt(999999));
                sb.append(random.nextInt(999999));
                sb.append(random.nextInt(9999));
                newPrivate = new BigInteger(sb.toString());
                ecc.setPrivateKey(newPrivate);
                privateKeyText.setText(newPrivate.toString());
                publicKey = ecc.generatePublic();
                publicKeyText.setText(publicKey.X.toString() + "," + publicKey.Y.toString());
                Log.d("Public:", publicKeyText.getText().toString());
                Log.d("Private:", privateKeyText.getText().toString());
                SharedPreferences sp = getSharedPreferences("ECC_Key", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("public", publicKeyText.getText().toString());
                editor.putString("private", privateKeyText.getText().toString());
                editor.commit();
            }
        });
    }

}
