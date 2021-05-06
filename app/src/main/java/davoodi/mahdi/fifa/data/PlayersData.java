package davoodi.mahdi.fifa.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import davoodi.mahdi.fifa.components.Player;

public class PlayersData extends SQLiteOpenHelper {
    private static final String DB_NAME = "players-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_PLAYERS = "players";
    private static final String CMD_CREATE = "CREATE TABLE IF NOT EXISTS '" + TABLE_PLAYERS + "' " + "( '"
            + Player.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Player.KEY_NAME + "' TEXT, '"
            + Player.KEY_AGE + "' INTEGER, '"
            + Player.KEY_OVERALL + "' INTEGER, '"
            + Player.KEY_CLUB + "' INTEGER"
            + ")";

    public PlayersData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CMD_CREATE);
        Log.i("database", "Table '" + TABLE_PLAYERS + "' created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // We should get backups from old data.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        Log.i("database", "Table '" + TABLE_PLAYERS + "' dropped!");
        onCreate(database);
        // We should restore database.
    }
}
