package hu.ait.karen.bkktix;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import hu.ait.karen.bkktix.adapter.MyTixExpandableListAdapter;
import hu.ait.karen.bkktix.data.Ticket;
import hu.ait.karen.bkktix.data.TicketType;


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


////        TODO delete. this works.
//        addNewTicket(TicketType._20_MINUTES);
//        addNewTicket(TicketType._60_MINUTES);
//        addNewTicket(TicketType._120_MINUTES);


        return rootView;
    }


//    //TODO maybe delete
//    public void addNewTicket(TicketType ticketType) {
//        Ticket newTicket = new Ticket(new Date(System.currentTimeMillis()));
//        newTicket.setTicketType(ticketType);
//        listAdapter.addChild(newTicket);
//    }

}
