package hu.ait.karen.bkktix.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import hu.ait.karen.bkktix.MainActivity;
import hu.ait.karen.bkktix.R;


public class MessageFragment extends DialogFragment {

    private OnMessageFragmentAnswer onMessageFragmentAnswer = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnMessageFragmentAnswer) {
            onMessageFragmentAnswer = (OnMessageFragmentAnswer) context;
        } else {
            throw new RuntimeException(
                    "This Activity is not implementing the " +
                            "OnMessageFragmentAnswer interface");
            //TODO ^STRING
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        String message = getArguments().getString(MainActivity.KEY_MSG);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View dialogLayout = inflater.inflate(R.layout.layout_dialog,null);
//        alertDialogBuilder.setView(dialogLayout);


        alertDialogBuilder.setTitle(R.string.validate_ticket);
        alertDialogBuilder.setMessage(R.string.are_you_sure);
        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onMessageFragmentAnswer.onPositiveSelected();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onMessageFragmentAnswer.onNegativeSelected();
            }
        });


        return alertDialogBuilder.create();
    }
}
