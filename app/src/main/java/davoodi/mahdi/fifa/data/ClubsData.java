package davoodi.mahdi.fifa.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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

        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        long insertID = database.insert(TABLE_CLUBS, null, club.getContentValues());

        if (insertID == -1)
            Log.i("database", "Club data insertion failed. (Club: " + club.getClubName() + " ) ");
        else
            Log.i("database", "Club data inserted with id: " + insertID);
        if (database.isOpen()) database.close();
        Log.i("database", "Clubs database closed");
    }

    public ArrayList<Club> getAllClubs() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Club> clubs = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_CLUBS + "'", null);
        if (cursor.moveToFirst()) {
            do {

                Club club = new Club(
                        cursor.getInt(cursor.getColumnIndex(Club.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Club.KEY_NAME)),
                        cursor.getLong(cursor.getColumnIndex(Club.KEY_WEALTH)),
                        cursor.getInt(cursor.getColumnIndex(Club.KEY_MT)),
                        cursor.getInt(cursor.getColumnIndex(Club.KEY_TM)),
                        cursor.getInt(cursor.getColumnIndex(Club.KEY_CHAMPIONS)),
                        cursor.getInt(cursor.getColumnIndex(Club.KEY_EUROPE)),
                        cursor.getInt(cursor.getColumnIndex(Club.KEY_GOLDEN)),
                        cursor.getString(cursor.getColumnIndex(Club.KEY_CLASS)),
                        cursor.getInt(cursor.getColumnIndex(Club.KEY_OWNER)));
                clubs.add(club);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return clubs;
    }

    public void updateClub(Club club) {
        SQLiteDatabase database = getWritableDatabase();
        int count = database.update(TABLE_CLUBS, club.getContentValues(), Club.KEY_ID + " = " + club.getClubID(), null);
        if (count != 1) Log.e("ClubsData", "Error in update method");
    }

    public Club getClubFromID(int clubID) {
        SQLiteDatabase database = getReadableDatabase();
        Club club = null;
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_CLUBS + "' WHERE " + Club.KEY_ID + " = " + clubID, null);
        if (cursor.moveToFirst()) {
            club = new Club(
                    cursor.getInt(cursor.getColumnIndex(Club.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Club.KEY_NAME)),
                    cursor.getLong(cursor.getColumnIndex(Club.KEY_WEALTH)),
                    cursor.getInt(cursor.getColumnIndex(Club.KEY_MT)),
                    cursor.getInt(cursor.getColumnIndex(Club.KEY_TM)),
                    cursor.getInt(cursor.getColumnIndex(Club.KEY_CHAMPIONS)),
                    cursor.getInt(cursor.getColumnIndex(Club.KEY_EUROPE)),
                    cursor.getInt(cursor.getColumnIndex(Club.KEY_GOLDEN)),
                    cursor.getString(cursor.getColumnIndex(Club.KEY_CLASS)),
                    cursor.getInt(cursor.getColumnIndex(Club.KEY_OWNER)));
        }
        cursor.close();
        return club;
    }
}