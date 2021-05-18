package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Club;
import davoodi.mahdi.fifa.components.Match;
import davoodi.mahdi.fifa.components.Season;
import davoodi.mahdi.fifa.data.RanksData;
import davoodi.mahdi.fifa.data.ResultsData;
import davoodi.mahdi.fifa.data.SeasonsData;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class PlayPage extends AppCompatActivity {

    AppPreferences preferences;
    ArrayList<Club> owner_1_clubs, owner_2_clubs;
    RanksData ranksData;
    Season season;
    ResultsData resultsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_page);

        readData();
        initialize();
    }

    private void initialize() {

        // Create MT For First Time.
        if (!preferences.getSeasonDatabaseCreated())
            createMT();
    }

    private void readData() {
        // Preferences.
        preferences = new AppPreferences(this);

        // Ranks.
        ranksData = new RanksData(this);

        // Season.
        SeasonsData seasonsData = new SeasonsData(this);
        season = seasonsData.getSeason(preferences.getCurrentSeason());

        // Results.
        resultsData = new ResultsData(this);

        // Clubs.
        ArrayList<Club> rankedClubs = ranksData.getAllRankedClubs();
        owner_1_clubs = setOwnerClubs(1, rankedClubs);
        owner_2_clubs = setOwnerClubs(2, rankedClubs);

    }

    private ArrayList<Club> setOwnerClubs(int ownerID, ArrayList<Club> rankedClubs) {
        ArrayList<Club> ownerClubs = new ArrayList<>();
        for (Club club :
                rankedClubs) {
            if (club.getClubOwner() == ownerID)
                ownerClubs.add(club);
        }
        return ownerClubs;
    }

    private void createMT() {

        if (owner_1_clubs.size() == 10 && owner_2_clubs.size() == 10) {

            // Shuffle Lists.
            Collections.shuffle(owner_1_clubs);
            Collections.shuffle(owner_2_clubs);
            int temp;

            // Create All Games.
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    temp = i + j;
                    if (temp >= 10)
                        temp = temp - 10;

                    Match match = new Match(season.getSeasonID(), 1, owner_1_clubs.get(j).getClubID(),
                            owner_2_clubs.get(temp).getClubID(), 0, 0, 0);
                    resultsData.insertMatch(match);
                }
            }
            // Set Preferences.
            preferences.setSeasonDatabaseCreated(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }
}