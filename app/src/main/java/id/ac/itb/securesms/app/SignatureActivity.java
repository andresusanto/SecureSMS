package id.ac.itb.securesms.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        ecc = new ECC();
        publicKey = ecc.generatePublic();
        publicKeyText = (TextView) findViewById(R.id.publicKeyView);
        publicKeyText.setText(publicKey.X.toString() + "," + publicKey.Y.toString());
        privateKeyText = (TextView) findViewById(R.id.privateKeyView);
        privateKeyText.setText(ecc.getPrivateKey().toString());
        generateKeyButton = (Button) findViewById(R.id.generateKeyButton);
        generateKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PEMBANGKITAN PUBLIC KEY + PRIVATE KEY
                publicKey = ecc.generatePublic();
                publicKeyText.setText(publicKey.X.toString() + "," + publicKey.Y.toString());
            }
        });
    }

}
