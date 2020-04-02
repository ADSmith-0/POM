package com.adam.pom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "Users";
    private static final String COL0 = "ID";
    private static final String COL1 = "First_Name";
    private static final String COL2 = "Surname";
    private static final String COL3 = "PhoneNo";
    private static final String COL4 = "Sex";
    private static final String COL5 = "Email";
    private static final String COL6 = "Pass_word";
    //private static final String COL7 = "DOB";

    private static int version = 1;


    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 + " TINYTEXT, " + COL2 + " TINYTEXT, " + COL3 + " VARCHAR(10), " + COL4 + " VARCHAR(6), " + COL5 + " VARCHAR(50), " + COL6 + " VARCHAR(15));";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String FName, String SName, int PhoneNo, String Sex, String Email, String Password){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "INSERT INTO " + TABLE_NAME + " (" + COL1 + ", " + COL2 + ", " + COL3 + ", " + COL4 + ", " + COL5 + ", " + COL6 + ") VALUES "
        //        + "(" + FName + ", " + SName + ", " + Email + ", " + PhoneNo + ", " + Sex + ", " + Password + ");";
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, FName);
        contentValues.put(COL2, SName);
        contentValues.put(COL3, PhoneNo);
        contentValues.put(COL4, Sex);
        contentValues.put(COL5, Email);
        contentValues.put(COL6, Password);

        Log.d(TAG, "addData: Adding " + FName + ", " + SName + ", " + Email + ", " + PhoneNo + ", " + Sex + ", " + Password + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //Check if data is inserted correctly: -1 is error, anything else is good.
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void setVersion(int Version){
        this.version = Version;
    }

    public int getVersion(){
        return this.version;
    }
}
