package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.adapters.SeasonResultsAdapter;
import davoodi.mahdi.fifa.components.Season;
import davoodi.mahdi.fifa.data.SeasonsData;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class StatisticsPage extends AppCompatActivity {

    RecyclerView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_page);
        list = findViewById(R.id.statisticsList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }

    public void seasonOnclick(View view) {
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new SeasonResultsAdapter(this));
    }

    public void ownerOnClick(View view) {
    }

    public void clubsOnClick(View view) {
    }
}