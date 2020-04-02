package com.adam.pom.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabaseHelper extends SQLiteOpenHelper{

    private static final String TAG = UserDatabaseHelper.class.getSimpleName();

    private static final String TABLE_NAME = "user_details";
    private static final String COL0 = "id";
    private static final String COL1 = "first_name";
    private static final String COL2 = "surname";
    private static final String COL3 = "interested_in";

    private static int version = 1;

    public UserDatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COL0 + " VARCHAR(50) PRIMARY KEY, " +
                COL1 + " VARCHAR(10), " + COL2 + " VARCHAR(15), " + COL3 + " VARCHAR(6))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean addData(int id, String first_name, String surname, String interested_in){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL0, id);
        contentValues.put(COL1, first_name);
        contentValues.put(COL2, surname);
        contentValues.put(COL3, interested_in);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void setVersion(int version){
        this.version = version;
    }

    public int getVersion(){
        return this.version;
    }

    public void login(int id, String first_name, String surname, String interested_in){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 1, 1);
        boolean success = addData(id, first_name, surname, interested_in);

        if(success){
            Log.d(TAG, "Successfully inserted");
        }else{
            Log.e(TAG, "Error occurred");
        }
    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, this.getVersion(), this.getVersion());
    }
}
