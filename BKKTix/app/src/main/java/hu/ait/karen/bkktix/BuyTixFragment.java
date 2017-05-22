package hu.ait.karen.bkktix;

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

import hu.ait.karen.bkktix.data.TicketType;


public class BuyTixFragment extends Fragment {

    public static final String TAG = "BuyTixFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buy_tix, container, false);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.ticketTypeSpinner);
        final EditText etUserName = (EditText) rootView.findViewById(R.id.etUserName);
        final EditText etCardNumber = (EditText) rootView.findViewById(R.id.etCardNumber);
        final EditText etSecurityCode = (EditText) rootView.findViewById(R.id.etSecurityCode);
        final EditText etAddress1 = (EditText) rootView.findViewById(R.id.etAddress1);
        final EditText etCityState = (EditText) rootView.findViewById(R.id.etCityState);
        final EditText etCountry = (EditText) rootView.findViewById(R.id.etCountry);
        final NumberPicker numberPicker = (NumberPicker) rootView.findViewById(R.id.npTicketAmount);
        Button btnBuyTix = (Button) rootView.findViewById(R.id.btnBuy);

        numberPicker.setMinValue(0);
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
                String userName = etUserName.getText().toString();
                String creditCardNumber = etCardNumber.getText().toString();
                String securityCode = etSecurityCode.getText().toString();
                String address1 = etAddress1.getText().toString();
                String cityState = etCityState.getText().toString();
                String country = etCountry.getText().toString();

                ((MainActivity) getActivity()).verifyPurchase(
                        spinnerTicketTypePosition,
                        numberOfTickets,
                        userName,
                        creditCardNumber,
                        securityCode,
                        address1,
                        cityState,
                        country);
            }
        });



//
//        Button btnBuy20Min = (Button) rootView.findViewById(R.id.btnBuy20Min);
//        btnBuy20Min.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity) getActivity()).addNewTicket(TicketType._20_MINUTES);
//            }
//        });
//
//        Button btnBuy60Min = (Button) rootView.findViewById(R.id.btnBuy60Min);
//        btnBuy60Min.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity) getActivity()).addNewTicket(TicketType._60_MINUTES);
//            }
//        });
//
//        Button btnBuy120Min = (Button) rootView.findViewById(R.id.btnBuy120Min);
//        btnBuy120Min.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity) getActivity()).addNewTicket(TicketType._120_MINUTES);
//            }
//        });


        return rootView;
    }
}
