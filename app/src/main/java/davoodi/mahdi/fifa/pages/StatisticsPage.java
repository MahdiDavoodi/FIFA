package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.adapters.ClubsAdapter;
import davoodi.mahdi.fifa.adapters.OwnersAdapter;
import davoodi.mahdi.fifa.adapters.SeasonResultsAdapter;

public class StatisticsPage extends AppCompatActivity {

    RecyclerView list;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_page);
        list = findViewById(R.id.statisticsList);
        description = findViewById(R.id.statisticsListDescriptionText);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StatisticsPage.this, MainPage.class));
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
        finish();
    }

    public void seasonOnclick(View view) {
        description.setText(getResources().getString(R.string.season_description));
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new SeasonResultsAdapter(this));
    }

    public void ownerOnClick(View view) {
        description.setText(getResources().getString(R.string.owner_description));
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new OwnersAdapter(this));
    }

    public void clubsOnClick(View view) {
        description.setText(getResources().getString(R.string.club_description));
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new ClubsAdapter(this));
    }
}