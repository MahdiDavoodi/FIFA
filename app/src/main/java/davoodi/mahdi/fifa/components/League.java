package davoodi.mahdi.fifa.components;

public class League {
    public static final String KEY_ID = "leagueID";
    public static final String KEY_NAME = "leagueName";
    public static final String KEY_NUMBER = "leagueNumber";

    private int leagueID;
    private String leagueName;
    private int leagueNumber;

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
