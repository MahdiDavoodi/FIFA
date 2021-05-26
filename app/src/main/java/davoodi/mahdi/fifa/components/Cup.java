package davoodi.mahdi.fifa.components;

import android.content.ContentValues;

public class Cup {
    public static final String KEY_ID = "cupID";
    public static final String KEY_SEASON = "seasonID";
    public static final String KEY_LEAGUE = "leagueID";
    public static final String KEY_WINNER = "winnerID";


    public Cup(int cupID, int seasonID, int leagueID, int winnerID) {
        this.cupID = cupID;
        this.seasonID = seasonID;
        this.leagueID = leagueID;
        this.winnerID = winnerID;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, getCupID());
        values.put(KEY_SEASON, getSeasonID());
        values.put(KEY_LEAGUE, getLeagueID());
        values.put(KEY_WINNER, getWinnerID());
        return values;
    }

    private final int cupID;
    private int seasonID;
    private int leagueID;
    private int winnerID;

    public int getCupID() {
        return cupID;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }

    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    public int getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(int winnerID) {
        this.winnerID = winnerID;
    }
}
