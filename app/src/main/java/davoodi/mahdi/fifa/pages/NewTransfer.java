package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import davoodi.mahdi.fifa.R;

public class NewTransfer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transfer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }


    // From Club CardView.
    public void fromBackButton(View view) {
    }

    public void fromNextButton(View view) {
    }


    // To Club CardView.
    public void toBackButton(View view) {
    }

    public void toNextButton(View view) {
    }


    // Save Button.
    public void ntSaveButton(View view) {
    }
}