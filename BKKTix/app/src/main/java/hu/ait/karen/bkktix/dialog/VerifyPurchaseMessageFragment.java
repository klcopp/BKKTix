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
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final int numberOfTickets = getArguments().getInt(MainActivity.KEY_NUMBER_TIX);
        int ticketTypePosition = getArguments().getInt(MainActivity.KEY_TYPE_POSITION);
        final TicketType ticketType = TicketType.fromOrdinal(ticketTypePosition);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());


        alertDialogBuilder.setTitle(R.string.sure);
        int minutesValid = MainActivity.getTicketTypeInteger(ticketType);
        alertDialogBuilder.setMessage(
                String.format(getString(R.string.sure_long), numberOfTickets, minutesValid));


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
