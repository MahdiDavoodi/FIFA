package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Club;
import davoodi.mahdi.fifa.components.League;
import davoodi.mahdi.fifa.components.Match;
import davoodi.mahdi.fifa.components.Owner;
import davoodi.mahdi.fifa.components.Rank;
import davoodi.mahdi.fifa.components.Season;
import davoodi.mahdi.fifa.data.ClubsData;
import davoodi.mahdi.fifa.data.LeaguesData;
import davoodi.mahdi.fifa.data.OwnersData;
import davoodi.mahdi.fifa.data.RanksData;
import davoodi.mahdi.fifa.data.ResultsData;
import davoodi.mahdi.fifa.data.SeasonsData;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class PlayPage extends AppCompatActivity {

    // Preferences.
    AppPreferences preferences;

    // Useful Components.
    Season season;
    League league;
    Club home, away;
    Match currentMatch;
    int matchesPlayed, home_goals, away_goals;
    ArrayList<Club> owner_1_clubs, owner_2_clubs;
    Owner home_owner, away_owner;


    // DataBase.
    RanksData ranksData;
    ResultsData resultsData;
    ClubsData clubsData;
    OwnersData ownersData;
    SeasonsData seasonsData;
    LeaguesData leaguesData;

    // UI.
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
        whatToCreate();

        // Set Current Match.
        currentMatch = resultsData.getNextMatch(season.getSeasonID(), league.getLeagueID());

        // Set Clubs.
        home = clubsData.getClubFromID(currentMatch.getHomeTeamID());
        away = clubsData.getClubFromID(currentMatch.getAwayTeamID());

        // Owners.
        ownersData = new OwnersData(this);

        for (Club club :
                owner_1_clubs) {
            if (club.getClubID() == home.getClubID()) {
                home_owner = ownersData.getOwnerFromID(1);
                away_owner = ownersData.getOwnerFromID(2);
                break;
            }
        }
        for (Club club :
                owner_2_clubs) {
            if (club.getClubID() == home.getClubID()) {
                home_owner = ownersData.getOwnerFromID(2);
                away_owner = ownersData.getOwnerFromID(1);
                break;
            }
        }

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
        seasonsData = new SeasonsData(this);
        season = seasonsData.getSeason(preferences.getCurrentSeason());
        matchesPlayed = season.getSeasonMatchesPlayed();

        // Leagues.
        leaguesData = new LeaguesData(this);
        league = leaguesData.getLeagueFromID(League.currentLeagueID(matchesPlayed));

        // Results.
        resultsData = new ResultsData(this);

        // Clubs.
        ArrayList<Club> rankedClubs = ranksData.getAllRankedClubs();
        owner_1_clubs = setOwnerClubs(1, rankedClubs);
        owner_2_clubs = setOwnerClubs(2, rankedClubs);
        clubsData = new ClubsData(this);
    }

    private void whatToCreate() {
        switch (league.getLeagueID()) {
            case 2:
                createTM();
                break;
            case 3:
                createChampions();
                break;
            case 4:
                createEurope();
                break;
            case 5:
                createGolden();
                break;
            default:
                createMT();
                break;
        }
    }

    private void createMT() {
        // Create MT For First Time.
        if (!preferences.getMtCreated()) {
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

                        Match match = new Match(0, season.getSeasonID(), 1, owner_1_clubs.get(j).getClubID(),
                                owner_2_clubs.get(temp).getClubID(), 0, 0, 0);
                        resultsData.insertMatch(match);
                    }
                }
                // Set Preferences.
                preferences.setMtCreated(true);
                league.setLeagueNumber(league.getLeagueNumber() + 1);
                leaguesData.updateLeague(league);
            }
        }
    }

    private void manageMT() {
        Rank home_rank = ranksData.getClubRank(home.getClubID());
        Rank away_rank = ranksData.getClubRank(away.getClubID());

        // Match played.
        home_rank.setMatchesPlayed(home_rank.getMatchesPlayed() + 1);
        away_rank.setMatchesPlayed(away_rank.getMatchesPlayed() + 1);

        // Points and ...
        if (home_goals > away_goals) {

            // Home Won.
            home_rank.setWin(home_rank.getWin() + 1);
            away_rank.setLoss(away_rank.getLoss() + 1);

            home_rank.setGoalDifference(home_rank.getGoalDifference() + (home_goals - away_goals));
            away_rank.setGoalDifference(away_rank.getGoalDifference() + (away_goals - home_goals));

            home_rank.setPoints(home_rank.getPoints() + 3);

        } else if (away_goals > home_goals) {

            // Away Won.
            away_rank.setWin(away_rank.getWin() + 1);
            home_rank.setLoss(home_rank.getLoss() + 1);

            home_rank.setGoalDifference(home_rank.getGoalDifference() + (home_goals - away_goals));
            away_rank.setGoalDifference(away_rank.getGoalDifference() + (away_goals - home_goals));

            away_rank.setPoints(away_rank.getPoints() + 3);
        } else {
            // Draw.
            home_rank.setDraw(home_rank.getDraw() + 1);
            away_rank.setDraw(away_rank.getDraw() + 1);

            home_rank.setPoints(home_rank.getPoints() + 1);
            away_rank.setPoints(away_rank.getPoints() + 1);
        }

        ranksData.updateRank(home_rank);
        ranksData.updateRank(away_rank);
    }

    private void createGolden() {
    }

    private void createEurope() {
    }

    private void createChampions() {
    }

    private void createTM() {
    }

    private void manageTM() {

    }

    private void manageChampions() {

    }

    private void manageEurope() {

    }

    private void manageGolden() {

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

    // Save Button.
    public void playSaveOnClick(View view) {
        if (isInputValid()) {
            updateDataBase();
            switch (league.getLeagueID()) {
                case 2:
                    manageTM();
                    break;
                case 3:
                    manageChampions();
                    break;
                case 4:
                    manageEurope();
                    break;
                case 5:
                    manageGolden();
                    break;
                default:
                    manageMT();
                    break;
            }
            Toast.makeText(this, R.string.done, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    // Input Valid Checker.
    public boolean isInputValid() {
        String homeChecker = home_goals_input.getText().toString();
        String awayChecker = away_goals_input.getText().toString();
        if (homeChecker.isEmpty() || awayChecker.isEmpty()) {
            Toast.makeText(this, R.string.playToast1, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            home_goals = Integer.parseInt(homeChecker);
            away_goals = Integer.parseInt(awayChecker);
            return true;
        }
    }

    // Same Data Change In Any Case.
    private void updateDataBase() {
        // Match.
        currentMatch.setHomeGoals(home_goals);
        currentMatch.setAwayGoals(away_goals);
        currentMatch.setMatchPlayed(1);
        resultsData.updateMatch(currentMatch);

        // Choose the winner and loser.
        if (home_goals > away_goals) {
            home_owner.setOwnerTotalWin(home_owner.getOwnerTotalWin() + 1);
            away_owner.setOwnerTotalLoss(away_owner.getOwnerTotalLoss() + 1);
        } else if (away_goals > home_goals) {
            away_owner.setOwnerTotalWin(away_owner.getOwnerTotalWin() + 1);
            home_owner.setOwnerTotalLoss(home_owner.getOwnerTotalLoss() + 1);
        } else {
            away_owner.setOwnerTotalDraw(away_owner.getOwnerTotalDraw() + 1);
            home_owner.setOwnerTotalDraw(home_owner.getOwnerTotalDraw() + 1);
        }
        ownersData.updateOwner(home_owner);
        ownersData.updateOwner(away_owner);

        season.setSeasonMatchesPlayed(season.getSeasonMatchesPlayed() + 1);
        seasonsData.updateSeason(season);
    }
}
