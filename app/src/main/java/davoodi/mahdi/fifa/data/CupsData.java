package davoodi.mahdi.fifa.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import davoodi.mahdi.fifa.components.Cup;


public class CupsData extends SQLiteOpenHelper {
    private static final String DB_NAME = "cups-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_CUPS = "cups";
    private static final String CMD_CREATE = "CREATE TABLE IF NOT EXISTS '" + TABLE_CUPS + "' " + "( '"
            + Cup.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Cup.KEY_SEASON + "' INTEGER, '"
            + Cup.KEY_LEAGUE + "' INTEGER, '"
            + Cup.KEY_WINNER + "' INTEGER"
            + ")";

    public CupsData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CMD_CREATE);
        Log.i("database", "Table '" + TABLE_CUPS + "' created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // We should get backups from old data.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CUPS);
        Log.i("database", "Table '" + TABLE_CUPS + "' dropped!");
        onCreate(database);
        // We should restore database.
    }

    public ArrayList<Cup> getAllCups() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Cup> cups = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_CUPS
                + "' ORDER BY " + Cup.KEY_ID + " DESC ", null);
        if (cursor.moveToFirst()) {
            do {

                Cup cup = new Cup(cursor.getInt(cursor.getColumnIndex(Cup.KEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(Cup.KEY_SEASON)),
                        cursor.getInt(cursor.getColumnIndex(Cup.KEY_LEAGUE)),
                        cursor.getInt(cursor.getColumnIndex(Cup.KEY_WINNER)));
                cups.add(cup);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return cups;
    }

    public void insertCup(Cup cup) {

        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        // Let SQLite set id.
        ContentValues values = cup.getContentValues();
        values.remove(Cup.KEY_ID);
        //
        long insertID = database.insert(TABLE_CUPS, null, values);

        if (insertID == -1)
            Log.i("database", "Cup data insertion failed.");
        else
            Log.i("database", "Cup data inserted with id: " + insertID);
        // Better to not close it.
    }
}
