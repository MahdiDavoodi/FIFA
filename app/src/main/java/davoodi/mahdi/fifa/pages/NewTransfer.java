package davoodi.mahdi.fifa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Club;
import davoodi.mahdi.fifa.components.Transfer;
import davoodi.mahdi.fifa.data.ClubsData;
import davoodi.mahdi.fifa.data.TransfersData;

public class NewTransfer extends AppCompatActivity {

    // Player Info.
    EditText name_input, overall_input;

    // From Club CardView.
    TextView f_textView;
    ImageButton f_imageButton;

    // To Club CardView.
    TextView t_textView;
    ImageButton t_imageButton;

    // Components.
    ArrayList<Club> from_clubs, to_clubs;
    int from_index, to_index;
    Club to, from;
    int overall;
    String name;

    // Database.
    ClubsData clubsData;
    TransfersData transfersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transfer);
        initialize();
    }

    private void initialize() {

        // Database.
        transfersData = new TransfersData(this);
        clubsData = new ClubsData(this);

        // Components.
        from_clubs = clubsData.getAllClubs();
        to_clubs = clubsData.getAllClubs();

        from_clubs.sort((o1, o2) -> (int) (o2.getClubWealth() - o1.getClubWealth()));
        to_clubs.sort((o1, o2) -> (int) (o2.getClubWealth() - o1.getClubWealth()));

        to_clubs.remove(to_clubs.size() - 1);

        // Other Details.
        from_index = 0;
        to_index = 1;

        // Set UI.
        name_input = findViewById(R.id.nt_player_name);
        overall_input = findViewById(R.id.nt_player_overall);

        f_textView = findViewById(R.id.nt_fromClubName);
        f_imageButton = findViewById(R.id.nt_fromClubButton);

        t_textView = findViewById(R.id.nt_toClubName);
        t_imageButton = findViewById(R.id.nt_toClubButton);

        fromRefreshCard();
        toRefreshCard();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }


    // From Club CardView.
    private void fromRefreshCard() {
        from = from_clubs.get(from_index);

        // Set CardView.
        f_textView.setText(from.getClubName());
        f_imageButton.setImageResource(getResources().getIdentifier("club" + from.getClubID(),
                "drawable",
                getPackageName()));
    }

    public void fromBackButton(View view) {
        if (from_index == 0)
            from_index = from_clubs.size() - 1;
        else
            from_index--;

        fromRefreshCard();
    }

    public void fromNextButton(View view) {
        if (from_index == from_clubs.size() - 1)
            from_index = 0;
        else
            from_index++;

        fromRefreshCard();
    }


    // To Club CardView.

    private void toRefreshCard() {
        to = to_clubs.get(to_index);

        // Set CardView.
        t_textView.setText(to.getClubName());
        t_imageButton.setImageResource(getResources().getIdentifier("club" + to.getClubID(),
                "drawable",
                getPackageName()));
    }

    public void toBackButton(View view) {
        if (to_index == 0)
            to_index = to_clubs.size() - 1;
        else
            to_index--;

        toRefreshCard();
    }

    public void toNextButton(View view) {
        if (to_index == to_clubs.size() - 1)
            to_index = 0;
        else
            to_index++;

        toRefreshCard();
    }


    // Save Button.
    public void ntSaveButton(View view) {
        if (inputValid()) {
            long price = getPrice(overall);
            if (doesToClubHasItMoney(price)) {
                long price_with_tax = (price * 90) / 100;

                Transfer transfer = new Transfer(0,
                        from.getClubID(),
                        to.getClubID(),
                        name,
                        price);

                transfersData.insertTransfer(transfer);

                to.setClubWealth(to.getClubWealth() - price);
                from.setClubWealth(from.getClubWealth() + price);

                clubsData.updateClub(from);
                clubsData.updateClub(to);

                Toast.makeText(this, "Transfer saved. "
                        + (price - price_with_tax) + " million$ tax applied!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Destination club does not have enough money ("
                        + price + " million$).", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean inputValid() {
        name = name_input.getText().toString().trim();
        String overall_temp = overall_input.getText().toString().trim();

        if (name.isEmpty() || overall_temp.isEmpty()) {
            Toast.makeText(this, "Please input the player info!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            overall = Integer.parseInt(overall_temp);
            return true;
        }
    }

    private long getPrice(int overall) {
        if (overall <= 86)
            return overall;
        else if (overall <= 90)
            return overall * 2;
        else if (overall <= 95)
            return overall * 5;
        else return overall * 10;
    }

    private boolean doesToClubHasItMoney(long price) {
        return (to.getClubWealth() - price) > 0;
    }
}