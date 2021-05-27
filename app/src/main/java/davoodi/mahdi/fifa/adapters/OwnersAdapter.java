package davoodi.mahdi.fifa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Owner;
import davoodi.mahdi.fifa.data.FifaData;


public class OwnersAdapter extends RecyclerView.Adapter<OwnersAdapter.ViewHolder> {

    Context context;
    FifaData fifaData;
    ArrayList<Owner> owners;

    public OwnersAdapter(Context context) {
        this.context = context;
        readData();
    }

    private void readData() {
        // Owners.
        fifaData = new FifaData(context);
        owners = fifaData.getAllOwners();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.statistics_owner_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Owner owner = owners.get(position);

        if (owner.getOwnerID() == 1)
            holder.owner_color.setCardBackgroundColor(context.getResources().getColor(R.color.blue, context.getTheme()));
        else if (owner.getOwnerID() == 2)
            holder.owner_color.setCardBackgroundColor(context.getResources().getColor(R.color.red, context.getTheme()));

        holder.owner_name.setText(owner.getOwnerName());
        holder.win.setText(String.valueOf(owner.getOwnerTotalWin()));
        holder.loss.setText(String.valueOf(owner.getOwnerTotalLoss()));
        holder.draw.setText(String.valueOf(owner.getOwnerTotalDraw()));
        holder.cups.setText(String.valueOf(owner.getOwnerTotalCups()));
    }

    @Override
    public int getItemCount() {
        // Items number.
        return owners.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView owner_name, win, loss, draw, cups;
        CardView owner_color;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Widgets.
            owner_color = itemView.findViewById(R.id.statistics_owner_color);
            win = itemView.findViewById(R.id.statistics_owner_win);
            loss = itemView.findViewById(R.id.statistics_owner_loss);
            draw = itemView.findViewById(R.id.statistics_owner_draw);
            cups = itemView.findViewById(R.id.statistics_owner_cups);
            owner_name = itemView.findViewById(R.id.statistics_owner_name);
        }
    }
}