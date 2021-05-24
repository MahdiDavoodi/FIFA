package davoodi.mahdi.fifa.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.Objects;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.components.Owner;
import davoodi.mahdi.fifa.data.OwnersData;
import davoodi.mahdi.fifa.pages.NewTransfer;

public class TransferPasswordDialog extends AppCompatDialogFragment {

    OwnersData ownersData;
    Owner owner_1, owner_2;
    EditText owner_1_input, owner_2_input;
    String input_1 = "", input_2 = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ownersData = new OwnersData(getActivity());
        ArrayList<Owner> owners = ownersData.getAllOwners();
        owner_1 = owners.get(0);
        owner_2 = owners.get(1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.transfer_password_dialog, null);

        builder.setView(view)
                .setTitle(getActivity().getResources().getString(R.string.login))
                .setNegativeButton(getActivity().getResources().getString(R.string.cancel),
                        (dialog, which) -> {
                            // Do nothing.
                        })
                .setPositiveButton(getActivity().getResources().getString(R.string.done),
                        (dialog, which) -> checkPasswords())
                .setMessage(getActivity().getResources().getString(R.string.enter_password));

        owner_1_input = view.findViewById(R.id.tpd_owner_1_password);
        owner_2_input = view.findViewById(R.id.tpd_owner_2_password);

        owner_1_input.setHint(owner_1.getOwnerName() + "'s Password");
        owner_2_input.setHint(owner_2.getOwnerName() + "'s Password");


        return builder.create();
    }

    private void checkPasswords() {
        if (inputValid()) {
            long owner_1_hash = Owner.passwordHash(input_1);
            long owner_2_hash = Owner.passwordHash(input_2);
            if (owner_1.getOwnerPasswordHash() == owner_1_hash
                    && owner_2.getOwnerPasswordHash() == owner_2_hash) {
                startActivity(new Intent(getContext(), NewTransfer.class));
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
            } else
                Toast.makeText(getActivity(), "Passwords was not correct!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getActivity(), "Please input the passwords correctly!", Toast.LENGTH_SHORT).show();
    }

    private boolean inputValid() {
        input_1 = owner_1_input.getText().toString().trim();
        input_2 = owner_2_input.getText().toString().trim();
        return !input_1.isEmpty() && !input_2.isEmpty();
    }
}