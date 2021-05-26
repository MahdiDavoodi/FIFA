package davoodi.mahdi.fifa.pages;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Season;
import davoodi.mahdi.fifa.data.SeasonsData;
import davoodi.mahdi.fifa.dialogs.TransferPasswordDialog;
import davoodi.mahdi.fifa.fragments.CupsFragment;
import davoodi.mahdi.fifa.fragments.MatchesFragment;
import davoodi.mahdi.fifa.fragments.TransferFragment;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class MainPage extends AppCompatActivity {

    BottomNavigationView navigationView;
    Toolbar toolbar;
    Season season;
    SeasonsData seasonsData;
    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new MatchesFragment()).commit();
        initializeWidgets();
    }

    private void initializeWidgets() {
        preferences = new AppPreferences(this);
        seasonsData = new SeasonsData(this);
        season = seasonsData.getSeason(preferences.getCurrentSeason());
        navigationView = findViewById(R.id.mainBottomNavigation);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
    }

    // On Navigation Listener.
    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            item -> {
                Fragment selectedFragment;
                int itemID = item.getItemId();
                if (itemID == R.id.main_transfer)
                    selectedFragment = new TransferFragment();
                else if (itemID == R.id.main_cups)
                    selectedFragment = new CupsFragment();
                else
                    selectedFragment = new MatchesFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, selectedFragment).commit();
                return true;
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_more_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_info) {
            openInfoPage();
            return true;
        } else if (item.getItemId() == R.id.main_rules) {
            openRulesPage();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void openRulesPage() {
        startActivity(new Intent(MainPage.this, SlidesPage.class));
        overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
    }

    private void openInfoPage() {
        startActivity(new Intent(MainPage.this, InfoPage.class));
        overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
    }

    // Matches Fragment Button listeners.
    public void playOnClick(View view) {
        startActivity(new Intent(MainPage.this, PlayPage.class));
        overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
        finish();
    }


    // Transfer Fragment Buttons.
    public void statisticsOnClick(View view) {
        startActivity(new Intent(MainPage.this, StatisticsPage.class));
        overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
        finish();
    }

    public void transferNewOnClick(View view) {
        if (season.getSeasonMatchesPlayed() <= 20)
            showTransferPasswordDialog();
        else
            Toast.makeText(this, "Transfer window is not open!", Toast.LENGTH_LONG).show();
    }

    private void showTransferPasswordDialog() {
        TransferPasswordDialog dialog = new TransferPasswordDialog();
        dialog.show(getSupportFragmentManager(), "Transfer Password Dialog.");
    }

    public void thOnClick(View view) {
        startActivity(new Intent(MainPage.this, TransferHistoryPage.class));
        overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }
}