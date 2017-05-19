package hu.ait.karen.bkktix;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hu.ait.karen.bkktix.data.TicketType;


public class BuyTixFragment extends Fragment {

    public static final String TAG = "BuyTixFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buy_tix, container, false);



        Button btnBuy20Min = (Button) rootView.findViewById(R.id.btnBuy20Min);
        btnBuy20Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addNewTicket(TicketType._20_MINUTES);
            }
        });

        Button btnBuy60Min = (Button) rootView.findViewById(R.id.btnBuy60Min);
        btnBuy60Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addNewTicket(TicketType._60_MINUTES);
            }
        });



        Button btnBuy120Min = (Button) rootView.findViewById(R.id.btnBuy120Min);
        btnBuy120Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addNewTicket(TicketType._120_MINUTES);
            }
        });



        return rootView;
    }
}
