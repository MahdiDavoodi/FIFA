package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Club;
import davoodi.mahdi.fifa.components.Owner;
import davoodi.mahdi.fifa.data.ClubsData;
import davoodi.mahdi.fifa.data.OwnersData;

public class SelectClubsPage extends AppCompatActivity {

    ImageButton nextButton, backButton, clubButton;
    Button doneButton;
    TextView clubName, clubBudget, clubClass,
            firstOwnerName, secondOwnerName,
            firstOwnerClubsNumber, secondOwnerClubsNumber,
            firstOwnerClubs, secondOwnerClubs;

    ArrayList<Club> clubs, firstOwnerSelected, secondOwnerSelected;
    ArrayList<Owner> owners;
    Club currentClub;
    int currentIndex = 0, maxIndex;
    int turn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_clubs_page);
        initializeWidgets();
        firstOwnerName.setText(owners.get(0).getOwnerName());
        secondOwnerName.setText(owners.get(1).getOwnerName());
        firstOwnerName.setTextColor(getResources().getColor(R.color.mainBigTextColor, getTheme()));
        refreshPage();
        refreshCardView();
    }

    private void initializeWidgets() {
        // Image Buttons.
        nextButton = findViewById(R.id.selectClubsNextButton);
        backButton = findViewById(R.id.selectClubsBackButton);
        clubButton = findViewById(R.id.selectClubsClubButton);

        // Button.
        doneButton = findViewById(R.id.selectClubsDoneButton);

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

        // Read Saved Data.
        ClubsData clubsData = new ClubsData(this);
        OwnersData ownersData = new OwnersData(this);
        clubs = clubsData.getAllClubs();
        owners = ownersData.getAllOwners();

        // Containers.
        firstOwnerSelected = new ArrayList<>();
        secondOwnerSelected = new ArrayList<>();

        // Sort Clubs.
        clubs.sort((o1, o2) -> (int) (o2.getClubWealth() - o1.getClubWealth()));
    }

    private void refreshPage() {
        int first_size = firstOwnerSelected.size();
        int second_size = secondOwnerSelected.size();
        if (first_size == 10 && second_size == 10)
            doneButton.setVisibility(View.VISIBLE);

        maxIndex = clubs.size() - 2;

        StringBuilder list_1 = new StringBuilder(),
                list_2 = new StringBuilder();

        for (int i = 0; i < first_size; i++) {
            if (i == 0)
                list_1 = new StringBuilder(firstOwnerSelected.get(i).getClubName());
            else
                list_1.append(", ").append(firstOwnerSelected.get(i).getClubName());
        }

        for (int i = 0; i < second_size; i++) {
            if (i == 0)
                list_2 = new StringBuilder(secondOwnerSelected.get(i).getClubName());
            else
                list_2.append(", ").append(secondOwnerSelected.get(i).getClubName());
        }

        firstOwnerClubsNumber.setText(getString(R.string.selectClubsOfTenText, first_size));
        secondOwnerClubsNumber.setText(getString(R.string.selectClubsOfTenText, second_size));
        firstOwnerClubs.setText(list_1.toString());
        secondOwnerClubs.setText(list_2.toString());
    }

    private void refreshCardView() {
        currentClub = clubs.get(currentIndex);
        int imageRes = getResources().getIdentifier("club" + currentClub.getClubID(),
                "drawable",
                getPackageName());
        clubButton.setImageResource(imageRes);
        clubName.setText(currentClub.getClubName());
        clubBudget.setText(getString(R.string.selectClubsClubBudgetText, currentClub.getClubWealth()));
        clubClass.setText(getString(R.string.selectClubsClassText, currentClub.getClubClass()));
    }


    // Back Image Button OnClick.
    public void backButtonOnClick(View view) {
        if (currentIndex == 0)
            currentIndex = maxIndex;
        else
            currentIndex--;

        refreshCardView();
    }

    // Next Image Button OnClick.
    public void nextButtonOnClick(View view) {
        if (currentIndex == maxIndex)
            currentIndex = 0;
        else
            currentIndex++;
        refreshCardView();
    }

    // Club Image Button OnClick.
    public void clubButtonOnClick(View view) {
        if (firstOwnerSelected.size() < 10 || secondOwnerSelected.size() < 10) {
            if (currentIndex == maxIndex)
                currentIndex = --maxIndex;
            // If turn is 1, owner 1 should select and if it is 2, owner 2 should select.
            if (turn == 1) {
                firstOwnerSelected.add(currentClub);
                firstOwnerName.setTextColor(getResources().getColor(R.color.mainDescriptionTextColor, getTheme()));
                secondOwnerName.setTextColor(getResources().getColor(R.color.mainBigTextColor, getTheme()));
                Toast.makeText(this, "Its " + owners.get(1).getOwnerName()
                        + " 's turn!", Toast.LENGTH_SHORT).show();
                turn = 2;
            } else {
                secondOwnerSelected.add(currentClub);
                secondOwnerName.setTextColor(getResources().getColor(R.color.mainDescriptionTextColor, getTheme()));
                firstOwnerName.setTextColor(getResources().getColor(R.color.mainBigTextColor, getTheme()));
                Toast.makeText(this, "Its " + owners.get(0).getOwnerName()
                        + " 's turn!", Toast.LENGTH_SHORT).show();
                turn = 1;
            }
            clubs.remove(currentClub);
            refreshPage();
            refreshCardView();
        } else {
            Toast.makeText(this, "You are good to go!", Toast.LENGTH_SHORT).show();
        }
    }

    // Done Button OnClick.
    public void doneOnClick(View view) {
    }
}