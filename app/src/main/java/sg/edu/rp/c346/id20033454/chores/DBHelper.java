package sg.edu.rp.c346.id20033454.chores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chores.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CHORES = "Chores";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DETAILS = "details";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_IMPORTANCE = "importance";

    public DBHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CHORES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DETAILS + " TEXT, "
                + COLUMN_DAY + " TEXT, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_IMPORTANCE + " INTEGER )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHORES);
        onCreate(db);
    }

    public long insertChore(String name,String details, String day, String time, int importance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DETAILS, details);
        values.put(COLUMN_DAY, day);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_IMPORTANCE, importance);

        long result = db.insert(TABLE_CHORES, null, values);

        db.close();

        return result;

    }

    public ArrayList<Chores> getAllChores() {
        ArrayList<Chores> choreList = new ArrayList<Chores>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_DETAILS + ","
                + COLUMN_DAY + "," + COLUMN_TIME + ","
                + COLUMN_IMPORTANCE + " FROM " + TABLE_CHORES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String details = cursor.getString(2);
                String day = cursor.getString(3);
                String time = cursor.getString(4);
                int importance = cursor.getInt(5);

                Chores newChore = new Chores(id, name, details, day, time, importance);
                choreList.add(newChore);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return choreList;
    }

    public ArrayList<Chores> getAllChoresByDay(String filter) {
        ArrayList<Chores> choreList = new ArrayList<Chores>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DETAILS, COLUMN_DAY, COLUMN_TIME, COLUMN_IMPORTANCE};
        String condition = COLUMN_DAY + ">= ?";
        String[] args = {String.valueOf(filter)};

        Cursor cursor;
        cursor = db.query(TABLE_CHORES, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String details = cursor.getString(2);
                String day = cursor.getString(3);
                String time = cursor.getString(4);
                int importance = cursor.getInt(5);

                Chores newChore = new Chores(id, name, details, day, time, importance);
                choreList.add(newChore);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return choreList;

    }
    public int updateChore(Chores data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DETAILS, data.getDetails());
        values.put(COLUMN_DAY, data.getDay());
        values.put(COLUMN_TIME, data.getTime());
        values.put(COLUMN_IMPORTANCE, data.getImportance());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_CHORES, values, condition, args);
        db.close();
        return result;
    }


    public int deleteChore(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_CHORES, condition, args);
        db.close();
        return result;
    }
}
