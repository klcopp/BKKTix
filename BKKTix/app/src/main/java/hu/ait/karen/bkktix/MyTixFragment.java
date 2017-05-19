package hu.ait.karen.bkktix;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import hu.ait.karen.bkktix.adapter.MyTixExpandableListAdapter;
import hu.ait.karen.bkktix.data.Ticket;


public class MyTixFragment extends Fragment {

    public static final String TAG = "MyTixFragment";

    MyTixExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    //TODO remove
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_tix, container, false);

        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExpandable);
        listAdapter = new MyTixExpandableListAdapter(this.getContext()/*, listDataHeader, listDataChild*/);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        //TODO Delete:
        addNewTicket(TicketType._20_MINUTES);
        addNewTicket(TicketType._120_MINUTES);


        return rootView;
    }

    public void addNewTicket(TicketType ticketType){
        Ticket newTicket = new Ticket(new Date(System.currentTimeMillis()));
        newTicket.setTicketType(ticketType);
        listAdapter.addChild(newTicket);
    }

}
