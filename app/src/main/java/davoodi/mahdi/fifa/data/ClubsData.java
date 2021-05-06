package davoodi.mahdi.fifa.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import davoodi.mahdi.fifa.components.Club;

public class ClubsData extends SQLiteOpenHelper {

    private static final String DB_NAME = "clubs-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_CLUBS = "clubs";
    private static final String CMD_CREATE = "CREATE TABLE IF NOT EXISTS '" + TABLE_CLUBS + "' " + "( '"
            + Club.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Club.KEY_NAME + "' TEXT, '"
            + Club.KEY_WEALTH + "' INTEGER, '"
            + Club.KEY_MT + "' INTEGER, '"
            + Club.KEY_TM + "' INTEGER, '"
            + Club.KEY_CHAMPIONS + "' INTEGER, '"
            + Club.KEY_EUROPE + "' INTEGER, '"
            + Club.KEY_GOLDEN + "' INTEGER, '"
            + Club.KEY_CLASS + "' TEXT, '"
            + Club.KEY_OWNER + "' INTEGER"
            + ")";


    public ClubsData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CMD_CREATE);
        Log.i("database", "Table '" + TABLE_CLUBS + "' created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // We should get backups from old data.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CLUBS);
        Log.i("database", "Table '" + TABLE_CLUBS + "' dropped!");
        onCreate(database);
        // We should restore database.
    }

    public void insertClub(Club club) {
        ContentValues values = new ContentValues();
        values.put(Club.KEY_ID, club.getClubID());
        values.put(Club.KEY_NAME, club.getClubName());
        values.put(Club.KEY_WEALTH, club.getClubWealth());
        values.put(Club.KEY_MT, club.getClubMT());
        values.put(Club.KEY_TM, club.getClubTM());
        values.put(Club.KEY_CHAMPIONS, club.getClubChampions());
        values.put(Club.KEY_EUROPE, club.getClubEurope());
        values.put(Club.KEY_GOLDEN, club.getClubGolden());
        values.put(Club.KEY_CLASS, club.getClubClass());

        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        long insertID = database.insert(TABLE_CLUBS, null, values);

        if (insertID == -1)
            Log.i("database", "Club data insertion failed. (Club: " + club.toString() + " ) ");
        else
            Log.i("database", "Club data inserted with id: " + insertID);
        if (database.isOpen()) database.close();
        Log.i("database", "Clubs database closed");
    }
}