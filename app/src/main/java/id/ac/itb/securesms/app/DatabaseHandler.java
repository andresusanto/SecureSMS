package id.ac.itb.securesms.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakhmatullah Yoga S on 24/04/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "outboxManager";

    // Message table name
    private static final String TABLE_MESSAGES = "smsOutbox";

    // Message Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MESSAGE + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new message
    void addMessage(Sms sms) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, sms.getBody()); // Contact Name
        values.put(KEY_PH_NO, sms.getAddress()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_MESSAGES, null, values);
//        db.close(); // Closing database connection
    }

    // Getting All Messages
    public List<Sms> getAllMessages() {
        List<Sms> messageList = new ArrayList<Sms>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Sms sms = new Sms();
                sms.setBody(cursor.getString(1));
                sms.setAddress(cursor.getString(2));
                // Adding contact to list
                messageList.add(sms);
            } while (cursor.moveToNext());
        }

//        db.close(); // Closing database connection

        // return message list
        return messageList;
    }

    // Getting messages count
    public int getMessagesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MESSAGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int nbMessage = cursor.getCount();
        cursor.close();

        // return count
        return nbMessage;
    }
}
