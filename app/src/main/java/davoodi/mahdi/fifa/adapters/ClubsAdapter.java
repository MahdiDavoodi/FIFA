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
import davoodi.mahdi.fifa.data.RanksData;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ViewHolder> {

    Context context;
    RanksData ranksData;
    ArrayList<Club> clubs;

    public ClubsAdapter(Context context) {
        this.context = context;
        readData();
    }

    private void readData() {
        // Ranks.
        ranksData = new RanksData(context);
        // Clubs.
        clubs = ranksData.getAllRankedClubs();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.statistics_club_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Club club = clubs.get(position);

        // Team Image.
        holder.team_image.setImageResource(context.getResources().getIdentifier("club" + club.getClubID(),
                "drawable",
                context.getPackageName()));

        // Info.

        holder.team_name.setText(club.getClubName());
        holder.budget.setText(String.valueOf(club.getClubWealth() + "$"));
        holder.mt.setText(String.valueOf(club.getClubMT()));
        holder.tm.setText(String.valueOf(club.getClubTM()));
        holder.champions.setText(String.valueOf(club.getClubChampions()));
        holder.europe.setText(String.valueOf(club.getClubEurope()));
        holder.golden.setText(String.valueOf(club.getClubGolden()));
    }

    @Override
    public int getItemCount() {
        // Items number.
        return clubs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView team_name, mt, tm, champions, europe, golden, budget;
        ImageView team_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Widgets.
            mt = itemView.findViewById(R.id.statistics_club_mt);
            tm = itemView.findViewById(R.id.statistics_club_tm);
            champions = itemView.findViewById(R.id.statistics_club_champions);
            europe = itemView.findViewById(R.id.statistics_club_europe);
            golden = itemView.findViewById(R.id.statistics_club_golden);
            budget = itemView.findViewById(R.id.statistics_club_budget);
            team_name = itemView.findViewById(R.id.statistics_club_team_name);
            team_image = itemView.findViewById(R.id.statistics_club_image);
        }
    }
}