package davoodi.mahdi.fifa.components;

public class Player {
    public static final String KEY_ID = "playerID";
    public static final String KEY_NAME = "playerName";
    public static final String KEY_AGE = "playerAge";
    public static final String KEY_OVERALL = "playerOverall";
    public static final String KEY_CLUB = "playerCurrentClubID";

    private long playerID;
    private int playerAge, playerOverall, playerCurrentClubID;
    private String playerName;

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public int getPlayerAge() {
        return playerAge;
    }

    public void setPlayerAge(int playerAge) {
        this.playerAge = playerAge;
    }

    public int getPlayerOverall() {
        return playerOverall;
    }

    public void setPlayerOverall(int playerOverall) {
        this.playerOverall = playerOverall;
    }

    public int getPlayerCurrentClubID() {
        return playerCurrentClubID;
    }

    public void setPlayerCurrentClubID(int playerCurrentClubID) {
        this.playerCurrentClubID = playerCurrentClubID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return playerName;
    }
}