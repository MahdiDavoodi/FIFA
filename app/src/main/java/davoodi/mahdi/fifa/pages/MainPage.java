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

import com.google.android.material.bottomnavigation.BottomNavigationView;


import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.fragments.CupsFragment;
import davoodi.mahdi.fifa.fragments.MatchesFragment;
import davoodi.mahdi.fifa.fragments.TransferFragment;

public class MainPage extends AppCompatActivity {

    BottomNavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new MatchesFragment()).commit();
        initializeWidgets();
    }

    private void initializeWidgets() {
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
        } else {
            return super.onOptionsItemSelected(item);
        }
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

    // Matches Fragment Button listeners.
    public void statisticsOnClick(View view) {
        startActivity(new Intent(MainPage.this, StatisticsPage.class));
        overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
    }
}