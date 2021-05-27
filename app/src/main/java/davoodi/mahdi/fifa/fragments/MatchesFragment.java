package davoodi.mahdi.fifa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.adapters.MatchesResultsAdapter;
import davoodi.mahdi.fifa.components.Season;
import davoodi.mahdi.fifa.data.FifaData;
import davoodi.mahdi.fifa.preferences.AppPreferences;


public class MatchesFragment extends Fragment {
    RecyclerView resultsList;
    Season season;
    FifaData fifaData;
    AppPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        preferences = new AppPreferences(Objects.requireNonNull(getActivity()));
        fifaData = new FifaData(getActivity());
        season = fifaData.getSeason(preferences.getCurrentSeason());

        // Inflate the layout.
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        // Create RecyclerView
        resultsList = view.findViewById(R.id.matchesResults);
        resultsList.setHasFixedSize(true);
        resultsList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        resultsList.setAdapter(new MatchesResultsAdapter(getActivity()));
        if (season.getSeasonMatchesPlayed() < 100 && season.getSeasonMatchesPlayed() > 1) {
            resultsList.scrollToPosition(season.getSeasonMatchesPlayed() - 1);
        }
        // I have to read data in LoadingPage to make this faster.(V1 final edition.)

        return view;
    }
}