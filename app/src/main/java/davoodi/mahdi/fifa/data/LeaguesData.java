package davoodi.mahdi.fifa.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import davoodi.mahdi.fifa.components.League;


public class LeaguesData extends SQLiteOpenHelper {
    private static final String DB_NAME = "leagues-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_LEAGUES = "leagues";
    private static final String CMD_CREATE = "CREATE TABLE IF NOT EXISTS '" + TABLE_LEAGUES + "' " + "( '"
            + League.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + League.KEY_NAME + "' TEXT, '"
            + League.KEY_NUMBER + "' INTEGER"
            + ")";


    public LeaguesData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CMD_CREATE);
        Log.i("database", "Table '" + TABLE_LEAGUES + "' created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // We should get backups from old data.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAGUES);
        Log.i("database", "Table '" + TABLE_LEAGUES + "' dropped!");
        onCreate(database);
        // We should restore database.
    }

    public void insertLeague(League league) {
        ContentValues values = new ContentValues();
        values.put(League.KEY_ID, league.getLeagueID());
        values.put(League.KEY_NAME, league.getLeagueName());
        values.put(League.KEY_NUMBER, league.getLeagueNumber());

        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        long insertID = database.insert(TABLE_LEAGUES, null, values);

        if (insertID == -1)
            Log.i("database", "League data insertion failed. (League: " + league.toString() + " ) ");
        else
            Log.i("database", "League data inserted with id: " + insertID);
        if (database.isOpen()) database.close();
        Log.i("database", "Leagues database closed");
    }
}
