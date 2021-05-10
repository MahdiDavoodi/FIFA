package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Club;
import davoodi.mahdi.fifa.components.Owner;
import davoodi.mahdi.fifa.data.ClubsData;
import davoodi.mahdi.fifa.data.OwnersData;

public class SelectClubsPage extends AppCompatActivity {

    ImageButton nextButton, backButton, clubButton;
    TextView clubName, clubBudget, clubClass,
            firstOwnerName, secondOwnerName,
            firstOwnerClubsNumber, secondOwnerClubsNumber,
            firstOwnerClubs, secondOwnerClubs;

    ArrayList<Club> clubs, firstOwnerSelected, secondOwnerSelected;
    ArrayList<Owner> owners;

    private void refreshPage() {
        int firstOwnerSelectedSize = firstOwnerSelected.size();
        int secondOwnerSelectedSize = secondOwnerSelected.size();

        StringBuilder list_1 = new StringBuilder(),
                list_2 = new StringBuilder();

        for (int i = 0; i < firstOwnerSelectedSize; i++) {
            if (i == 0)
                list_1 = new StringBuilder(firstOwnerSelected.get(i).getClubName());
            else
                list_1.append(", ").append(firstOwnerSelected.get(i).getClubName());
        }

        for (int i = 0; i < secondOwnerSelectedSize; i++) {
            if (i == 0)
                list_2 = new StringBuilder(secondOwnerSelected.get(i).getClubName());
            else
                list_2.append(", ").append(secondOwnerSelected.get(i).getClubName());
        }

        firstOwnerClubsNumber.setText(firstOwnerSelectedSize + "/10");
        secondOwnerClubsNumber.setText(secondOwnerSelectedSize + "/10");
        firstOwnerClubs.setText(list_1.toString());
        secondOwnerClubs.setText(list_2.toString());
    }

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

        // Saved Data.
        ClubsData clubsData = new ClubsData(this);
        OwnersData ownersData = new OwnersData(this);
        clubs = clubsData.getAllClubs();
        owners = ownersData.getAllOwners();

        // Containers.
        firstOwnerSelected = new ArrayList<>();
        secondOwnerSelected = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_clubs_page);
        initializeWidgets();
        firstOwnerName.setText(owners.get(0).getOwnerName());
        secondOwnerName.setText(owners.get(1).getOwnerName());
        refreshPage();
    }

    // Back Image Button OnClick.
    public void backButtonOnClick(View view) {
    }

    // Next Image Button OnClick.
    public void nextButtonOnClick(View view) {
    }

    // Club Image Button OnClick.
    public void clubButtonOnClick(View view) {
    }
}