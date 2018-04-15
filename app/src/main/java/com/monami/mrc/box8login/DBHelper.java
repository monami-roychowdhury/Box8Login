package com.monami.mrc.box8login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Box8Credentials";
    private static final int DATABASE_VERSION = 1;
    public String table_name = "users";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + table_name +
                "(USERNAME text, PHONE text, EMAIL text primary key, PASSWORD text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_name);

    }
    public void insertData(String name, String phone, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", name);
        contentValues.put("PHONE", phone);
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWORD", password);
        db.insert(table_name, null, contentValues);
    }

    public Cursor getData(HashMap<String, String> details){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE (\"EMAIL\"=? OR \"PHONE\"=?) AND \"PASSWORD\"=?", table_name), new String[]{details.get("email"), details.get("phone"), details.get("password")});
        return cursor;
    }

    public boolean emailPhoneChecker(HashMap<String, String> details){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE (\"EMAIL\"=? OR \"PHONE\"=?)", table_name), new String[]{details.get("email"), details.get("phone")});
        return cursor.getCount()>0? true: false;
    }

}
