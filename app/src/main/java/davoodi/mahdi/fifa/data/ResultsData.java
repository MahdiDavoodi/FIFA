package davoodi.mahdi.fifa.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import davoodi.mahdi.fifa.components.Match;


public class ResultsData extends SQLiteOpenHelper {
    private static final String DB_NAME = "results-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_RESULTS = "results";
    private static final String CMD_CREATE = "CREATE TABLE IF NOT EXISTS '" + TABLE_RESULTS + "' " + "( '"
            + Match.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Match.KEY_SEASON + "' INTEGER, '"
            + Match.KEY_LEAGUE + "' INTEGER, '"
            + Match.KEY_HOME + "' INTEGER, '"
            + Match.KEY_AWAY + "' INTEGER, '"
            + Match.KEY_HOME_GOALS + "' INTEGER, '"
            + Match.KEY_AWAY_GOALS + "' INTEGER, '"
            + Match.KEY_MATCH_PLAYED + "' INTEGER"
            + ")";

    public ResultsData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CMD_CREATE);
        Log.i("database", "Table '" + TABLE_RESULTS + "' created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // We should get backups from old data.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        Log.i("database", "Table '" + TABLE_RESULTS + "' dropped!");
        onCreate(database);
        // We should restore database.
    }
}
