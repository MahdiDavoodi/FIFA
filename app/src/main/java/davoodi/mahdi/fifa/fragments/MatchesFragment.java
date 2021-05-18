package davoodi.mahdi.fifa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.adapters.MatchesResultsAdapter;


public class MatchesFragment extends Fragment {
    RecyclerView resultsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout.
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        new Thread(() -> createResultsList(view)).start();
        // I have to read data in LoadingPage to make this faster.(V1 final edition.)

        return view;
    }

    private void createResultsList(View view) {
        // Create RecyclerView
        resultsList = view.findViewById(R.id.matchesResults);
        resultsList.setHasFixedSize(true);
        resultsList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        resultsList.setAdapter(new MatchesResultsAdapter(getActivity()));
    }
}