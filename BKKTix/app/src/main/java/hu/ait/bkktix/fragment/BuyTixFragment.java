package hu.ait.karen.bkktix.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import hu.ait.karen.bkktix.MainActivity;
import hu.ait.karen.bkktix.R;
import hu.ait.karen.bkktix.data.TicketType;


public class BuyTixFragment extends Fragment {

    public static final String TAG = "BuyTixFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buy_tix, container, false);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.ticketTypeSpinner);
        final NumberPicker numberPicker = (NumberPicker) rootView.findViewById(R.id.npTicketAmount);
        Button btnBuyTix = (Button) rootView.findViewById(R.id.btnBuy);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(20);
        numberPicker.setValue(1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.ticket_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnBuyTix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int spinnerTicketTypePosition = spinner.getSelectedItemPosition();
                int numberOfTickets = numberPicker.getValue();

                ((MainActivity) getActivity()).verifyPurchase(
                        spinnerTicketTypePosition,
                        numberOfTickets);
            }
        });

        return rootView;
    }
}
