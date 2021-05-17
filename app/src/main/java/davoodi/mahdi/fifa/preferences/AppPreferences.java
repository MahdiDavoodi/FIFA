package davoodi.mahdi.fifa.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    SharedPreferences preferences;

    private static final String APP_PREF = "appPreferences";
    private static final String LAST_SEEN = "Visited";
    private static final String CURRENT_SEASON = "Season";
    private static final String SEASON_DATABASE_CREATED = "SeasonDatabase";

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

    public void setSeasonDatabaseCreated(boolean created) {
        preferences.edit().putBoolean(SEASON_DATABASE_CREATED, created).apply();
    }

    public boolean getSeasonDatabaseCreated() {
        return preferences.getBoolean(SEASON_DATABASE_CREATED, false);
    }
}
