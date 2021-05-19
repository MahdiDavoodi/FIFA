package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Club;
import davoodi.mahdi.fifa.components.League;
import davoodi.mahdi.fifa.components.Match;
import davoodi.mahdi.fifa.components.Season;
import davoodi.mahdi.fifa.data.ClubsData;
import davoodi.mahdi.fifa.data.LeaguesData;
import davoodi.mahdi.fifa.data.RanksData;
import davoodi.mahdi.fifa.data.ResultsData;
import davoodi.mahdi.fifa.data.SeasonsData;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class PlayPage extends AppCompatActivity {

    AppPreferences preferences;
    ArrayList<Club> owner_1_clubs, owner_2_clubs;
    RanksData ranksData;
    Season season;
    League league;
    ResultsData resultsData;
    int matchesPlayed;
    Match currentMatch;
    Club home, away;
    ClubsData clubsData;

    // UI
    TextView season_text, league_text;
    EditText home_goals_input, away_goals_input;
    ImageView home_image, away_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_page);
        initialize();
    }

    private void initialize() {
        // DataBase.
        readData();

        // What league and situation we have.
        whatToDo();

        // Set Current Match.
        currentMatch = resultsData.getNextMatch(season.getSeasonID(), league.getLeagueID());

        // Set Clubs.
        home = clubsData.getClubFromID(currentMatch.getHomeTeamID());
        away = clubsData.getClubFromID(currentMatch.getAwayTeamID());

        // Set UI ID.
        season_text = findViewById(R.id.playSeasonText);
        league_text = findViewById(R.id.playLeagueText);
        home_image = findViewById(R.id.playHomeImage);
        away_image = findViewById(R.id.playAwayImage);
        home_goals_input = findViewById(R.id.playHomeInput);
        away_goals_input = findViewById(R.id.playAwayInput);

        // Set UI.
        season_text.setText(getResources().getString(R.string.playSeasonText, season.getSeasonID()));
        league_text.setText(league.getLeagueName());

        home_image.setImageResource(getResources().getIdentifier("club" + home.getClubID(),
                "drawable",
                getPackageName()));
        away_image.setImageResource(getResources().getIdentifier("club" + away.getClubID(),
                "drawable",
                getPackageName()));

    }

    private void readData() {
        // Preferences.
        preferences = new AppPreferences(this);

        // Ranks.
        ranksData = new RanksData(this);

        // Season.
        SeasonsData seasonsData = new SeasonsData(this);
        season = seasonsData.getSeason(preferences.getCurrentSeason());
        matchesPlayed = season.getSeasonMatchesPlayed();

        // Leagues.
        LeaguesData leaguesData = new LeaguesData(this);
        league = leaguesData.getLeagueFromID(League.currentLeagueID(matchesPlayed));

        // Results.
        resultsData = new ResultsData(this);

        // Clubs.
        ArrayList<Club> rankedClubs = ranksData.getAllRankedClubs();
        owner_1_clubs = setOwnerClubs(1, rankedClubs);
        owner_2_clubs = setOwnerClubs(2, rankedClubs);
        clubsData = new ClubsData(this);

    }


    private void whatToDo() {
        switch (league.getLeagueID()) {
            case 2:
                controlTM();
                break;
            case 3:
                controlChampions();
                break;
            case 4:
                controlEurope();
                break;
            case 5:
                controlGolden();
                break;
            default:
                controlMT();
                break;
        }
    }

    // MT
    private void controlMT() {
        // Create MT For First Time.
        if (!preferences.getMtCreated())
            createMT();

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
            preferences.setMtCreated(true);
        }
    }


    private void controlGolden() {
    }

    private void controlEurope() {
    }

    private void controlChampions() {
    }

    private void controlTM() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }
}