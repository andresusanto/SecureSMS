package id.ac.itb.securesms.app;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.ac.itb.securesms.R;
import id.ac.itb.securesms.spec.ECCSpec;
import id.ac.itb.securesms.spec.TreeCipherSpec;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String INBOX = "inbox";
    private final String OUTBOX = "sent";
    private static MainActivity inst;
    private List<Sms> smsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SmsAdapter mAdapter;

    public void refreshSmsList(String option) {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsListCursor = contentResolver.query(Uri.parse("content://sms/"+option), null, null, null, null);
        int indexBody = smsListCursor.getColumnIndex("body");
        int indexAddress = smsListCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsListCursor.moveToFirst()) return;
        smsList.clear();
        mAdapter.notifyDataSetChanged();
        do {
            Sms sms = new Sms(smsListCursor.getString(indexAddress), smsListCursor.getString(indexBody), null);
            smsList.add(sms);
        } while (smsListCursor.moveToNext());
        mAdapter.notifyDataSetChanged();
    }

    public void updateList(final String smsSender, final String smsBody) {
        Sms newSms = new Sms(smsSender, smsBody, null);
        smsList.add(0,newSms);
        mAdapter.notifyDataSetChanged();
    }

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SecureSMS - Inbox");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, NewMessageActivity.class);
                context.startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new SmsAdapter(smsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        refreshSmsList(INBOX);

        // sampel ecc
        ECCSpec.testDSA();

        // sampel tree cipher block
        try {
            TreeCipherSpec.testTreeCipher();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SignatureActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
            refreshSmsList(INBOX);
            mAdapter.isOutbox = false;
            getSupportActionBar().setTitle("SecureSMS - Inbox");
        } else if (id == R.id.nav_outbox) {
            refreshSmsList(OUTBOX);
            mAdapter.isOutbox = true;
            getSupportActionBar().setTitle("SecureSMS - Outbox");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
