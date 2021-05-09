package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import davoodi.mahdi.fifa.R;

public class CreateOwnersPage extends AppCompatActivity {

    Button saveButton;
    EditText nameEditText, passwordEditText, confirmPasswordEditText;
    int saveButtonStage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_owners_page);
        saveButton = findViewById(R.id.createOwnersSaveButton);
        nameEditText = findViewById(R.id.createOwnersNameEditText);
        passwordEditText = findViewById(R.id.createOwnersPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.createOwnersConfirmPasswordEditText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }

    // Save button onClick.
    public void saveButtonOnClick(View view) {
        if (informationIsOkay()) {
            if (saveButtonStage == 1) {

            } else if (saveButtonStage == 2) {

            }
        }
    }

    public boolean informationIsOkay() {
        String checkName = nameEditText.getText().toString().trim();
        String checkPassword = passwordEditText.getText().toString().trim();
        String checkConfirmPassword = confirmPasswordEditText.getText().toString().trim();
        if (checkName.isEmpty() || checkPassword.isEmpty()) {
            Toast.makeText(this, "Please input your information!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkPassword.equals(checkConfirmPassword)) {
            Toast.makeText(this, "Password does not match!", Toast.LENGTH_SHORT).show();
            confirmPasswordEditText.getText().clear();
            return false;
        } else return true;
    }
}
