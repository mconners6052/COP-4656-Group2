package com.fitness.sm.smartmuscle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDB extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MDAG2_Database";
    // Contacts table name
    private static final String TABLE_NAME = "Exercises";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "exercise";
    private static final String KEY_URL = "url";
    private static final String KEY_REPS = "reps";
    private static final String KEY_SETS = "Sets";
    private static final String KEY_FIN = "finished";
    private static final String KEY_STEPS = "steps";

    private List<String> temp;
    public ExerciseDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME +
                "("+
                    KEY_ID + " INTEGER PRIMARY KEY," +
                    KEY_NAME + " TEXT,"+
                    KEY_URL + " TEXT," +
                    KEY_REPS + " TEXT" +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Creating tables again
        onCreate(db);
    }

    // Adding new Exercise
    public void addExercise(Exercise shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, shop.getName()); // Exercise Name
        values.put(KEY_URL, shop.getUrl()); // Exercise URL
        values.put(KEY_REPS, shop.getReps()); // Exercise Reps
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting one Exercise Using an ID
    public Exercise getExercise(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_NAME, KEY_URL, KEY_REPS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Exercise contact = new Exercise(
                cursor.getString(1),
                temp ,
                cursor.getString(2),
                0,
                Integer.parseInt(cursor.getString(3))
        );
        // return shop
        return contact;
    }

    // Updating an Exercise
    public int updateShop(Exercise shop,int reps, int sets) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, shop.getName());
        values.put(KEY_URL, shop.getUrl());
        values.put(KEY_REPS, reps);
        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(shop.getName())});
    }
}
