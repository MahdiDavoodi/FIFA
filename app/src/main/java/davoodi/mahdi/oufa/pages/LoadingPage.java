package davoodi.mahdi.oufa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import davoodi.mahdi.oufa.R;
import davoodi.mahdi.oufa.preferences.AppPreferences;

public class LoadingPage extends AppCompatActivity {
    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);
        new Thread(this::nextActivity).start();
    }

    public void nextActivity() {
        preferences = new AppPreferences(this);
        Intent intent;
        try {
            Thread.sleep(500);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            String lastSeen = preferences.getLastSeen();

            switch (lastSeen) {
                case "SlidesPage":
                    intent = new Intent(LoadingPage.this, MainPage.class);
                    break;
                case "CreateOwnersPage":
                    intent = new Intent(LoadingPage.this, SelectClubsPage.class);
                    break;
                case "SelectClubsPage":
                    intent = new Intent(LoadingPage.this, SlidesPage.class);
                    break;
                case "StartPage":
                    intent = new Intent(LoadingPage.this, CreateOwnersPage.class);
                    break;
                default:
                    // User did not visit this app before.
                    intent = new Intent(LoadingPage.this, StartPage.class);
                    break;
            }

            startActivity(intent);
            finish();
        }
    }
}