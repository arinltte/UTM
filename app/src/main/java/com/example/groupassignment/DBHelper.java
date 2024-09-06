/*
UCCD 3223 Mobile Applications Development
June 2024 Trimester

Chen Jin Shen	2202076
Chin Whye Ting	2200559
Ong Jing Yang	2200327
Tan Zong Ting	2302731
*/

package com.example.groupassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";
    public static final String USERS_TABLE = "users";
    public static final String TIMETABLE_TABLE = "user_timetable";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    // Create Database Using SQLite to store the username, password, course, etc
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE " + USERS_TABLE + " (username TEXT PRIMARY KEY, password TEXT)");
        MyDB.execSQL("CREATE TABLE " + TIMETABLE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, day TEXT, time TEXT, activity TEXT, remarks TEXT)");
        MyDB.execSQL("CREATE TABLE feedback_table (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, feedback TEXT)");
    }

    // To Recreate if Database not Existed
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TIMETABLE_TABLE);
        onCreate(MyDB);
    }

    // To Update Database
    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert(USERS_TABLE, null, contentValues);
        MyDB.close();
        return result != -1;
    }

    // To Check the Username during Sign In
    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        MyDB.close();
        return exists;
    }

    // To Check the Password during Sign In
    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE username = ? AND password = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        MyDB.close();
        return exists;
    }

    public Boolean insertTimetableData(String username, String day, String time, String activity, String remarks) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("day", day);
        contentValues.put("time", time);
        contentValues.put("activity", activity);
        contentValues.put("remarks", remarks);
        long result = MyDB.insert(TIMETABLE_TABLE, null, contentValues);
        MyDB.close();
        return result != -1;
    }

    public Cursor getUserTimetable(String username, String day) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        return MyDB.rawQuery("SELECT * FROM " + TIMETABLE_TABLE + " WHERE username = ? AND day = ?", new String[]{username, day});
    }

    public boolean deleteTimetableEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TIMETABLE_TABLE, "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
