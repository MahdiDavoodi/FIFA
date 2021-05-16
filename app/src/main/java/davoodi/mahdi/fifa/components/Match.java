package davoodi.mahdi.fifa.components;

import android.content.ContentValues;

public class Match {
    public static final String KEY_ID = "matchID";
    public static final String KEY_SEASON = "seasonID";
    public static final String KEY_LEAGUE = "leagueID";
    public static final String KEY_HOME = "homeTeamID";
    public static final String KEY_AWAY = "awayTeamID";
    public static final String KEY_HOME_GOALS = "homeGoals";
    public static final String KEY_AWAY_GOALS = "awayGoals";
    public static final String KEY_MATCH_PLAYED = "matchPlayed";

    public Match(long matchID, int seasonID, int leagueID, int homeTeamID, int awayTeamID, int homeGoals, int awayGoals, int matchPlayed) {
        this.matchID = matchID;
        this.seasonID = seasonID;
        this.leagueID = leagueID;
        this.homeTeamID = homeTeamID;
        this.awayTeamID = awayTeamID;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.matchPlayed = matchPlayed;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, getMatchID());
        values.put(KEY_SEASON, getSeasonID());
        values.put(KEY_LEAGUE, getLeagueID());
        values.put(KEY_HOME, getHomeTeamID());
        values.put(KEY_AWAY, getAwayTeamID());
        values.put(KEY_HOME_GOALS, getHomeGoals());
        values.put(KEY_AWAY_GOALS, getAwayGoals());
        values.put(KEY_MATCH_PLAYED, getMatchPlayed());
        return values;
    }

    private long matchID;
    private int seasonID,
            leagueID,
            homeTeamID,
            awayTeamID,
            homeGoals,
            awayGoals,
            matchPlayed;

    public long getMatchID() {
        return matchID;
    }

    public void setMatchID(long matchID) {
        this.matchID = matchID;
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

    public int getHomeTeamID() {
        return homeTeamID;
    }

    public void setHomeTeamID(int homeTeamID) {
        this.homeTeamID = homeTeamID;
    }

    public int getAwayTeamID() {
        return awayTeamID;
    }

    public void setAwayTeamID(int awayTeamID) {
        this.awayTeamID = awayTeamID;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public int getMatchPlayed() {
        return matchPlayed;
    }

    public void setMatchPlayed(int matchPlayed) {
        this.matchPlayed = matchPlayed;
    }
}
