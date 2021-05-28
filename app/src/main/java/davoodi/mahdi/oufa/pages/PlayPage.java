package davoodi.mahdi.oufa.pages;

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

import davoodi.mahdi.oufa.R;
import davoodi.mahdi.oufa.components.Club;
import davoodi.mahdi.oufa.components.Cup;
import davoodi.mahdi.oufa.components.League;
import davoodi.mahdi.oufa.components.Match;
import davoodi.mahdi.oufa.components.Owner;
import davoodi.mahdi.oufa.components.Rank;
import davoodi.mahdi.oufa.components.Season;
import davoodi.mahdi.oufa.data.FifaData;
import davoodi.mahdi.oufa.preferences.AppPreferences;

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
    FifaData fifaData;

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
        fifaData = new FifaData(this);

        // preferences.
        preferences = new AppPreferences(this);

        // ranks.
        ranked_clubs = fifaData.getAllRankedClubs();

        // Season.
        season = fifaData.getSeason(preferences.getCurrentSeason());
        matchesPlayed = season.getSeasonMatchesPlayed();

        // Leagues.
        league = fifaData.getLeagueFromID(League.currentLeagueID(matchesPlayed));

        // Clubs.
        owner_1_clubs = setOwnerClubs(1, ranked_clubs);
        owner_2_clubs = setOwnerClubs(2, ranked_clubs);

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

        // What league and situation we have.
        whatToCreate();

        // Set Current Match.
        currentMatch = fifaData.getNextMatch(season.getSeasonID(), league.getLeagueID());

        // Set Clubs.
        home = fifaData.getClubFromID(currentMatch.getHomeTeamID());
        away = fifaData.getClubFromID(currentMatch.getAwayTeamID());

        // Owners.
        if (owner_1_clubs.contains(home)) {
            home_owner = fifaData.getOwnerFromID(1);
            away_owner = fifaData.getOwnerFromID(2);
        }
        if (owner_2_clubs.contains(home)) {
            home_owner = fifaData.getOwnerFromID(2);
            away_owner = fifaData.getOwnerFromID(1);
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
                        fifaData.insertMatch(match);
                    }
                }
                // Set Preferences.
                preferences.setMtCreated(true);
                league.setLeagueNumber(league.getLeagueNumber() + 1);
                fifaData.updateLeague(league);
            }
        }
    }

    private void manageMT() {
        Rank home_rank = fifaData.getClubRank(home.getClubID());
        Rank away_rank = fifaData.getClubRank(away.getClubID());

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

        fifaData.updateRank(home_rank);
        fifaData.updateRank(away_rank);
    }

    private void finishMT() {
        Club first = ranked_clubs.get(0);
        Club second = ranked_clubs.get(1);
        Club third = ranked_clubs.get(2);

        first.setClubMT(first.getClubMT() + 1);
        first.setClubWealth(first.getClubWealth() + 250);

        second.setClubWealth(second.getClubWealth() + 80);
        third.setClubWealth(third.getClubWealth() + 50);

        fifaData.updateClub(first);
        fifaData.updateClub(second);
        fifaData.updateClub(third);

        Club club;
        for (int i = 3; i < 8; i++) {
            club = ranked_clubs.get(i);
            club.setClubWealth(club.getClubWealth() + 20);
            fifaData.updateClub(club);
        }
        for (int i = 8; i < 16; i++) {
            club = ranked_clubs.get(i);
            club.setClubWealth(club.getClubWealth() + 10);
            fifaData.updateClub(club);
        }
        for (int i = 16; i < 20; i++) {
            club = ranked_clubs.get(i);
            club.setClubWealth(club.getClubWealth() + 3);
            fifaData.updateClub(club);
        }

        season.setSeasonMTWinnerID(first.getClubID());
        fifaData.updateSeason(season);

        Owner first_owner = fifaData.getOwnerFromID(first.getClubOwner());
        first_owner.setOwnerTotalCups(first_owner.getOwnerTotalCups() + 1);
        fifaData.updateOwner(first_owner);

        fifaData.insertCup(new Cup(0, season.getSeasonID(),
                league.getLeagueID() - 1, first.getClubID()));
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
                    fifaData.insertMatch(match);
                }
                preferences.setTmCreated(true);
                league.setLeagueNumber(league.getLeagueNumber() + 1);
                fifaData.updateLeague(league);
            } else
                Log.e("PlayPage", "TM creation failed!");
        } else createNewGameTM();
    }

    private void createNewGameTM() {
        ArrayList<Match> tm_matches = fifaData.getAllRemainMatches(season.getSeasonID(), league.getLeagueID());
        if (tm_matches.isEmpty()) {
            ArrayList<Match> past_results = fifaData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
            Club home, away;
            for (Match match :
                    past_results) {
                home = fifaData.getClubFromID(match.getHomeTeamID());
                away = fifaData.getClubFromID(match.getAwayTeamID());
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
                    fifaData.insertMatch(match);
                }
            } else
                Log.e("PlayPage", "TM creation of new game failed!");
        }
    }

    private void finishTM() {
        ArrayList<Match> past_results = fifaData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID() - 1);
        Club home, away;
        for (Match match :
                past_results) {
            home = fifaData.getClubFromID(match.getHomeTeamID());
            away = fifaData.getClubFromID(match.getAwayTeamID());
            if (match.getHomeGoals() > match.getAwayGoals()) {
                tm_clubs.remove(away);
            } else if (match.getHomeGoals() < match.getAwayGoals()) {
                tm_clubs.remove(home);
            } else Log.e("PlayPage", "Error in finish TM");
        }
        if (tm_clubs.size() == 1) {

            Club winner = tm_clubs.get(0);

            winner.setClubTM(winner.getClubTM() + 1);
            winner.setClubWealth(winner.getClubWealth() + 40);
            fifaData.updateClub(winner);

            season.setSeasonTMWinnerID(winner.getClubID());
            fifaData.updateSeason(season);

            Owner winner_owner = fifaData.getOwnerFromID(winner.getClubOwner());
            winner_owner.setOwnerTotalCups(winner_owner.getOwnerTotalCups() + 1);
            fifaData.updateOwner(winner_owner);

            fifaData.insertCup(new Cup(0, season.getSeasonID(),
                    league.getLeagueID() - 1, winner.getClubID()));

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
                    fifaData.insertMatch(match);
                }
                preferences.setChampionsCreated(true);
                league.setLeagueNumber(league.getLeagueNumber() + 1);
                fifaData.updateLeague(league);
            } else
                Log.e("PlayPage", "Champions creation failed!");
        } else createNewGameChampions();
    }

    private void createNewGameChampions() {
        ArrayList<Match> cl_matches = fifaData.getAllRemainMatches(season.getSeasonID(), league.getLeagueID());
        if (cl_matches.isEmpty()) {
            ArrayList<Match> past_results = fifaData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
            Club home, away;
            for (Match match :
                    past_results) {
                home = fifaData.getClubFromID(match.getHomeTeamID());
                away = fifaData.getClubFromID(match.getAwayTeamID());
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
                    fifaData.insertMatch(match);
                }
            } else
                Log.e("PlayPage", "Champions creation of new game failed!");
        }
    }

    private void finishChampions() {
        ArrayList<Match> past_results = fifaData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID() - 1);
        Club home, away;
        for (Match match :
                past_results) {
            home = fifaData.getClubFromID(match.getHomeTeamID());
            away = fifaData.getClubFromID(match.getAwayTeamID());
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
            fifaData.updateClub(winner);

            season.setSeasonChampionsWinnerID(winner.getClubID());
            fifaData.updateSeason(season);

            Owner winner_owner = fifaData.getOwnerFromID(winner.getClubOwner());
            winner_owner.setOwnerTotalCups(winner_owner.getOwnerTotalCups() + 1);
            fifaData.updateOwner(winner_owner);

            fifaData.insertCup(new Cup(0, season.getSeasonID(),
                    league.getLeagueID() - 1, winner.getClubID()));

        } else Log.e("PlayPage", "Finish Champions Fucked up!");
    }

    // Europe.
    private void createEurope() {
        if (!preferences.getEuropeCreated()) {
            finishChampions();
            Club tm_winner = fifaData.getClubFromID(season.getSeasonTMWinnerID());
            Club cl_winner = fifaData.getClubFromID(season.getSeasonChampionsWinnerID());

            Match match = new Match(0, season.getSeasonID(), 4, tm_winner.getClubID(),
                    cl_winner.getClubID(), 0, 0, 0);
            fifaData.insertMatch(match);
            preferences.setEuropeCreated(true);
            league.setLeagueNumber(league.getLeagueNumber() + 1);
            fifaData.updateLeague(league);
        } else
            Log.e("PlayPage", "Europe creation failed!");
    }

    private void finishEurope() {
        ArrayList<Match> past_results = fifaData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID() - 1);
        if (past_results.size() == 1) {
            Club home, away, europe_winner = null;
            Match match = past_results.get(0);

            home = fifaData.getClubFromID(match.getHomeTeamID());
            away = fifaData.getClubFromID(match.getAwayTeamID());
            if (match.getHomeGoals() > match.getAwayGoals()) {
                europe_winner = home;
            } else if (match.getHomeGoals() < match.getAwayGoals()) {
                europe_winner = away;
            } else Log.e("PlayPage", "Error in finish Europe result goals.");

            assert europe_winner != null;
            europe_winner.setClubEurope(europe_winner.getClubEurope() + 1);
            europe_winner.setClubWealth(europe_winner.getClubWealth() + 70);
            fifaData.updateClub(europe_winner);

            season.setSeasonEuropeWinnerID(europe_winner.getClubID());
            fifaData.updateSeason(season);

            Owner winner_owner = fifaData.getOwnerFromID(europe_winner.getClubOwner());
            winner_owner.setOwnerTotalCups(winner_owner.getOwnerTotalCups() + 1);
            fifaData.updateOwner(winner_owner);

            fifaData.insertCup(new Cup(0, season.getSeasonID(),
                    league.getLeagueID() - 1, europe_winner.getClubID()));
        } else Log.e("PlayPage", "Error in finish Europe past results.");
    }

    // Golden.
    private void createGolden() {
        if (!preferences.getGoldenCreated()) {
            finishEurope();
            Club mt_winner = fifaData.getClubFromID(season.getSeasonMTWinnerID());
            Club europe_winner = fifaData.getClubFromID(season.getSeasonEuropeWinnerID());
            Match match;
            if (mt_winner.equals(europe_winner)) {
                Club mt_second = ranked_clubs.get(1);
                match = new Match(0, season.getSeasonID(), 5, mt_winner.getClubID(),
                        mt_second.getClubID(), 0, 0, 0);
            } else {
                match = new Match(0, season.getSeasonID(), 5, mt_winner.getClubID(),
                        europe_winner.getClubID(), 0, 0, 0);
            }

            fifaData.insertMatch(match);
            preferences.setGoldenCreated(true);
            league.setLeagueNumber(league.getLeagueNumber() + 1);
            fifaData.updateLeague(league);
        }
    }

    private void finishGolden() {
        ArrayList<Match> past_results = fifaData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
        if (past_results.size() == 1) {
            Club home, away, golden_winner = null;
            Match match = past_results.get(0);

            home = fifaData.getClubFromID(match.getHomeTeamID());
            away = fifaData.getClubFromID(match.getAwayTeamID());
            if (match.getHomeGoals() > match.getAwayGoals()) {
                golden_winner = home;
            } else if (match.getHomeGoals() < match.getAwayGoals()) {
                golden_winner = away;
            } else Log.e("PlayPage", "Error in finish Golden result goals.");

            assert golden_winner != null;
            golden_winner.setClubGolden(golden_winner.getClubGolden() + 1);
            golden_winner.setClubWealth(golden_winner.getClubWealth() + 100);
            fifaData.updateClub(golden_winner);

            season.setSeasonGoldenWinnerID(golden_winner.getClubID());
            fifaData.updateSeason(season);

            Owner winner_owner = fifaData.getOwnerFromID(golden_winner.getClubOwner());
            winner_owner.setOwnerTotalCups(winner_owner.getOwnerTotalCups() + 1);
            fifaData.updateOwner(winner_owner);

            fifaData.insertCup(new Cup(0, season.getSeasonID(),
                    league.getLeagueID(), golden_winner.getClubID()));

            finishSeason();

        } else Log.e("PlayPage", "Error in finish Golden past results.");
    }

    // Finish Season.
    private void finishSeason() {
        preferences.setSeasonCount(preferences.getSeasonCount() + 1);
        int new_season = preferences.getCurrentSeason() + 1;
        preferences.setCurrentSeason(new_season);
        Season newSeason = new Season(new_season,
                0,
                0,
                0,
                0,
                0,
                0);
        fifaData.insertSeason(newSeason);
        fifaData.refreshRanksData();

        preferences.setMtCreated(false);
        preferences.setTmCreated(false);
        preferences.setChampionsCreated(false);
        preferences.setEuropeCreated(false);
        preferences.setGoldenCreated(false);

        if ((preferences.getSeasonCount() % 5) == 0)
            controlMoney();
    }

    private void controlMoney() {
        long wealth;
        for (Club club :
                ranked_clubs) {
            wealth = club.getClubWealth();
            // remain 70% of wealth.
            wealth = (wealth * 70) / 100;
            club.setClubWealth(wealth);
            fifaData.updateClub(club);
        }
        Toast.makeText(this, "Money control applied!", Toast.LENGTH_LONG).show();
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
        } else if (league.getLeagueID() > 1 && homeChecker.equalsIgnoreCase(awayChecker)) {
            Toast.makeText(this, "It should not be equals!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            home_goals = Integer.parseInt(homeChecker);
            away_goals = Integer.parseInt(awayChecker);
            if (home_goals > 30 || away_goals > 30) {
                Toast.makeText(this, "Input is not realistic!", Toast.LENGTH_LONG).show();
                return false;
            } else
                return true;
        }
    }

    // Same Data Change In Any Case.
    private void updateDataBase() {
        // Match.
        currentMatch.setHomeGoals(home_goals);
        currentMatch.setAwayGoals(away_goals);
        currentMatch.setMatchPlayed(1);
        fifaData.updateMatch(currentMatch);

        // Choose the winner and loser.
        if (home.getClubOwner() != away.getClubOwner()) {
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
        }
        fifaData.updateOwner(home_owner);
        fifaData.updateOwner(away_owner);

        int mp = season.getSeasonMatchesPlayed() + 1;
        season.setSeasonMatchesPlayed(mp);
        fifaData.updateSeason(season);

        if (mp == 116)
            finishGolden();
    }
}
