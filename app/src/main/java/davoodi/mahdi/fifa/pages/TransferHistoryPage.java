package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.adapters.TransferHistoryAdapter;

public class TransferHistoryPage extends AppCompatActivity {
    RecyclerView transfer_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_history_page);

        transfer_list = findViewById(R.id.th_list);
        transfer_list.setHasFixedSize(true);
        transfer_list.setLayoutManager(new LinearLayoutManager(this));
        transfer_list.setAdapter(new TransferHistoryAdapter(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }
}