package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import davoodi.mahdi.fifa.R;

public class SelectClubsPage extends AppCompatActivity {

    ImageButton nextButton, backButton, clubButton;
    TextView clubName, clubBudget, clubClass,
            firstOwnerName, secondOwnerName,
            firstOwnerClubsNumber, secondOwnerClubsNumber,
            firstOwnerClubs, secondOwnerClubs;


    private void initializeWidgets() {
        // Image Buttons.
        nextButton = findViewById(R.id.selectClubsNextButton);
        backButton = findViewById(R.id.selectClubsBackButton);
        clubButton = findViewById(R.id.selectClubsClubButton);

        // CardView TextViews.
        clubName = findViewById(R.id.selectClubsClubNameText);
        clubBudget = findViewById(R.id.selectClubsTransferBudgetText);
        clubClass = findViewById(R.id.selectClubsClassText);

        // TextViews.
        firstOwnerName = findViewById(R.id.selectClubsOwner1NameText);
        secondOwnerName = findViewById(R.id.selectClubsOwner2NameText);
        firstOwnerClubsNumber = findViewById(R.id.selectClubsCounterText1);
        secondOwnerClubsNumber = findViewById(R.id.selectClubsCounterText2);
        firstOwnerClubs = findViewById(R.id.selectClubsListText1);
        secondOwnerClubs = findViewById(R.id.selectClubsListText2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_clubs_page);
        initializeWidgets();
    }
}