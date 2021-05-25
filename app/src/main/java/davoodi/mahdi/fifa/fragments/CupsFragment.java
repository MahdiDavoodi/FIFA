package davoodi.mahdi.fifa.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import davoodi.mahdi.fifa.R;
import davoodi.mahdi.fifa.adapters.CupsListAdapter;

public class CupsFragment extends Fragment {

    RecyclerView cups_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cups, container, false);
        // Create RecyclerView
        cups_list = view.findViewById(R.id.cups_list);
        cups_list.setHasFixedSize(true);
        cups_list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        cups_list.setAdapter(new CupsListAdapter(view.getContext()));
        return view;
    }
}
