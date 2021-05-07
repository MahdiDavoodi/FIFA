package davoodi.mahdi.fifa.components;

import android.content.ContentValues;

public class Club {
    public static final String KEY_ID = "clubID";
    public static final String KEY_NAME = "clubName";
    public static final String KEY_WEALTH = "clubWealth";
    public static final String KEY_MT = "clubMT";
    public static final String KEY_TM = "clubTM";
    public static final String KEY_CHAMPIONS = "clubChampions";
    public static final String KEY_EUROPE = "clubEurope";
    public static final String KEY_GOLDEN = "clubGolden";
    public static final String KEY_CLASS = "clubClass";
    public static final String KEY_OWNER = "clubOwner";


    private int clubID;
    private String clubName;
    private long clubWealth;
    private int clubMT, clubTM, clubChampions, clubEurope, clubGolden;
    private String clubClass;
    private int clubOwner;

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, getClubID());
        values.put(KEY_NAME, getClubName());
        values.put(KEY_WEALTH, getClubWealth());
        values.put(KEY_MT, getClubMT());
        values.put(KEY_TM, getClubTM());
        values.put(KEY_CHAMPIONS, getClubChampions());
        values.put(KEY_EUROPE, getClubEurope());
        values.put(KEY_GOLDEN, getClubGolden());
        values.put(KEY_CLASS, getClubClass());
        return values;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public long getClubWealth() {
        return clubWealth;
    }

    public void setClubWealth(long clubWealth) {
        this.clubWealth = clubWealth;
    }

    public int getClubMT() {
        return clubMT;
    }

    public void setClubMT(int clubMT) {
        this.clubMT = clubMT;
    }

    public int getClubTM() {
        return clubTM;
    }

    public void setClubTM(int clubTM) {
        this.clubTM = clubTM;
    }

    public int getClubChampions() {
        return clubChampions;
    }

    public void setClubChampions(int clubChampions) {
        this.clubChampions = clubChampions;
    }

    public int getClubEurope() {
        return clubEurope;
    }

    public void setClubEurope(int clubEurope) {
        this.clubEurope = clubEurope;
    }

    public int getClubGolden() {
        return clubGolden;
    }

    public void setClubGolden(int clubGolden) {
        this.clubGolden = clubGolden;
    }

    public String getClubClass() {
        return clubClass;
    }

    public void setClubClass(String clubClass) {
        this.clubClass = clubClass;
    }

    public int getClubOwner() {
        return clubOwner;
    }

    public void setClubOwner(int clubOwner) {
        this.clubOwner = clubOwner;
    }

    @Override
    public String toString() {
        return clubName;
    }
}