package com.example.a2048;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "mydatabase.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_SCORE_2048 = "Score2048";
    public static final String COLUMN_ID_2048 = "_id";
    public static final String COLUMN_NAME_2048 = "name";
    public static final String COLUMN_SCORE_2048 = "score";

    public static final String TABLE_SCORE_LIGHTS_OUT = "ScoreLightsOut";
    public static final String COLUMN_ID_LIGHTS_OUT = "_id";
    public static final String COLUMN_NAME_LIGHTS_OUT = "name";
    public static final String COLUMN_SCORE_LIGHTS_OUT = "score";

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable2048 = "CREATE TABLE " + TABLE_SCORE_2048 + " (" +
                COLUMN_ID_2048 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_2048 + " TEXT, " +
                COLUMN_SCORE_2048 + " INTEGER)";
        db.execSQL(createTable2048);

        String createTableLightsOut = "CREATE TABLE " + TABLE_SCORE_LIGHTS_OUT + " (" +
                COLUMN_ID_LIGHTS_OUT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_LIGHTS_OUT + " TEXT, " +
                COLUMN_SCORE_LIGHTS_OUT + " INTEGER)";
        db.execSQL(createTableLightsOut);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required for this example
    }

    public long insert(String table, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(table, null, values);
    }



    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(table, values, whereClause, whereArgs);
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table, whereClause, whereArgs);
    }

    public long count(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.compileStatement("SELECT COUNT(*) FROM " + table).simpleQueryForLong();
    }
    public List<Scores> getAllScores(String table) {
        List<Scores> scoresList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, new String[]{COLUMN_ID_2048, COLUMN_NAME_2048, COLUMN_SCORE_2048},
                null, null, null, null, COLUMN_SCORE_2048 + " DESC");

        while (cursor.moveToNext()) {
            Scores score = new Scores();
            score.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_2048)));
            score.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_2048)));
            score.setScore(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE_2048))));
            scoresList.add(score);
        }
        cursor.close();
        return scoresList;
    }


    @SuppressLint("Range")
    public Scores query(int position, String table) {
        Scores score = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, new String[]{COLUMN_ID_2048, COLUMN_NAME_2048, COLUMN_SCORE_2048},
                null, null, null, null, COLUMN_SCORE_2048 + " DESC");
        if (cursor.moveToPosition(position)) {
            score = new Scores();
            score.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID_2048)));
            score.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_2048)));
            score.setScore(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE_2048))));
        }
        cursor.close();
        return score;
    }
}
