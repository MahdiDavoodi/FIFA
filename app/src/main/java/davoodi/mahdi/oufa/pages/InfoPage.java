package davoodi.mahdi.oufa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import davoodi.mahdi.oufa.R;

public class InfoPage extends AppCompatActivity {

    public static final String REPOSITORY = "https://github.com/MahdiDavoodi/OUFA";

    public static final String MYKET_VOTE = "myket://comment?id=davoodi.mahdi.oufa";

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

    // Myket OnClick.
    public void openMyket(View view) {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo("ir.mservices.market", 0);
            if (info != null)
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MYKET_VOTE)));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, getResources().getString(R.string.infoToast), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}