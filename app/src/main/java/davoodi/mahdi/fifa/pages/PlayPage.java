package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    ArrayList<Club> ranked_clubs;
    ArrayList<Club> tm_clubs, cl_clubs;
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

        if (owner_1_clubs.contains(home)) {
            home_owner = ownersData.getOwnerFromID(1);
            away_owner = ownersData.getOwnerFromID(2);
        }
        if (owner_2_clubs.contains(home)) {
            home_owner = ownersData.getOwnerFromID(2);
            away_owner = ownersData.getOwnerFromID(1);
        }

        // TM Clubs.
        tm_clubs = new ArrayList<>();
        for (int i = 8; i < 16; i++) {
            tm_clubs.add(ranked_clubs.get(i));
        }

        // CL Clubs.
        cl_clubs = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            cl_clubs.add(ranked_clubs.get(i));
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
        ranked_clubs = ranksData.getAllRankedClubs();

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
        clubsData = new ClubsData(this);
        owner_1_clubs = setOwnerClubs(1, ranked_clubs);
        owner_2_clubs = setOwnerClubs(2, ranked_clubs);
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

    // MT.
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

    private void finishMT() {
        Club first = ranked_clubs.get(0);
        Club second = ranked_clubs.get(1);
        Club third = ranked_clubs.get(2);

        first.setClubMT(first.getClubMT() + 1);
        first.setClubWealth(first.getClubWealth() + 250);

        second.setClubWealth(second.getClubWealth() + 80);
        third.setClubWealth(third.getClubWealth() + 50);

        clubsData.updateClub(first);
        clubsData.updateClub(second);
        clubsData.updateClub(third);

        Club club;
        for (int i = 3; i < 8; i++) {
            club = ranked_clubs.get(i);
            club.setClubWealth(club.getClubWealth() + 20);
            clubsData.updateClub(club);
        }
        for (int i = 8; i < 16; i++) {
            club = ranked_clubs.get(i);
            club.setClubWealth(club.getClubWealth() + 10);
            clubsData.updateClub(club);
        }
        for (int i = 16; i < 20; i++) {
            club = ranked_clubs.get(i);
            club.setClubWealth(club.getClubWealth() + 3);
            clubsData.updateClub(club);
        }

        season.setSeasonMTWinnerID(first.getClubID());
        seasonsData.updateSeason(season);

        Owner first_owner = ownersData.getOwnerFromID(first.getClubOwner());
        first_owner.setOwnerTotalCups(first_owner.getOwnerTotalCups() + 1);
        ownersData.updateOwner(first_owner);
    }

    // TM.
    private void createTM() {
        if (!preferences.getTmCreated()) {
            finishMT();
            Collections.shuffle(tm_clubs);
            if (tm_clubs.size() == 8) {
                for (int i = 0; i < tm_clubs.size(); i = i + 2) {
                    Match match = new Match(0, season.getSeasonID(), 2, tm_clubs.get(i).getClubID(),
                            tm_clubs.get(i + 1).getClubID(), 0, 0, 0);
                    resultsData.insertMatch(match);
                }
                preferences.setTmCreated(true);
                league.setLeagueNumber(league.getLeagueNumber() + 1);
                leaguesData.updateLeague(league);
            } else
                Log.e("PlayPage", "TM creation failed!");
        } else createNewGameTM();
    }

    private void createNewGameTM() {
        ArrayList<Match> tm_matches = resultsData.getAllRemainMatches(season.getSeasonID(), league.getLeagueID());
        if (tm_matches.isEmpty()) {
            ArrayList<Match> past_results = resultsData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
            Club home, away;
            for (Match match :
                    past_results) {
                home = clubsData.getClubFromID(match.getHomeTeamID());
                away = clubsData.getClubFromID(match.getAwayTeamID());
                if (match.getHomeGoals() > match.getAwayGoals()) {
                    tm_clubs.remove(away);
                } else if (match.getHomeGoals() < match.getAwayGoals()) {
                    tm_clubs.remove(home);
                } else Log.e("PlayPage", "Error in createNewGameTM");
            }

            // All winners remain in tm_clubs.
            Collections.shuffle(tm_clubs);
            if ((tm_clubs.size() % 2) == 0) {
                for (int i = 0; i < tm_clubs.size(); i = i + 2) {
                    Match match = new Match(0, season.getSeasonID(), 2, tm_clubs.get(i).getClubID(),
                            tm_clubs.get(i + 1).getClubID(), 0, 0, 0);
                    resultsData.insertMatch(match);
                }
            } else
                Log.e("PlayPage", "TM creation of new game failed!");
        }
    }

    private void finishTM() {
        ArrayList<Match> past_results = resultsData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
        Club home, away;
        for (Match match :
                past_results) {
            home = clubsData.getClubFromID(match.getHomeTeamID());
            away = clubsData.getClubFromID(match.getAwayTeamID());
            if (match.getHomeGoals() > match.getAwayGoals()) {
                tm_clubs.remove(away);
            } else if (match.getHomeGoals() < match.getAwayGoals()) {
                tm_clubs.remove(home);
            } else Log.e("PlayPage", "Error in finish Champions");
        }
        if (tm_clubs.size() == 1) {

            Club winner = tm_clubs.get(0);

            winner.setClubTM(winner.getClubTM() + 1);
            winner.setClubWealth(winner.getClubWealth() + 40);
            clubsData.updateClub(winner);

            season.setSeasonTMWinnerID(winner.getClubID());
            seasonsData.updateSeason(season);

            Owner winner_owner = ownersData.getOwnerFromID(winner.getClubOwner());
            winner_owner.setOwnerTotalCups(winner_owner.getOwnerTotalCups() + 1);
            ownersData.updateOwner(winner_owner);

        } else Log.e("PlayPage", "Finish TM Fucked up!");
    }

    // Champions.
    private void createChampions() {
        if (!preferences.getChampionsCreated()) {
            finishTM();
            Collections.shuffle(cl_clubs);
            if (cl_clubs.size() == 8) {
                for (int i = 0; i < cl_clubs.size(); i = i + 2) {
                    Match match = new Match(0, season.getSeasonID(), 3, cl_clubs.get(i).getClubID(),
                            cl_clubs.get(i + 1).getClubID(), 0, 0, 0);
                    resultsData.insertMatch(match);
                }
                preferences.setChampionsCreated(true);
                league.setLeagueNumber(league.getLeagueNumber() + 1);
                leaguesData.updateLeague(league);
            } else
                Log.e("PlayPage", "Champions creation failed!");
        } else createNewGameChampions();
    }

    private void createNewGameChampions() {
        ArrayList<Match> cl_matches = resultsData.getAllRemainMatches(season.getSeasonID(), league.getLeagueID());
        if (cl_matches.isEmpty()) {
            ArrayList<Match> past_results = resultsData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
            Club home, away;
            for (Match match :
                    past_results) {
                home = clubsData.getClubFromID(match.getHomeTeamID());
                away = clubsData.getClubFromID(match.getAwayTeamID());
                if (match.getHomeGoals() > match.getAwayGoals()) {
                    cl_clubs.remove(away);
                } else if (match.getHomeGoals() < match.getAwayGoals()) {
                    cl_clubs.remove(home);
                } else Log.e("PlayPage", "Error in createNewGameChampions");
            }

            // All winners remain in cl_clubs.
            Collections.shuffle(cl_clubs);
            if ((cl_clubs.size() % 2) == 0) {
                for (int i = 0; i < cl_clubs.size(); i = i + 2) {
                    Match match = new Match(0, season.getSeasonID(), 3, cl_clubs.get(i).getClubID(),
                            cl_clubs.get(i + 1).getClubID(), 0, 0, 0);
                    resultsData.insertMatch(match);
                }
            } else
                Log.e("PlayPage", "Champions creation of new game failed!");
        }
    }

    private void finishChampions() {
        ArrayList<Match> past_results = resultsData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
        Club home, away;
        for (Match match :
                past_results) {
            home = clubsData.getClubFromID(match.getHomeTeamID());
            away = clubsData.getClubFromID(match.getAwayTeamID());
            if (match.getHomeGoals() > match.getAwayGoals()) {
                cl_clubs.remove(away);
            } else if (match.getHomeGoals() < match.getAwayGoals()) {
                cl_clubs.remove(home);
            } else Log.e("PlayPage", "Error in finish Champions");
        }
        if (cl_clubs.size() == 1) {

            Club winner = cl_clubs.get(0);

            winner.setClubChampions(winner.getClubChampions() + 1);
            winner.setClubWealth(winner.getClubWealth() + 200);
            clubsData.updateClub(winner);

            season.setSeasonChampionsWinnerID(winner.getClubID());
            seasonsData.updateSeason(season);

            Owner winner_owner = ownersData.getOwnerFromID(winner.getClubOwner());
            winner_owner.setOwnerTotalCups(winner_owner.getOwnerTotalCups() + 1);
            ownersData.updateOwner(winner_owner);

        } else Log.e("PlayPage", "Finish Champions Fucked up!");
    }

    // Europe.
    private void createEurope() {
        if (!preferences.getEuropeCreated()) {
            finishChampions();
            Club tm_winner = clubsData.getClubFromID(season.getSeasonTMWinnerID());
            Club cl_winner = clubsData.getClubFromID(season.getSeasonChampionsWinnerID());

            Match match = new Match(0, season.getSeasonID(), 4, tm_winner.getClubID(),
                    cl_winner.getClubID(), 0, 0, 0);
            resultsData.insertMatch(match);
            preferences.setEuropeCreated(true);
            league.setLeagueNumber(league.getLeagueNumber() + 1);
            leaguesData.updateLeague(league);
        } else
            Log.e("PlayPage", "Europe creation failed!");
    }

    private void finishEurope() {
        ArrayList<Match> past_results = resultsData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
        if (past_results.size() == 1) {
            Club home, away, europe_winner = null;
            Match match = past_results.get(0);

            home = clubsData.getClubFromID(match.getHomeTeamID());
            away = clubsData.getClubFromID(match.getAwayTeamID());
            if (match.getHomeGoals() > match.getAwayGoals()) {
                europe_winner = home;
            } else if (match.getHomeGoals() < match.getAwayGoals()) {
                europe_winner = away;
            } else Log.e("PlayPage", "Error in finish Europe result goals.");

            assert europe_winner != null;
            europe_winner.setClubEurope(europe_winner.getClubEurope() + 1);
            europe_winner.setClubWealth(europe_winner.getClubWealth() + 70);
            clubsData.updateClub(europe_winner);

            season.setSeasonEuropeWinnerID(europe_winner.getClubID());
            seasonsData.updateSeason(season);

            Owner winner_owner = ownersData.getOwnerFromID(europe_winner.getClubOwner());
            winner_owner.setOwnerTotalCups(winner_owner.getOwnerTotalCups() + 1);
            ownersData.updateOwner(winner_owner);
        } else Log.e("PlayPage", "Error in finish Europe past results.");
    }

    // Golden.
    private void createGolden() {
        if (!preferences.getGoldenCreated()) {
            finishEurope();
            Club mt_winner = clubsData.getClubFromID(season.getSeasonMTWinnerID());
            Club europe_winner = clubsData.getClubFromID(season.getSeasonEuropeWinnerID());
            Match match;
            if (mt_winner.equals(europe_winner)) {
                Club mt_second = ranked_clubs.get(1);
                match = new Match(0, season.getSeasonID(), 5, mt_winner.getClubID(),
                        mt_second.getClubID(), 0, 0, 0);
            } else {
                match = new Match(0, season.getSeasonID(), 5, mt_winner.getClubID(),
                        europe_winner.getClubID(), 0, 0, 0);
            }

            resultsData.insertMatch(match);
            preferences.setGoldenCreated(true);
            league.setLeagueNumber(league.getLeagueNumber() + 1);
            leaguesData.updateLeague(league);
        }
    }

    private void finishGolden() {
        ArrayList<Match> past_results = resultsData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
        if (past_results.size() == 1) {
            Club home, away, golden_winner = null;
            Match match = past_results.get(0);

            home = clubsData.getClubFromID(match.getHomeTeamID());
            away = clubsData.getClubFromID(match.getAwayTeamID());
            if (match.getHomeGoals() > match.getAwayGoals()) {
                golden_winner = home;
            } else if (match.getHomeGoals() < match.getAwayGoals()) {
                golden_winner = away;
            } else Log.e("PlayPage", "Error in finish Golden result goals.");

            assert golden_winner != null;
            golden_winner.setClubGolden(golden_winner.getClubGolden() + 1);
            golden_winner.setClubWealth(golden_winner.getClubWealth() + 100);
            clubsData.updateClub(golden_winner);

            season.setSeasonGoldenWinnerID(golden_winner.getClubID());
            seasonsData.updateSeason(season);

            Owner winner_owner = ownersData.getOwnerFromID(golden_winner.getClubOwner());
            winner_owner.setOwnerTotalCups(winner_owner.getOwnerTotalCups() + 1);
            ownersData.updateOwner(winner_owner);
            finishSeason();

        } else Log.e("PlayPage", "Error in finish Golden past results.");
    }

    // Finish Season.
    private void finishSeason() {
        int new_season = preferences.getCurrentSeason() + 1;
        preferences.setCurrentSeason(new_season);
        Season newSeason = new Season(new_season,
                0,
                0,
                0,
                0,
                0,
                0);
        seasonsData.insertSeason(newSeason);
        ranksData.refreshRanksData();
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
        startActivity(new Intent(PlayPage.this, MainPage.class));
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }

    // Save Button.
    public void playSaveOnClick(View view) {
        if (isInputValid()) {
            updateDataBase();
            if (league.getLeagueID() == 1)
                manageMT();
            Toast.makeText(this, R.string.done, Toast.LENGTH_LONG).show();
            startActivity(new Intent(PlayPage.this, MainPage.class));
            overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
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

        int mp = season.getSeasonMatchesPlayed() + 1;
        season.setSeasonMatchesPlayed(mp);
        seasonsData.updateSeason(season);

        if (mp == 116)
            finishGolden();
    }
}
