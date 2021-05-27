package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Owner;
import davoodi.mahdi.fifa.data.FifaData;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class CreateOwnersPage extends AppCompatActivity {
    AppPreferences preferences;
    Button saveButton;
    EditText nameEditText, passwordEditText, confirmPasswordEditText;
    int saveButtonStage = 1;
    Owner owner1, owner2;
    TextView ownerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_owners_page);
        saveButton = findViewById(R.id.createOwnersSaveButton);
        nameEditText = findViewById(R.id.createOwnersNameEditText);
        passwordEditText = findViewById(R.id.createOwnersPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.createOwnersConfirmPasswordEditText);
        ownerText = findViewById(R.id.createOwnersText);
    }

    // Save button onClick.
    public void saveButtonOnClick(View view) {
        if (informationIsOkay()) {
            if (saveButtonStage == 1) {
                owner1 = new Owner(saveButtonStage,
                        nameEditText.getText().toString().trim(),
                        Owner.passwordHash(passwordEditText.getText().toString().trim()),
                        0,
                        0,
                        0,
                        0,
                        0);

                nameEditText.setText("");
                passwordEditText.setText("");
                confirmPasswordEditText.setText("");
                ownerText.setText(R.string.createOwnersSecondOwnerText);
                Toast.makeText(this, R.string.createOwnersToast3, Toast.LENGTH_SHORT).show();
                saveButtonStage++;
                saveButton.setText(R.string.done);
            } else if (saveButtonStage == 2) {
                owner2 = new Owner(saveButtonStage,
                        nameEditText.getText().toString().trim(),
                        Owner.passwordHash(passwordEditText.getText().toString().trim()),
                        0,
                        0,
                        0,
                        0,
                        0);
                saveOwnersData();
                startActivity(new Intent(CreateOwnersPage.this, SelectClubsPage.class));
                overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
                finish();
            }
        }
    }

    private void saveOwnersData() {
        FifaData fifaData = new FifaData(this);
        fifaData.insertOwner(owner1);
        fifaData.insertOwner(owner2);
        preferences = new AppPreferences(this);
        preferences.setLastSeen("CreateOwnersPage");
        Toast.makeText(this, R.string.createOwnersToast4, Toast.LENGTH_SHORT).show();
    }

    public boolean informationIsOkay() {
        String checkName = nameEditText.getText().toString().trim();
        String checkPassword = passwordEditText.getText().toString().trim();
        String checkConfirmPassword = confirmPasswordEditText.getText().toString().trim();
        if (checkName.isEmpty() || checkPassword.isEmpty()) {
            Toast.makeText(this, R.string.createOwnersToast1, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkPassword.equals(checkConfirmPassword)) {
            Toast.makeText(this, R.string.createOwnersToast2, Toast.LENGTH_SHORT).show();
            confirmPasswordEditText.getText().clear();
            return false;
        } else return true;
    }
}
