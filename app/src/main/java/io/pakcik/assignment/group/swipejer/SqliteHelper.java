package io.pakcik.assignment.group.swipejer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(int userID, String name, String price, String description, String category, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO PRODUCT VALUES (NULL,?, ?, ?, ?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, userID);
        statement.bindString(2, name);
        statement.bindString(3, price);
        statement.bindString(4, description);
        statement.bindString(5, category);
        statement.bindBlob(6, image);

        statement.executeInsert();
    }


    public void updateData(int userID, String name, String price, String description,String category,byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE PRODUCT SET userID = ?, name = ?, price = ?, description = ?, category = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindDouble(1, userID);
        statement.bindString(2, name);
        statement.bindString(3, price);
        statement.bindString(4, description);
        statement.bindString(5, category);
        statement.bindBlob(6, image);

        statement.execute();
        database.close();
    }

    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM PRODUCT WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}