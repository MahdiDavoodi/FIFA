package davoodi.mahdi.fifa.pages;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.fragments.CupsFragment;
import davoodi.mahdi.fifa.fragments.MatchesFragment;
import davoodi.mahdi.fifa.fragments.TransferFragment;

public class MainPage extends AppCompatActivity {

    BottomNavigationView navigationView;

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
}