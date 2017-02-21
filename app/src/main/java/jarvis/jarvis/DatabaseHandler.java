package jarvis.jarvis;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "jarvis";
    // Database table names
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_Flags = "flagTable";
    private static final String TABLE_ACCOUNTS = "accountsTable";
    private static final String TABLE_STUDY = "studyTable";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_PH_EMAIL = "email_id";

    // Flags Table Columns names
    private static final String KEY_FLAG = "isFirstTime";

    // Accounts Table Columns names
    private static final String KEY_ID_ACC = "id";
    private static final String KEY_ACCOUNT = "accName";
    private static final String KEY_PASSWORD = "accPassword";
    private static final String KEY_USERNAME = "accUsername";

    // Study Table Columns names
    private static final String KEY_ID_STUDY = "id";
    private static final String KEY_SUBJECT = "subName";
    private static final String KEY_TOPIC = "topicName";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_PH_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_FLAGS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_Flags + "("
                + KEY_FLAG + " INTEGER PRIMARY KEY" + ")";
        db.execSQL(CREATE_FLAGS_TABLE);

        String CREATE_STUDY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_STUDY + "("
                + KEY_ID_STUDY + " INTEGER PRIMARY KEY," + KEY_SUBJECT + " TEXT,"
                + KEY_TOPIC + " TEXT" + ")";
        db.execSQL(CREATE_STUDY_TABLE);

        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNTS + "("
                + KEY_ID_ACC + " INTEGER PRIMARY KEY, " + KEY_ACCOUNT + " TEXT,"
                + KEY_PASSWORD + " TEXT, " + KEY_USERNAME + " TEXT" + ")";
        db.execSQL(CREATE_ACCOUNTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
        values.put(KEY_PH_EMAIL, contact.getEmailId());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //db.close(); // Closing database connection
    }

    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_PH_EMAIL }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return contact
        return contact;
    }



    void addFlag(int num) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FLAG, num); // Contact Name
        // Inserting Row
        db.insert(TABLE_Flags, null, values);
        //db.close(); // Closing database connection
    }

    int getFlagsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_Flags;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting All Contacts
    List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contact.setEmailId(cursor.getString(3));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    /*// Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        //db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    */
}