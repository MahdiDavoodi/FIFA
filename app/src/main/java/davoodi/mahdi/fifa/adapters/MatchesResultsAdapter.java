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
import davoodi.mahdi.fifa.data.SeasonsData;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class MatchesResultsAdapter extends RecyclerView.Adapter<MatchesResultsAdapter.ViewHolder> {

    Context context;
    ArrayList<League> leagues;
    ArrayList<Club> clubs;
    ArrayList<Match> matches;
    AppPreferences preferences;

    Season season;
    League league;


    public MatchesResultsAdapter(Context context) {
        this.context = context;
        readData();
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
    }

    private void setLeagueToShow() {
        
    }

    private void fillMatches() {

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
        // set widgets information
    }

    @Override
    public int getItemCount() {
        // Items number.
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
