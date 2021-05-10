package davoodi.mahdi.fifa.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import davoodi.mahdi.fifa.components.Season;

public class SeasonsData extends SQLiteOpenHelper {
    private static final String DB_NAME = "seasons-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_SEASONS = "seasons";
    private static final String CMD_CREATE = "CREATE TABLE IF NOT EXISTS '" + TABLE_SEASONS + "' " + "( '"
            + Season.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Season.KEY_MT + "' INTEGER, '"
            + Season.KEY_TM + "' INTEGER, '"
            + Season.KEY_CHAMPIONS + "' INTEGER, '"
            + Season.KEY_EUROPE + "' INTEGER, '"
            + Season.KEY_GOLDEN + "' INTEGER, '"
            + Season.KEY_MATCHES + "' INTEGER"
            + ")";

    public SeasonsData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CMD_CREATE);
        Log.i("database", "Table '" + TABLE_SEASONS + "' created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // We should get backups from old data.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SEASONS);
        Log.i("database", "Table '" + TABLE_SEASONS + "' dropped!");
        onCreate(database);
        // We should restore database.
    }

    public void insertSeason(Season season) {
        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        long insertID = database.insert(TABLE_SEASONS, null, season.getContentValues());

        if (insertID == -1)
            Log.i("database", "Season data insertion failed. (Season: " + season.toString() + " ) ");
        else
            Log.i("database", "Season data inserted with id: " + insertID);
        if (database.isOpen()) database.close();
        Log.i("database", "Seasons database closed");
    }
}
