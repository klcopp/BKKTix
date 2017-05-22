package hu.ait.karen.bkktix.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import hu.ait.karen.bkktix.MainActivity;
import hu.ait.karen.bkktix.R;
import hu.ait.karen.bkktix.data.TicketType;


public class VerifyPurchaseMessageFragment extends DialogFragment {

    public static final String TAG = "VerifyPurchaseMessageFragment";
    private VerifyPurchaseMessageFragmentAnswer answer = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof VerifyPurchaseMessageFragmentAnswer) {
            answer = (VerifyPurchaseMessageFragmentAnswer) context;
        } else {
            throw new RuntimeException(
                    "This Activity is not implementing the VerifyPurchaseMessageFragmentAnswer" +
                            " interface");
            //TODO ^STRING
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final int numberOfTickets = getArguments().getInt(MainActivity.KEY_NUMBER_TIX);
        int ticketTypePosition = getArguments().getInt(MainActivity.KEY_TYPE_POSITION);
        final TicketType ticketType = TicketType.fromOrdinal(ticketTypePosition);
        Log.d("TAG", "ticket type: " + ticketType+ ", ticket number: "+numberOfTickets);

        String userName =  getArguments().getString(MainActivity.KEY_USERNAME);
        String creditCardNumber = getArguments().getString(MainActivity.KEY_CREDIT_CARD_NUMBER);
        String address1 = getArguments().getString(MainActivity.KEY_ADDRESS1);
        String cityState = getArguments().getString(MainActivity.KEY_CITY_STATE);
        String country = getArguments().getString(MainActivity.KEY_COUNTRY);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View dialogLayout = inflater.inflate(R.layout.layout_dialog,null);
//        alertDialogBuilder.setView(dialogLayout);
//
//        //TODO make a thing - layout- and try to see if the thing is working
//
//        TextView tv2 = (TextView) dialogLayout.findViewById(R.id.textView2);
//        tv2.setText("TV2 YO");


        alertDialogBuilder.setTitle("Are you sure?");
        int minutesValid =  MainActivity.getTicketTypeInteger(ticketType);
        alertDialogBuilder.setMessage(
                "Are you sure you want to buy " + numberOfTickets + " tickets? " +
                        "They will be valid for " + minutesValid +" minutes.\n\n"+
                        "Charge to:\n"+
                        userName + "\n"+
                        address1 + "\n"+
                        cityState + "\n"+
                        country + "\n");
//                String.format(getString(R.string.are_you_sure), minutesTillExpiration));


        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                answer.onPurchaseSelected(ticketType, numberOfTickets);
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                answer.onPurchaseCancelledSelected();
            }
        });


        return alertDialogBuilder.create();
    }
}
