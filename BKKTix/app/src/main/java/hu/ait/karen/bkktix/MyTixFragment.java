package hu.ait.karen.bkktix;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import hu.ait.karen.bkktix.adapter.MyTixExpandableListAdapter;
import hu.ait.karen.bkktix.data.Ticket;


public class MyTixFragment extends Fragment {

    public static final String TAG = "MyTixFragment";

    private MyTixExpandableListAdapter listAdapter;
    private ExpandableListView expListView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_tix, container, false);

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExpandable);
//        listAdapter = new MyTixExpandableListAdapter(this.getContext());
        listAdapter = ((MainActivity) getActivity()).getListAdapter();
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Ticket ticket = (Ticket) listAdapter.getChild(groupPosition, childPosition);

                ((MainActivity) getActivity()).showTicket(ticket, groupPosition, childPosition);

                //orig:
//                return false;
                return true;
            }
        });

        return rootView;
    }


}
