package davoodi.mahdi.fifa.components;

public class Season {

    public static final String KEY_ID = "seasonID";
    public static final String KEY_MT = "seasonMTWinnerID";
    public static final String KEY_TM = "seasonTMWinnerID";
    public static final String KEY_CHAMPIONS = "seasonChampionsWinnerID";
    public static final String KEY_EUROPE = "seasonEuropeWinnerID";
    public static final String KEY_GOLDEN = "seasonGoldenWinnerID";
    public static final String KEY_MATCHES = "seasonMatchesPlayed";

    private int seasonID,
            seasonMTWinnerID,
            seasonTMWinnerID,
            seasonChampionsWinnerID,
            seasonEuropeWinnerID,
            seasonGoldenWinnerID,
            seasonMatchesPlayed;

    public Season(int seasonID, int seasonMatchesPlayed) {
        this.seasonID = seasonID;
        this.seasonMatchesPlayed = seasonMatchesPlayed;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(int seasonID) {
        this.seasonID = seasonID;
    }

    public int getSeasonMTWinnerID() {
        return seasonMTWinnerID;
    }

    public void setSeasonMTWinnerID(int seasonMTWinnerID) {
        this.seasonMTWinnerID = seasonMTWinnerID;
    }

    public int getSeasonTMWinnerID() {
        return seasonTMWinnerID;
    }

    public void setSeasonTMWinnerID(int seasonTMWinnerID) {
        this.seasonTMWinnerID = seasonTMWinnerID;
    }

    public int getSeasonChampionsWinnerID() {
        return seasonChampionsWinnerID;
    }

    public void setSeasonChampionsWinnerID(int seasonChampionsWinnerID) {
        this.seasonChampionsWinnerID = seasonChampionsWinnerID;
    }

    public int getSeasonEuropeWinnerID() {
        return seasonEuropeWinnerID;
    }

    public void setSeasonEuropeWinnerID(int seasonEuropeWinnerID) {
        this.seasonEuropeWinnerID = seasonEuropeWinnerID;
    }

    public int getSeasonGoldenWinnerID() {
        return seasonGoldenWinnerID;
    }

    public void setSeasonGoldenWinnerID(int seasonGoldenWinnerID) {
        this.seasonGoldenWinnerID = seasonGoldenWinnerID;
    }

    public int getSeasonMatchesPlayed() {
        return seasonMatchesPlayed;
    }

    public void setSeasonMatchesPlayed(int seasonMatchesPlayed) {
        this.seasonMatchesPlayed = seasonMatchesPlayed;
    }

    @Override
    public String toString() {
        return String.valueOf(seasonID);
    }
}
