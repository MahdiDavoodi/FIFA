package davoodi.mahdi.fifa.components;

import android.content.ContentValues;

public class League {
    public static final String KEY_ID = "leagueID";
    public static final String KEY_NAME = "leagueName";
    public static final String KEY_NUMBER = "leagueNumber";

    private int leagueID;
    private String leagueName;
    private int leagueNumber;

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, getLeagueID());
        values.put(KEY_NAME, getLeagueName());
        values.put(KEY_NUMBER, getLeagueNumber());
        return values;
    }

    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public int getLeagueNumber() {
        return leagueNumber;
    }

    public void setLeagueNumber(int leagueNumber) {
        this.leagueNumber = leagueNumber;
    }

    @Override
    public String toString() {
        return leagueName;
    }
}
