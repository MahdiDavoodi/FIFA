package davoodi.mahdi.fifa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Club;
import davoodi.mahdi.fifa.components.League;
import davoodi.mahdi.fifa.components.Match;
import davoodi.mahdi.fifa.components.Season;
import davoodi.mahdi.fifa.data.ClubsData;
import davoodi.mahdi.fifa.data.LeaguesData;
import davoodi.mahdi.fifa.data.ResultsData;
import davoodi.mahdi.fifa.data.SeasonsData;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class MatchesResultsAdapter extends RecyclerView.Adapter<MatchesResultsAdapter.ViewHolder> {

    Context context;
    ArrayList<League> leagues;
    ArrayList<Club> clubs;
    ArrayList<Match> matches;
    AppPreferences preferences;

    int matchesPlayed;
    Season season;
    League league;


    public MatchesResultsAdapter(Context context) {
        this.context = context;
        readData();
        setLeagueToShow();
        setMatchesToShow();
    }

    private void readData() {
        // Clubs.
        ClubsData clubsData = new ClubsData(context);
        clubs = clubsData.getAllClubs();

        // Leagues.
        LeaguesData leaguesData = new LeaguesData(context);
        leagues = leaguesData.getAllLeagues();

        // Preferences.
        preferences = new AppPreferences(Objects.requireNonNull(context));

        // Season.
        SeasonsData seasonsData = new SeasonsData(context);
        season = seasonsData.getSeason(preferences.getCurrentSeason());
        matchesPlayed = season.getSeasonMatchesPlayed();
    }

    private void setLeagueToShow() {
        int ID = 0;
        if (matchesPlayed < 200) {
            // MT.
            ID = 1;
        } else if (matchesPlayed < 207) {
            // TM.
            ID = 2;
        } else if (matchesPlayed < 221) {
            // Champions.
            ID = 3;
        } else if (matchesPlayed < 228) {
            // Europe.
            ID = 4;
        } else if (matchesPlayed < 229) {
            // Golden.
            ID = 5;
        }
        for (League league :
                leagues) {
            if (league.getLeagueID() == ID) this.league = league;
        }
    }

    private void setMatchesToShow() {
        ResultsData resultsData = new ResultsData(context);
        matches = resultsData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
    }

    private Club getClubFromID(int clubID) {
        for (Club club :
                clubs) {
            if (club.getClubID() == clubID)
                return club;
        }
        return null;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.matches_recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This Match.
        Match match = matches.get(position);

        // set widgets information
        Club home_team = getClubFromID(match.getHomeTeamID());
        Club away_team = getClubFromID(match.getAwayTeamID());

        assert home_team != null;
        assert away_team != null;

        // Card views.
        if (home_team.getClubOwner() == 1) {
            holder.owner_color_1.setBackgroundColor(context.getResources().getColor(R.color.blue, context.getTheme()));
            holder.owner_color_2.setBackgroundColor(context.getResources().getColor(R.color.red, context.getTheme()));
        } else if (home_team.getClubOwner() == 2) {
            holder.owner_color_1.setBackgroundColor(context.getResources().getColor(R.color.red, context.getTheme()));
            holder.owner_color_2.setBackgroundColor(context.getResources().getColor(R.color.blue, context.getTheme()));
        }

        // Images
        int home_imageRes = context.getResources().getIdentifier("club" + home_team.getClubID(),
                "drawable",
                context.getPackageName());
        holder.home_team_logo.setImageResource(home_imageRes);

        int away_imageRes = context.getResources().getIdentifier("club" + away_team.getClubID(),
                "drawable",
                context.getPackageName());
        holder.away_team_logo.setImageResource(away_imageRes);

        // Text Views.
        holder.home_team_name.setText(home_team.getClubName());
        holder.away_team_name.setText(away_team.getClubName());
        holder.league.setText(league.getLeagueName());

        if (match.getMatchPlayed() == 1)
            holder.result.setText(context.getString(R.string.match_result, match.getHomeGoals(), match.getAwayGoals()));
        else
            holder.result.setText("---");
    }

    @Override
    public int getItemCount() {
        // Items number.
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView owner_color_1, owner_color_2;
        TextView result, home_team_name, away_team_name, league;
        ImageView home_team_logo, away_team_logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Widgets.
            owner_color_1 = itemView.findViewById(R.id.matchesOwnerColorView1);
            owner_color_2 = itemView.findViewById(R.id.matchesOwnerColorView2);
            home_team_name = itemView.findViewById(R.id.matchesHomeTeamName);
            away_team_name = itemView.findViewById(R.id.matchesAwayTeamName);
            result = itemView.findViewById(R.id.matchesResultText);
            league = itemView.findViewById(R.id.matchesLeagueName);
            home_team_logo = itemView.findViewById(R.id.matchesHomeTeamImage);
            away_team_logo = itemView.findViewById(R.id.matchesAwayTeamImage);

        }
    }
}