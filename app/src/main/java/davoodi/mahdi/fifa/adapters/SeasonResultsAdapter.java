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
import davoodi.mahdi.fifa.components.Rank;
import davoodi.mahdi.fifa.data.ClubsData;
import davoodi.mahdi.fifa.data.RanksData;

public class SeasonResultsAdapter extends RecyclerView.Adapter<SeasonResultsAdapter.ViewHolder> {

    Context context;
    ClubsData clubsData;
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
        clubsData = new ClubsData(context);
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
            if (count == 0) {
                holder.team_rank.setCardBackgroundColor(context.getResources().getColor(R.color.gold, context.getTheme()));
            } else if (count < 8) {
                holder.team_rank.setBackgroundColor(context.getResources().getColor(R.color.blue, context.getTheme()));
            } else if (count < 16) {
                holder.team_rank.setBackgroundColor(context.getResources().getColor(R.color.orange, context.getTheme()));
            } else {
                holder.team_rank.setBackgroundColor(context.getResources().getColor(R.color.gray, context.getTheme()));
            }

            // Team Image.
            holder.team_image.setImageResource(context.getResources().getIdentifier("club" + rank.getClubID(),
                    "drawable",
                    context.getPackageName()));

            // Rank Info.

            holder.team_name.setText(clubsData.getClubFromID(rank.getClubID()).getClubName());
            holder.played.setText(String.valueOf(rank.getMatchesPlayed()));
            holder.win.setText(String.valueOf(rank.getWin()));
            holder.loss.setText(String.valueOf(rank.getLoss()));
            holder.draw.setText(String.valueOf(rank.getDraw()));
            holder.gd.setText(String.valueOf(rank.getGoalDifference()));
            holder.points.setText(String.valueOf(rank.getPoints()));
        }
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