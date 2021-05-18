package davoodi.mahdi.fifa.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    SharedPreferences preferences;

    private static final String APP_PREF = "appPreferences";
    private static final String LAST_SEEN = "Visited";
    private static final String CURRENT_SEASON = "Season";
    private static final String MT_CREATED = "MTDatabase";
    private static final String TM_CREATED = "TMDatabase";
    private static final String CHAMPIONS_CREATED = "ChampionsDatabase";
    private static final String EUROPE_CREATED = "EuropeDatabase";
    private static final String GOLDEN_CREATED = "GoldenDatabase";

    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
    }

    public String getLastSeen() {
        return preferences.getString(LAST_SEEN, "404-NOT-FOUND");
    }

    public void setLastSeen(String lastSeen) {
        preferences.edit().putString(LAST_SEEN, lastSeen).apply();
    }

    public void setCurrentSeason(int season) {
        preferences.edit().putInt(CURRENT_SEASON, season).apply();
    }

    public int getCurrentSeason() {
        return preferences.getInt(CURRENT_SEASON, 0);
    }

    public void setMtCreated(boolean created) {
        preferences.edit().putBoolean(MT_CREATED, created).apply();
    }

    public boolean getMtCreated() {
        return preferences.getBoolean(MT_CREATED, false);
    }

    public void setTmCreated(boolean created) {
        preferences.edit().putBoolean(TM_CREATED, created).apply();
    }

    public boolean getTmCreated() {
        return preferences.getBoolean(TM_CREATED, false);
    }

    public void setChampionsCreated(boolean created) {
        preferences.edit().putBoolean(CHAMPIONS_CREATED, created).apply();
    }

    public boolean getChampionsCreated() {
        return preferences.getBoolean(CHAMPIONS_CREATED, false);
    }

    public void setEuropeCreated(boolean created) {
        preferences.edit().putBoolean(EUROPE_CREATED, created).apply();
    }

    public boolean getEuropeCreated() {
        return preferences.getBoolean(EUROPE_CREATED, false);
    }

    public void setGoldenCreated(boolean created) {
        preferences.edit().putBoolean(GOLDEN_CREATED, created).apply();
    }

    public boolean getGoldenCreated() {
        return preferences.getBoolean(GOLDEN_CREATED, false);
    }
}
