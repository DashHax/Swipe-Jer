package io.pakcik.assignment.group.swipejer;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amardeep on 10/26/2017.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "swipejer";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";

    //COLUMN email
    public static final String KEY_EMAIL = "email";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_GENDER = "gender";

    public static final String KEY_PHONE_NUMBER = "phone_number";

    public static final String KEY_LOCATION = "location";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT, "
            + KEY_GENDER + " TEXT,"
            + KEY_PHONE_NUMBER + " TEXT,"
            + KEY_LOCATION + " TEXT"
            + " ) " ;


    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);

        sqLiteDatabase.execSQL("INSERT INTO "+ TABLE_USERS  + " VALUES(null, 'aminhakim' , 'a@a.com' ,  '1234qwer' , '' , '' , '' );");
        sqLiteDatabase.execSQL("INSERT INTO "+ TABLE_USERS  + " VALUES(null, 'senoi' , 'senoi@topglove.com' ,  '1234qwer' , '' , '' , '' );");
        sqLiteDatabase.execSQL("INSERT INTO "+ TABLE_USERS  + " VALUES(null, 'odell' , 'odell@ivis.com' ,  '1234qwer' , '' , '' , '' );");
        sqLiteDatabase.execSQL("INSERT INTO "+ TABLE_USERS  + " VALUES(null, 'aqiff' , 'aqiff@tnb.com' ,  '1234qwer' , '' , '' , '' );");
        sqLiteDatabase.execSQL("INSERT INTO "+ TABLE_USERS  + " VALUES(null, 'fawzcopter' , 'fawz@fuzzyzadeh.com' ,  '1234qwer' , '' , '' , '' );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in  @values
        values.put(KEY_EMAIL, user.email);

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);

        //Put gender in  @values
        values.put(KEY_PASSWORD, user.gender);

        //Put location in  @values
        values.put(KEY_PASSWORD, user.location);

        //Put phone number in  @values
        values.put(KEY_PASSWORD, user.phone_number);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_GENDER, KEY_PHONE_NUMBER, KEY_LOCATION},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                    );

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }


    //create function to get user id
}

