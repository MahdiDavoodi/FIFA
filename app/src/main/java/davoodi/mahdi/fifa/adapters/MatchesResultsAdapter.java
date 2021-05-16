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

import davoodi.mahdi.fifa.R;

public class MatchesResultsAdapter extends RecyclerView.Adapter<MatchesResultsAdapter.ViewHolder> {

    Context context;

    public MatchesResultsAdapter(Context context) {
        this.context = context;
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
