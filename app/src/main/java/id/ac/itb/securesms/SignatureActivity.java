package id.ac.itb.securesms;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignatureActivity extends AppCompatActivity {
    private TextView publicKey, privateKey;
    private Button generateKeyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);

        publicKey = (TextView) findViewById(R.id.publicKeyView);
        privateKey = (TextView) findViewById(R.id.privateKeyView);
        generateKeyButton = (Button) findViewById(R.id.generateKeyButton);
        generateKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PEMBANGKITAN PUBLIC KEY + PRIVATE KEY
            }
        });
    }

}
