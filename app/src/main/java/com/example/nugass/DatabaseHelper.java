package com.example.nugass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "NugasDB.db";

    public static final String TABLE_TASK = "tasks";
    public static final String TASK_ID = "taskId";
    public static final String TASK_NAME = "taskName";
    public static final String TASK_REWARD = "taskReward";
    public static final String TASK_DATE = "taskDate";

    public static final String TABLE_USER = "user";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String USER_COINS = "coins";
    public static final String USER_CHARACTER = "characterId";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TASK_NAME + " TEXT," +
                TASK_REWARD + " INTEGER," +
                TASK_DATE + " DATETIME" + ");";
        db.execSQL(CREATE_TASK_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" +
                USER_ID + " INTEGER PRIMARY KEY," +
                USER_NAME + " TEXT," +
                USER_COINS + " INTEGER," +
                USER_CHARACTER + " TEXT" + ");";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void addHistoryData(String taskName, int taskReward) {
        SQLiteDatabase db = getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:MM");
        String strDate = sdf.format(new Date());

        ContentValues values = new ContentValues();
        values.put(TASK_NAME, taskName);
        values.put(TASK_REWARD, taskReward);
        values.put(TASK_DATE, strDate);

        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    public void addUserData(String userName, int coins, String characterId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_COINS, coins);
        values.put(USER_CHARACTER, characterId);

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public Cursor readHistoryData() {
        String query = "SELECT * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public Cursor readUserData() {
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + USER_ID +"=1;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void updateCoins(int coins) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE user SET coins=" + coins + " where userId=1;";
        db.execSQL(query);
    }

    public void updateCharacter(String characterID) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE user SET characterId='" + characterID + "' where userId=1;";
        db.execSQL(query);
    }
}
