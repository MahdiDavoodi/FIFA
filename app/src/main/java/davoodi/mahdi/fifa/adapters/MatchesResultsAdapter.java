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
    ArrayList<Match> matches;
    AppPreferences preferences;
    ClubsData clubsData;
    int matchesPlayed;
    Season season;
    League league;

    public MatchesResultsAdapter(Context context) {
        this.context = context;
        readData();
        setMatchesToShow();
    }

    private void readData() {
        // Clubs.
        clubsData = new ClubsData(context);

        // Preferences.
        preferences = new AppPreferences(Objects.requireNonNull(context));

        // Season.
        SeasonsData seasonsData = new SeasonsData(context);
        season = seasonsData.getSeason(preferences.getCurrentSeason());
        matchesPlayed = season.getSeasonMatchesPlayed();

        // Leagues.
        LeaguesData leaguesData = new LeaguesData(context);
        league = leaguesData.getLeagueFromID(League.currentLeagueID(matchesPlayed));
    }

    private void setMatchesToShow() {
        ResultsData resultsData = new ResultsData(context);
        matches = resultsData.getAllSeasonMatches(season.getSeasonID(), league.getLeagueID());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.matches_results_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This Match.
        Match match = matches.get(position);

        // set widgets information
        Club home_team = clubsData.getClubFromID(match.getHomeTeamID());
        Club away_team = clubsData.getClubFromID(match.getAwayTeamID());

        assert home_team != null;
        assert away_team != null;

        // Card views.

        if (home_team.getClubOwner() == 1) {
            holder.owner_color_1.setCardBackgroundColor(context.getResources().getColor(R.color.blue, context.getTheme()));
        } else {
            holder.owner_color_1.setCardBackgroundColor(context.getResources().getColor(R.color.red, context.getTheme()));
        }

        if (away_team.getClubOwner() == 1) {
            holder.owner_color_2.setCardBackgroundColor(context.getResources().getColor(R.color.blue, context.getTheme()));

        } else {

            holder.owner_color_2.setCardBackgroundColor(context.getResources().getColor(R.color.red, context.getTheme()));
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
            holder.result.setText(context.getString(R.string.matchesNotPlayed));
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