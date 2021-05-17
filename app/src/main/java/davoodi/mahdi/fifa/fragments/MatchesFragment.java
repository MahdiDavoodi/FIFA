package davoodi.mahdi.fifa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Objects;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.adapters.MatchesResultsAdapter;
import davoodi.mahdi.fifa.components.Season;
import davoodi.mahdi.fifa.data.ResultsData;
import davoodi.mahdi.fifa.data.SeasonsData;
import davoodi.mahdi.fifa.preferences.AppPreferences;

public class MatchesFragment extends Fragment {
    SeasonsData seasonsData;
    AppPreferences preferences;
    Season season;
    RecyclerView resultsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initialize();
        return inflater.inflate(R.layout.fragment_matches, container, false);
    }

    private void initialize() {


        // Preferences.
        preferences = new AppPreferences(Objects.requireNonNull(getActivity()));

        // Database.
        seasonsData = new SeasonsData(getActivity());

        season = seasonsData.getSeason(preferences.getCurrentSeason());

        // Results List.
        if (season.getSeasonMatchesPlayed() != 0) {
            new Thread(this::showLeagueList);
        }
    }

    private void showLeagueList() {
        MatchesResultsAdapter adapter = new MatchesResultsAdapter(getActivity());
        resultsList.setAdapter(adapter);
        resultsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // Widgets In Fragment.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultsList = Objects.requireNonNull(getView()).findViewById(R.id.matchesResults);
    }
}