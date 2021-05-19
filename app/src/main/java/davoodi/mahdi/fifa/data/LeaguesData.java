package davoodi.mahdi.fifa.data;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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

    public void updateLeague(League league) {
        SQLiteDatabase database = getWritableDatabase();
        int count = database.update(TABLE_LEAGUES, league.getContentValues(), League.KEY_ID + " = " + league.getLeagueID(), null);
        if (count != 1) Log.e("LeaguesData", "Error in update method");
        if (database.isOpen()) database.close();
    }

    public void insertLeague(League league) {

        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        long insertID = database.insert(TABLE_LEAGUES, null, league.getContentValues());

        if (insertID == -1)
            Log.i("database", "League data insertion failed. (League: " + league.toString() + " ) ");
        else
            Log.i("database", "League data inserted with id: " + insertID);
        if (database.isOpen()) database.close();
        Log.i("database", "Leagues database closed");
    }

    public ArrayList<League> getAllLeagues() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<League> leagues = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_LEAGUES + "'", null);
        if (cursor.moveToFirst()) {
            do {

                League league = new League(
                        cursor.getInt(cursor.getColumnIndex(League.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(League.KEY_NAME)),
                        cursor.getInt(cursor.getColumnIndex(League.KEY_NUMBER)));
                leagues.add(league);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return leagues;
    }

    public League getLeagueFromID(int leagueID) {
        SQLiteDatabase database = getReadableDatabase();
        League league = null;
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_LEAGUES + "' WHERE " + League.KEY_ID + " = " + leagueID, null);
        if (cursor.moveToFirst()) {
            league = new League(
                    cursor.getInt(cursor.getColumnIndex(League.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(League.KEY_NAME)),
                    cursor.getInt(cursor.getColumnIndex(League.KEY_NUMBER)));
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return league;
    }
}
