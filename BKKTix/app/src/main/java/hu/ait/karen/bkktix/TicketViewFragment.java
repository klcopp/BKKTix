package hu.ait.karen.bkktix;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import hu.ait.karen.bkktix.data.Ticket;
import hu.ait.karen.bkktix.data.TicketType;


public class TicketViewFragment extends Fragment {

    public static final String TAG = "TicketViewFragment";
    Ticket ticket;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ticket_view, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView ivQRCode = (ImageView) getView().findViewById(R.id.ivQRCode);
        TextView tvDate = (TextView) getView().findViewById(R.id.tvTicketDate);
        TextView tvTicketType = (TextView) getView().findViewById(R.id.tvTicketType);
        TextView tvValidatedOrNot = (TextView) getView().findViewById(R.id.tvValidatedOrNot);
        Button btnValidate = (Button) getView().findViewById(R.id.btnValidate);

        //TODO something with ivQRCode.
        //TODO I promise the validate button is there, it's just hidden. add onclicklistener
        // TODO                            - dialog:"do you really want to validate this ticket for ___ minutes?"
        //TODO                                   & if ok: add date validated
        //TODO                                   & send this ticket back to MainActivity etc.


        tvDate.setText(String.format(getString(R.string.purchased_at), ticket.getDatePurchased()));

        int minutesToAdd = 20;
        String ticketTypeString=getString(R.string._20_min_tix);
        switch (ticket.getTicketType()){
            case _60_MINUTES:
                ticketTypeString = getString(R.string._60_min_tix);
                minutesToAdd = 60;
                break;
            case _120_MINUTES:
                ticketTypeString = getString(R.string._120_min_tix);
                minutesToAdd = 120;
                break;
        }

        //TODO says 'Valid for 1 hour'. this is confusing; clarify that the ticket is not validated yet.
        tvTicketType.setText(ticketTypeString);
        if(ticket.getDateValidated() == null){
            tvValidatedOrNot.setText(R.string.not_validated);
        }
        else {
            Calendar gcal = new GregorianCalendar();
            gcal.setTime(ticket.getDateValidated());
            gcal.add(Calendar.SECOND, minutesToAdd);
            Date validUntil = gcal.getTime();


            //TODO check if this works? (validation doesn't work yet
            tvValidatedOrNot.setText("Valid until: "+ validUntil);
        }

    }

    public void sendTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}

