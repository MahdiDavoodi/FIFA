package davoodi.mahdi.fifa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Transfer;
import davoodi.mahdi.fifa.data.TransfersData;


public class TransferHistoryAdapter extends RecyclerView.Adapter<TransferHistoryAdapter.ViewHolder> {

    Context context;
    TransfersData transfersData;
    ArrayList<Transfer> transfers;

    public TransferHistoryAdapter(Context context) {
        this.context = context;
        readData();
    }

    private void readData() {
        // Owners.
        transfersData = new TransfersData(context);
        transfers = transfersData.getAllTransfers();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transfer_history_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transfer transfer = transfers.get(position);

        // Images.
        holder.from.setImageResource(context.getResources().getIdentifier("club" + transfer.getFromClubID(),
                "drawable",
                context.getPackageName()));

        holder.to.setImageResource(context.getResources().getIdentifier("club" + transfer.getToClubID(),
                "drawable",
                context.getPackageName()));

        // Text.
        holder.playerName.setText(transfer.getPlayerName());
        holder.playerPrice.setText(context.getResources().getString(R.string.million_dollar, transfer.getPrice()));
    }

    @Override
    public int getItemCount() {
        // Items number.
        return transfers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView from, to;
        TextView playerName, playerPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Widgets.
            from = itemView.findViewById(R.id.th_row_fromImage);
            to = itemView.findViewById(R.id.th_row_toImage);
            playerName = itemView.findViewById(R.id.th_row_playerName);
            playerPrice = itemView.findViewById(R.id.th_row_playerPrice);
        }
    }
}