package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class SlidesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slides_page);
    }

    // Skip OnClick.
    public void skipOnClick(View view) {
        // Edit Preferences.
        new AppPreferences(this).setLastSeen("SlidesPage");
        // Intent MainPage.
        startActivity(new Intent(SlidesPage.this, MainPage.class));
        overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
    }
}