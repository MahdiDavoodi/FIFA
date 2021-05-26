package davoodi.mahdi.fifa.components;

import android.content.ContentValues;

public class Rank {
    public static final String KEY_CLUB = "clubID";
    public static final String KEY_PLAYED = "matchesPlayed";
    public static final String KEY_WIN = "win";
    public static final String KEY_LOSS = "loss";
    public static final String KEY_DRAW = "draw";
    public static final String KEY_GD = "goalDifference";
    public static final String KEY_PTS = "points";


    private final int clubID;
    private int matchesPlayed;
    private int win;
    private int loss;
    private int draw;
    private int goalDifference;
    private int points;


    public Rank(int clubID, int matchesPlayed, int win, int loss, int draw, int goalDifference, int points) {
        this.clubID = clubID;
        this.matchesPlayed = matchesPlayed;
        this.win = win;
        this.loss = loss;
        this.draw = draw;
        this.goalDifference = goalDifference;
        this.points = points;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_CLUB, getClubID());
        values.put(KEY_PLAYED, getMatchesPlayed());
        values.put(KEY_WIN, getWin());
        values.put(KEY_LOSS, getLoss());
        values.put(KEY_DRAW, getDraw());
        values.put(KEY_GD, getGoalDifference());
        values.put(KEY_PTS, getPoints());
        return values;
    }

    public int getClubID() {
        return clubID;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
