package davoodi.mahdi.fifa.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import davoodi.mahdi.fifa.components.Club;
import davoodi.mahdi.fifa.components.Rank;


public class RanksData extends SQLiteOpenHelper {
    Context context;

    private static final String DB_NAME = "ranks-db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_RANKS = "ranks";
    private static final String CMD_CREATE = "CREATE TABLE IF NOT EXISTS '" + TABLE_RANKS + "' " + "( '"
            + Rank.KEY_CLUB + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Rank.KEY_PLAYED + "' INTEGER, '"
            + Rank.KEY_WIN + "' INTEGER, '"
            + Rank.KEY_LOSS + "' INTEGER, '"
            + Rank.KEY_DRAW + "' INTEGER, '"
            + Rank.KEY_GD + "' INTEGER, '"
            + Rank.KEY_PTS + "' INTEGER"
            + ")";


    public RanksData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CMD_CREATE);
        Log.i("database", "Table '" + TABLE_RANKS + "' created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // We should get backups from old data.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RANKS);
        Log.i("database", "Table '" + TABLE_RANKS + "' dropped!");
        onCreate(database);
        // We should restore database.
    }

    public void insertClubs(ArrayList<Club> clubs) {
        for (Club club :
                clubs) {
            insertClub(club);
        }
    }

    public void insertClub(Club club) {

        Rank rank = new Rank(club.getClubID(),
                0, 0, 0, 0, 0, 0);

        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        long insertID = database.insert(TABLE_RANKS, null, rank.getContentValues());

        if (insertID == -1)
            Log.i("database", "Rank data insertion failed. (Rank: " + club.getClubName() + " ) ");
        else
            Log.i("database", "Rank data inserted with id: " + insertID);


        // Better not to close.
    }

    public ArrayList<Rank> getAllRanks() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Rank> ranks = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_RANKS + "' ORDER BY "
                + Rank.KEY_PTS + " DESC, " + Rank.KEY_GD + " DESC, " + Rank.KEY_WIN + " DESC", null);
        if (cursor.getCount() == 20) {
            Log.i("RanksData", "Everything is okay.");
            if (cursor.moveToFirst()) {
                do {

                    Rank rank = new Rank(
                            cursor.getInt(cursor.getColumnIndex(Rank.KEY_CLUB)),
                            cursor.getInt(cursor.getColumnIndex(Rank.KEY_PLAYED)),
                            cursor.getInt(cursor.getColumnIndex(Rank.KEY_WIN)),
                            cursor.getInt(cursor.getColumnIndex(Rank.KEY_LOSS)),
                            cursor.getInt(cursor.getColumnIndex(Rank.KEY_DRAW)),
                            cursor.getInt(cursor.getColumnIndex(Rank.KEY_GD)),
                            cursor.getInt(cursor.getColumnIndex(Rank.KEY_PTS)));
                    ranks.add(rank);
                } while (cursor.moveToNext());
            }
        } else Log.e("RanksData", "Error in getAllRanks!");
        cursor.close();
        if (database.isOpen()) database.close();
        return ranks;
    }

    public void updateRank(Rank rank) {
        SQLiteDatabase database = getWritableDatabase();
        int count = database.update(TABLE_RANKS, rank.getContentValues(), Rank.KEY_CLUB + " = " + rank.getClubID(), null);
        if (count != 1) Log.e("RanksData", "Error in update method");
    }

    public Rank getClubRank(int clubID) {
        SQLiteDatabase database = getReadableDatabase();
        Rank rank = null;
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_RANKS + "' WHERE " + Rank.KEY_CLUB + " = " + clubID, null);
        if (cursor.moveToFirst()) {

            if (cursor.getCount() > 1)
                Log.e("DataBase", "RanksData fucked up!");
            rank = new Rank(
                    cursor.getInt(cursor.getColumnIndex(Rank.KEY_CLUB)),
                    cursor.getInt(cursor.getColumnIndex(Rank.KEY_PLAYED)),
                    cursor.getInt(cursor.getColumnIndex(Rank.KEY_WIN)),
                    cursor.getInt(cursor.getColumnIndex(Rank.KEY_LOSS)),
                    cursor.getInt(cursor.getColumnIndex(Rank.KEY_DRAW)),
                    cursor.getInt(cursor.getColumnIndex(Rank.KEY_GD)),
                    cursor.getInt(cursor.getColumnIndex(Rank.KEY_PTS)));
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return rank;
    }

    public void refreshRanksData() {
        ArrayList<Rank> ranks = getAllRanks();
        for (Rank rank :
                ranks) {
            rank.setMatchesPlayed(0);
            rank.setWin(0);
            rank.setLoss(0);
            rank.setDraw(0);
            rank.setGoalDifference(0);
            rank.setPoints(0);
            updateRank(rank);
        }
    }

    public ArrayList<Club> getAllRankedClubs() {
        ArrayList<Rank> ranks = getAllRanks();
        ArrayList<Club> clubs = new ArrayList<>();
        ClubsData clubsData = new ClubsData(context);
        for (Rank rank :
                ranks) {
            clubs.add(clubsData.getClubFromID(rank.getClubID()));
        }
        return clubs;
    }
}
