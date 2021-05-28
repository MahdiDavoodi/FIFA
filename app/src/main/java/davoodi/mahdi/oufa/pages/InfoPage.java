package davoodi.mahdi.oufa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import davoodi.mahdi.oufa.R;

public class InfoPage extends AppCompatActivity {

    public static final String REPOSITORY = "https://github.com/MahdiDavoodi/OUFA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }

    // Github OnClick.
    public void openGithub(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(REPOSITORY)));
    }
}