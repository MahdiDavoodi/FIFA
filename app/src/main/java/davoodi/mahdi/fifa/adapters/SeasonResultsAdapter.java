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

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Club;
import davoodi.mahdi.fifa.components.Match;
import davoodi.mahdi.fifa.components.Rank;
import davoodi.mahdi.fifa.data.RanksData;

public class SeasonResultsAdapter extends RecyclerView.Adapter<SeasonResultsAdapter.ViewHolder> {

    Context context;
    ArrayList<Club> ranked_clubs;
    ArrayList<Rank> ranks;
    RanksData ranksData;

    public SeasonResultsAdapter(Context context) {
        this.context = context;
        readData();
    }

    private void readData() {
        // Ranks.
        ranksData = new RanksData(context);
        ranks = ranksData.getAllRanks();

        // Clubs.
        ranked_clubs = ranksData.getAllRankedClubs();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.statistics_season_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int count = position - 1;
        if (count == -1) {
            holder.team_rank.setVisibility(View.INVISIBLE);
            holder.team_image.setVisibility(View.INVISIBLE);
        } else {
            holder.team_rank.setVisibility(View.VISIBLE);
            holder.team_image.setVisibility(View.VISIBLE);
            Rank rank = ranks.get(count);

            // Rank Color.
            if (position == 1) {
                holder.team_rank.setBackgroundColor(context.getResources().getColor(R.color.gold, context.getTheme()));
            } else if (position > 1 && position <= 8) {
                holder.team_rank.setBackgroundColor(context.getResources().getColor(R.color.blue, context.getTheme()));
            } else if (position >= 9 && position <= 16) {
                holder.team_rank.setBackgroundColor(context.getResources().getColor(R.color.orange, context.getTheme()));
            } else {
                holder.team_rank.setBackgroundColor(context.getResources().getColor(R.color.gray, context.getTheme()));
            }

            // Team Image.

            // Rank Info.


        }


        // This Match.
        Match match = matches.get(position);

        // set widgets information
        Club home_team = clubsData.getClubFromID(match.getHomeTeamID());
        Club away_team = clubsData.getClubFromID(match.getAwayTeamID());

        assert home_team != null;
        assert away_team != null;

        // Card views.
        if (home_team.getClubOwner() == 1) {
            holder.team_rank.setBackgroundColor();
            holder.owner_color_2.setBackgroundColor(context.getResources().getColor(R.color.red, context.getTheme()));
        } else if (home_team.getClubOwner() == 2) {
            holder.team_rank.setBackgroundColor(context.getResources().getColor(R.color.red, context.getTheme()));
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
        holder.played.setText(home_team.getClubName());
        holder.win.setText(away_team.getClubName());
        holder.loss.setText(league.getLeagueName());

        if (match.getMatchPlayed() == 1)
            holder.team_name.setText(context.getString(R.string.match_result, match.getHomeGoals(), match.getAwayGoals()));
        else
            holder.team_name.setText(context.getString(R.string.matchesNotPlayed));
    }

    @Override
    public int getItemCount() {
        // Items number.
        return ranks.size() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView team_rank;
        TextView team_name, played, win, loss, draw, gd, points;
        ImageView team_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Widgets.
            team_rank = itemView.findViewById(R.id.statistics_season_rank_color);
            played = itemView.findViewById(R.id.statistics_season_match_played);
            win = itemView.findViewById(R.id.statistics_season_win);
            loss = itemView.findViewById(R.id.statistics_season_loss);
            draw = itemView.findViewById(R.id.statistics_season_draw);
            gd = itemView.findViewById(R.id.statistics_season_goal_difference);
            points = itemView.findViewById(R.id.statistics_season_points);
            team_name = itemView.findViewById(R.id.statistics_season_team_name);
            team_image = itemView.findViewById(R.id.statistics_season_club_image);
        }
    }
}