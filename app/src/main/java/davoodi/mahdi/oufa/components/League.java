package davoodi.mahdi.oufa.components;

import android.content.ContentValues;

public class League {
    public static final String KEY_ID = "leagueID";
    public static final String KEY_NAME = "leagueName";
    public static final String KEY_NUMBER = "leagueNumber";

    private int leagueID;
    private String leagueName;
    private int leagueNumber;

    public League() {
    }

    public League(int leagueID, String leagueName, int leagueNumber) {
        this.leagueID = leagueID;
        this.leagueName = leagueName;
        this.leagueNumber = leagueNumber;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, getLeagueID());
        values.put(KEY_NAME, getLeagueName());
        values.put(KEY_NUMBER, getLeagueNumber());
        return values;
    }

    public static int currentLeagueID(int matchesPlayed) {
        int ID = 0;
        if (matchesPlayed < 100) {
            // MT.
            ID = 1;
        } else if (matchesPlayed < 107) {
            // TM.
            ID = 2;
        } else if (matchesPlayed < 114) {
            // Champions.
            ID = 3;
        } else if (matchesPlayed < 115) {
            // Europe.
            ID = 4;
        } else if (matchesPlayed < 116) {
            // Golden.
            ID = 5;
        }

        return ID;
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

}
