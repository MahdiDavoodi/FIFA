package davoodi.mahdi.oufa.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import davoodi.mahdi.oufa.components.Club;
import davoodi.mahdi.oufa.components.Cup;
import davoodi.mahdi.oufa.components.League;
import davoodi.mahdi.oufa.components.Match;
import davoodi.mahdi.oufa.components.Owner;
import davoodi.mahdi.oufa.components.Rank;
import davoodi.mahdi.oufa.components.Season;
import davoodi.mahdi.oufa.components.Transfer;

public class FifaData extends SQLiteOpenHelper {

    // Database.
    private static final String DB_NAME = "fifa-db";
    private static final int DB_VERSION = 1;

    // Clubs Table.
    public static final String TABLE_CLUBS = "clubs";
    private static final String CMD_CREATE_CLUBS = "CREATE TABLE IF NOT EXISTS '" + TABLE_CLUBS + "' " + "( '"
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

    // Cups Table.
    public static final String TABLE_CUPS = "cups";
    private static final String CMD_CREATE_CUPS = "CREATE TABLE IF NOT EXISTS '" + TABLE_CUPS + "' " + "( '"
            + Cup.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Cup.KEY_SEASON + "' INTEGER, '"
            + Cup.KEY_LEAGUE + "' INTEGER, '"
            + Cup.KEY_WINNER + "' INTEGER"
            + ")";

    // Leagues Table.
    public static final String TABLE_LEAGUES = "leagues";
    private static final String CMD_CREATE_LEAGUES = "CREATE TABLE IF NOT EXISTS '" + TABLE_LEAGUES + "' " + "( '"
            + League.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + League.KEY_NAME + "' TEXT, '"
            + League.KEY_NUMBER + "' INTEGER"
            + ")";

    // Owners Table.
    public static final String TABLE_OWNERS = "owners";
    private static final String CMD_CREATE_OWNERS = "CREATE TABLE IF NOT EXISTS '" + TABLE_OWNERS + "' " + "( '"
            + Owner.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Owner.KEY_NAME + "' TEXT, '"
            + Owner.KEY_PASSWORD + "' INTEGER, '"
            + Owner.KEY_WEALTH + "' INTEGER, '"
            + Owner.KEY_WIN + "' INTEGER, '"
            + Owner.KEY_LOSS + "' INTEGER, '"
            + Owner.KEY_DRAW + "' INTEGER, '"
            + Owner.KEY_CUPS + "' INTEGER"
            + ")";

    // Ranks Table.
    public static final String TABLE_RANKS = "ranks";
    private static final String CMD_CREATE_RANKS = "CREATE TABLE IF NOT EXISTS '" + TABLE_RANKS + "' " + "( '"
            + Rank.KEY_CLUB + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Rank.KEY_PLAYED + "' INTEGER, '"
            + Rank.KEY_WIN + "' INTEGER, '"
            + Rank.KEY_LOSS + "' INTEGER, '"
            + Rank.KEY_DRAW + "' INTEGER, '"
            + Rank.KEY_GD + "' INTEGER, '"
            + Rank.KEY_PTS + "' INTEGER"
            + ")";

    // Results Table.
    public static final String TABLE_RESULTS = "results";
    private static final String CMD_CREATE_RESULTS = "CREATE TABLE IF NOT EXISTS '" + TABLE_RESULTS + "' " + "( '"
            + Match.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Match.KEY_SEASON + "' INTEGER, '"
            + Match.KEY_LEAGUE + "' INTEGER, '"
            + Match.KEY_HOME + "' INTEGER, '"
            + Match.KEY_AWAY + "' INTEGER, '"
            + Match.KEY_HOME_GOALS + "' INTEGER, '"
            + Match.KEY_AWAY_GOALS + "' INTEGER, '"
            + Match.KEY_MATCH_PLAYED + "' INTEGER"
            + ")";

    // Seasons Table.
    public static final String TABLE_SEASONS = "seasons";
    private static final String CMD_CREATE_SEASONS = "CREATE TABLE IF NOT EXISTS '" + TABLE_SEASONS + "' " + "( '"
            + Season.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Season.KEY_MT + "' INTEGER, '"
            + Season.KEY_TM + "' INTEGER, '"
            + Season.KEY_CHAMPIONS + "' INTEGER, '"
            + Season.KEY_EUROPE + "' INTEGER, '"
            + Season.KEY_GOLDEN + "' INTEGER, '"
            + Season.KEY_MATCHES + "' INTEGER"
            + ")";

    // Transfers Table.
    public static final String TABLE_TRANSFERS = "transfers";
    private static final String CMD_CREATE_TRANSFERS = "CREATE TABLE IF NOT EXISTS '" + TABLE_TRANSFERS + "' " + "( '"
            + Transfer.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Transfer.KEY_FROM + "' INTEGER, '"
            + Transfer.KEY_TO + "' INTEGER, '"
            + Transfer.KEY_PLAYER + "' TEXT, '"
            + Transfer.KEY_PRICE + "' INTEGER"
            + ")";


    public FifaData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Clubs.
        database.execSQL(CMD_CREATE_CLUBS);
        Log.i("database", "Table '" + TABLE_CLUBS + "' created!");

        // Cups.
        database.execSQL(CMD_CREATE_CUPS);
        Log.i("database", "Table '" + TABLE_CUPS + "' created!");

        // Leagues.
        database.execSQL(CMD_CREATE_LEAGUES);
        Log.i("database", "Table '" + TABLE_LEAGUES + "' created!");

        // Owners.
        database.execSQL(CMD_CREATE_OWNERS);
        Log.i("database", "Table '" + TABLE_OWNERS + "' created!");

        // Ranks.
        database.execSQL(CMD_CREATE_RANKS);
        Log.i("database", "Table '" + TABLE_RANKS + "' created!");

        // Results.
        database.execSQL(CMD_CREATE_RESULTS);
        Log.i("database", "Table '" + TABLE_RESULTS + "' created!");

        // Seasons.
        database.execSQL(CMD_CREATE_SEASONS);
        Log.i("database", "Table '" + TABLE_SEASONS + "' created!");

        // Transfers.
        database.execSQL(CMD_CREATE_TRANSFERS);
        Log.i("database", "Table '" + TABLE_TRANSFERS + "' created!");

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // We should get backups from old data in future version.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CLUBS);
        Log.i("database", "Table '" + TABLE_CLUBS + "' dropped!");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CUPS);
        Log.i("database", "Table '" + TABLE_CUPS + "' dropped!");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAGUES);
        Log.i("database", "Table '" + TABLE_LEAGUES + "' dropped!");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_OWNERS);
        Log.i("database", "Table '" + TABLE_OWNERS + "' dropped!");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RANKS);
        Log.i("database", "Table '" + TABLE_RANKS + "' dropped!");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        Log.i("database", "Table '" + TABLE_RESULTS + "' dropped!");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SEASONS);
        Log.i("database", "Table '" + TABLE_SEASONS + "' dropped!");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSFERS);
        Log.i("database", "Table '" + TABLE_TRANSFERS + "' dropped!");

        onCreate(database);
        // We should restore database.
    }

    // Clubs Table.
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

    // Cups Table.
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

    // Leagues Table.
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

    // Owners Table.
    public void insertOwner(Owner owner) {
        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        long insertID = database.insert(TABLE_OWNERS, null, owner.getContentValues());

        if (insertID == -1)
            Log.i("database", "Owner data insertion failed. (Season: " + owner.toString() + " ) ");
        else
            Log.i("database", "Owner data inserted with id: " + insertID);
        if (database.isOpen()) database.close();
        Log.i("database", "Owners database closed");
    }

    public void updateOwner(Owner owner) {
        SQLiteDatabase database = getWritableDatabase();
        int count = database.update(TABLE_OWNERS, owner.getContentValues(), Owner.KEY_ID + " = " + owner.getOwnerID(), null);
        if (count != 1) Log.e("OwnersData", "Error in update method");
        if (database.isOpen()) database.close();
    }

    public Owner getOwnerFromID(int ownerID) {
        SQLiteDatabase database = getReadableDatabase();
        Owner owner = null;
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_OWNERS + "' WHERE " + Owner.KEY_ID + " = " + ownerID, null);
        if (cursor.moveToFirst()) {

            Log.i("OwnersData", "everything is good in getOwnerFromID");
            owner = new Owner(
                    cursor.getInt(cursor.getColumnIndex(Owner.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Owner.KEY_NAME)),
                    cursor.getLong(cursor.getColumnIndex(Owner.KEY_PASSWORD)),
                    cursor.getLong(cursor.getColumnIndex(Owner.KEY_WEALTH)),
                    cursor.getLong(cursor.getColumnIndex(Owner.KEY_WIN)),
                    cursor.getLong(cursor.getColumnIndex(Owner.KEY_LOSS)),
                    cursor.getLong(cursor.getColumnIndex(Owner.KEY_DRAW)),
                    cursor.getInt(cursor.getColumnIndex(Owner.KEY_CUPS)));


        } else Log.e("OwnersData", "Problem in getOwnerFromID");
        cursor.close();
        if (database.isOpen()) database.close();
        return owner;
    }

    public ArrayList<Owner> getAllOwners() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Owner> owners = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_OWNERS + "'", null);
        if (cursor.moveToFirst()) {
            do {

                Owner owner = new Owner(
                        cursor.getInt(cursor.getColumnIndex(Owner.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Owner.KEY_NAME)),
                        cursor.getLong(cursor.getColumnIndex(Owner.KEY_PASSWORD)),
                        cursor.getLong(cursor.getColumnIndex(Owner.KEY_WEALTH)),
                        cursor.getLong(cursor.getColumnIndex(Owner.KEY_WIN)),
                        cursor.getLong(cursor.getColumnIndex(Owner.KEY_LOSS)),
                        cursor.getLong(cursor.getColumnIndex(Owner.KEY_DRAW)),
                        cursor.getInt(cursor.getColumnIndex(Owner.KEY_CUPS)));
                owners.add(owner);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return owners;
    }

    // Ranks Data.
    public void insertClubsRanks(ArrayList<Club> clubs) {
        for (Club club :
                clubs) {
            insertClubRank(club);
        }
    }

    public void insertClubRank(Club club) {

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
        for (Rank rank :
                ranks) {
            clubs.add(getClubFromID(rank.getClubID()));
        }
        return clubs;
    }

    // Results Table.
    public ArrayList<Match> getAllSeasonMatches(int seasonID, int leagueID) {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Match> matches = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_RESULTS
                + "' WHERE " + Match.KEY_SEASON + " = " + seasonID + " AND " + Match.KEY_LEAGUE + " = " + leagueID
                + " ORDER BY " + Match.KEY_ID + " ASC ", null);
        if (cursor.moveToFirst()) {
            do {

                Match match = new Match(cursor.getInt(cursor.getColumnIndex(Match.KEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_SEASON)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_LEAGUE)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_HOME)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_AWAY)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_HOME_GOALS)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_AWAY_GOALS)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_MATCH_PLAYED)));
                matches.add(match);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return matches;
    }

    public ArrayList<Match> getAllRemainMatches(int seasonID, int leagueID) {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Match> matches = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_RESULTS
                + "' WHERE " + Match.KEY_SEASON + " = " + seasonID + " AND " + Match.KEY_LEAGUE + " = " + leagueID + " AND "
                + Match.KEY_MATCH_PLAYED + " = 0"
                + " ORDER BY " + Match.KEY_ID + " ASC ", null);
        if (cursor.moveToFirst()) {
            do {

                Match match = new Match(cursor.getInt(cursor.getColumnIndex(Match.KEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_SEASON)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_LEAGUE)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_HOME)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_AWAY)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_HOME_GOALS)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_AWAY_GOALS)),
                        cursor.getInt(cursor.getColumnIndex(Match.KEY_MATCH_PLAYED)));
                matches.add(match);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return matches;
    }

    public void insertMatch(Match match) {

        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        // Let SQLite set id.
        ContentValues values = match.getContentValues();
        values.remove(Match.KEY_ID);
        //
        long insertID = database.insert(TABLE_RESULTS, null, values);

        if (insertID == -1)
            Log.i("database", "Match data insertion failed. (Match: " + match.getMatchID() + " ) ");
        else
            Log.i("database", "Match data inserted with id: " + insertID);
        // Better to not close it.
    }

    public Match getNextMatch(int seasonID, int leagueID) {
        SQLiteDatabase database = getReadableDatabase();
        Match match = null;
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_RESULTS
                + "' WHERE " + Match.KEY_SEASON + " = " + seasonID + " AND " + Match.KEY_LEAGUE + " = " + leagueID
                + " AND " + Match.KEY_MATCH_PLAYED + " = 0  LIMIT 1", null);
        if (cursor.moveToFirst()) {
            match = new Match(cursor.getInt(cursor.getColumnIndex(Match.KEY_ID)),
                    cursor.getInt(cursor.getColumnIndex(Match.KEY_SEASON)),
                    cursor.getInt(cursor.getColumnIndex(Match.KEY_LEAGUE)),
                    cursor.getInt(cursor.getColumnIndex(Match.KEY_HOME)),
                    cursor.getInt(cursor.getColumnIndex(Match.KEY_AWAY)),
                    cursor.getInt(cursor.getColumnIndex(Match.KEY_HOME_GOALS)),
                    cursor.getInt(cursor.getColumnIndex(Match.KEY_AWAY_GOALS)),
                    cursor.getInt(cursor.getColumnIndex(Match.KEY_MATCH_PLAYED)));
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return match;
    }

    public void updateMatch(Match match) {
        SQLiteDatabase database = getWritableDatabase();
        int count = database.update(TABLE_RESULTS, match.getContentValues(), Match.KEY_ID + " = " + match.getMatchID(), null);
        if (count != 1) Log.e("ResultsData", "Error in update method" + count);
        if (database.isOpen()) database.close();
    }

    // Seasons Table.
    public void updateSeason(Season season) {
        SQLiteDatabase database = getWritableDatabase();
        int count = database.update(TABLE_SEASONS, season.getContentValues(), Season.KEY_ID + " = " + season.getSeasonID(), null);
        if (count != 1) Log.e("SeasonsData", "Error in update method");
        if (database.isOpen()) database.close();
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

    public Season getSeason(int ID) {
        Season season = null;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_SEASONS + " WHERE " + Season.KEY_ID + " = " + ID, null);
        if (cursor.moveToFirst())
            season = new Season(
                    cursor.getInt(cursor.getColumnIndex(Season.KEY_ID)),
                    cursor.getInt(cursor.getColumnIndex(Season.KEY_MT)),
                    cursor.getInt(cursor.getColumnIndex(Season.KEY_TM)),
                    cursor.getInt(cursor.getColumnIndex(Season.KEY_CHAMPIONS)),
                    cursor.getInt(cursor.getColumnIndex(Season.KEY_EUROPE)),
                    cursor.getInt(cursor.getColumnIndex(Season.KEY_GOLDEN)),
                    cursor.getInt(cursor.getColumnIndex(Season.KEY_MATCHES))
            );
        else
            Log.e("database", "Seasons database has problem!");


        cursor.close();
        if (database.isOpen()) database.close();
        return season;
    }

    // Transfers Table.
    public void insertTransfer(Transfer transfer) {

        // Now we work with our database.
        SQLiteDatabase database = getWritableDatabase();
        // Let SQLite set id.
        ContentValues values = transfer.getContentValues();
        values.remove(Transfer.KEY_ID);
        //
        long insertID = database.insert(TABLE_TRANSFERS, null, values);

        if (insertID == -1)
            Log.i("database", "Transfer data insertion failed. (Transfer: " + transfer.getPlayerName() + " ) ");
        else
            Log.i("database", "Transfer data inserted with id: " + insertID);
        // Better to not close it.
    }

    public ArrayList<Transfer> getAllTransfers() {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Transfer> transfers = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + TABLE_TRANSFERS
                + "' ORDER BY " + Transfer.KEY_ID + " DESC ", null);
        if (cursor.moveToFirst()) {
            do {

                Transfer transfer = new Transfer(cursor.getInt(cursor.getColumnIndex(Transfer.KEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(Transfer.KEY_FROM)),
                        cursor.getInt(cursor.getColumnIndex(Transfer.KEY_TO)),
                        cursor.getString(cursor.getColumnIndex(Transfer.KEY_PLAYER)),
                        cursor.getLong(cursor.getColumnIndex(Transfer.KEY_PRICE)));
                transfers.add(transfer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (database.isOpen()) database.close();
        return transfers;
    }
}