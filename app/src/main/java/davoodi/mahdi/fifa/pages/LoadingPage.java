package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import davoodi.mahdi.fifa.R;

public class LoadingPage extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);
        new Thread(this::nextActivity).start();
    }

    public void nextActivity() {
        sharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE);
        Intent intent;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            if (sharedPreferences.contains("Visited")) {
                // User visited this app before.
                String lastSituationSaved = sharedPreferences.getString("Visited", "NOT-FOUND");

                if (lastSituationSaved.equals("CreateOwnersPage"))
                    intent = new Intent(LoadingPage.this, SelectClubsPage.class);
                else
                    intent = new Intent(LoadingPage.this, MainPage.class);
            } else
                // User did not visit this app before.(Preference is NULL or it does not have "Visited" key)
                intent = new Intent(LoadingPage.this, StartPage.class);

            startActivity(intent);
            finish();
        }
    }
}