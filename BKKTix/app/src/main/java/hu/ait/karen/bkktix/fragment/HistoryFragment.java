package hu.ait.karen.bkktix.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.ait.karen.bkktix.MainActivity;
import hu.ait.karen.bkktix.R;
import hu.ait.karen.bkktix.adapter.HistoryRecyclerAdapter;


public class HistoryFragment extends Fragment {

    public static final String TAG = "HistoryFragment";

    private HistoryRecyclerAdapter historyRecyclerAdapter;
    private RecyclerView recyclerHistory;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerHistory = (RecyclerView) rootView.findViewById(R.id.recyclerViewHistory);
        recyclerHistory.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerHistory.setLayoutManager(layoutManager);

        historyRecyclerAdapter = ((MainActivity) getActivity()).getHistoryRecyclerAdapter();
        recyclerHistory.setAdapter(historyRecyclerAdapter);

        return rootView;
    }


}
