package davoodi.mahdi.fifa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.data.ResultsData;
import davoodi.mahdi.fifa.data.SeasonsData;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class MatchesFragment extends Fragment {
    SeasonsData seasonsData;
    ResultsData resultsData;
    AppPreferences preferences;
    int matchesCreated;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matches, container, false);
    }

    private void initWidgets() {

    }

    private void refresh() {

    }

    private boolean isThereAnyMatches() {
        return true;
    }
}
