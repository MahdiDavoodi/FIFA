package davoodi.mahdi.oufa.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import davoodi.mahdi.oufa.R;
import davoodi.mahdi.oufa.adapters.ClubsAdapter;
import davoodi.mahdi.oufa.adapters.OwnersAdapter;
import davoodi.mahdi.oufa.adapters.SeasonResultsAdapter;

public class StatisticsPage extends AppCompatActivity {

    RecyclerView list;
    TextView description;
    View season, clubs, owners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_page);
        list = findViewById(R.id.statisticsList);
        description = findViewById(R.id.statisticsListDescriptionText);
        season = findViewById(R.id.sp_season_line);
        owners = findViewById(R.id.sp_owner_line);
        clubs = findViewById(R.id.sp_clubs_line);

        // Default.
        description.setText(getResources().getString(R.string.season_description));
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new SeasonResultsAdapter(this));
    }

    private void makeThemeTransparent() {
        season.setBackgroundColor(getResources().getColor(R.color.transparent, getTheme()));
        owners.setBackgroundColor(getResources().getColor(R.color.transparent, getTheme()));
        clubs.setBackgroundColor(getResources().getColor(R.color.transparent, getTheme()));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StatisticsPage.this, MainPage.class));
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
        finish();
    }

    public void seasonOnclick(View view) {
        makeThemeTransparent();
        season.setBackgroundColor(getResources().getColor(R.color.mainButtonColor, getTheme()));
        description.setText(getResources().getString(R.string.season_description));
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new SeasonResultsAdapter(this));
    }

    public void ownerOnClick(View view) {
        makeThemeTransparent();
        owners.setBackgroundColor(getResources().getColor(R.color.mainButtonColor, getTheme()));
        description.setText(getResources().getString(R.string.owner_description));
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new OwnersAdapter(this));
    }

    public void clubsOnClick(View view) {
        makeThemeTransparent();
        clubs.setBackgroundColor(getResources().getColor(R.color.mainButtonColor, getTheme()));
        description.setText(getResources().getString(R.string.club_description));
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new ClubsAdapter(this));
    }
}