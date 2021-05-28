package davoodi.mahdi.oufa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import davoodi.mahdi.oufa.R;
import davoodi.mahdi.oufa.preferences.AppPreferences;

public class SlidesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slides_page);
    }

    // Ok OnClick.
    public void okOnClick(View view) {
        // Edit Preferences.
        AppPreferences preferences = new AppPreferences(this);
        if (!preferences.getLastSeen().equalsIgnoreCase("SlidesPage"))
            preferences.setLastSeen("SlidesPage");
        // Intent MainPage.
        startActivity(new Intent(SlidesPage.this, MainPage.class));
        overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }
}