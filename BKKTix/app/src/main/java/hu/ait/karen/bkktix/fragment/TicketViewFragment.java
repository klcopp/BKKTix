package hu.ait.karen.bkktix.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import hu.ait.karen.bkktix.MainActivity;
import hu.ait.karen.bkktix.R;
import hu.ait.karen.bkktix.data.Ticket;
import hu.ait.karen.bkktix.qr.Contents;
import hu.ait.karen.bkktix.qr.QRCodeEncoder;


public class TicketViewFragment extends Fragment {

    public static final String TAG = "TicketViewFragment";
    private Ticket ticket;
    private int groupPosition;
    private int childPosition;


    Button btnValidate;

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
        btnValidate = (Button) getView().findViewById(R.id.btnValidate);

        tvDate.setText(String.format(getString(R.string.purchased_at), ticket.getDatePurchased()));

        int minutesToAdd = 20;
        String ticketTypeString = getString(R.string._20_min_tix);
        switch (ticket.getTicketType()) {
            case _60_MINUTES:
                ticketTypeString = getString(R.string._60_min_tix);
                minutesToAdd = 60;
                break;
            case _120_MINUTES:
                ticketTypeString = getString(R.string._120_min_tix);
                minutesToAdd = 120;
                break;
        }

        tvTicketType.setText(ticketTypeString);
        if (ticket.getDateValidated() == null) {
            tvValidatedOrNot.setText(R.string.not_validated);
        } else {
            Calendar gcal = new GregorianCalendar();
            gcal.setTime(ticket.getDateValidated());
            gcal.add(Calendar.MINUTE, minutesToAdd);
            Date validUntil = gcal.getTime();

            tvValidatedOrNot.setText("Valid until: " + validUntil);

            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
            String QRData = sdf.format(validUntil);
            QRCodeEncoder qrCodeEncoder = makeQRCodeEncoder(QRData, 500);
            try {
                Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                ivQRCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        //not yet validated
//        if(ticket.getDateValidated() == null) {
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showValidateTicketDialog(
                        ticket.getTicketType(), groupPosition, childPosition, v);
            }
        });
//        }
//        // validated
//        else{
//            btnValidate.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (ticket.getDateValidated() != null) {
            btnValidate.setVisibility(View.GONE);
        }
    }

    private QRCodeEncoder makeQRCodeEncoder(String data, int size) {
        return new QRCodeEncoder(data, null,
                Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), size);
    }

    public void sendTicket(Ticket ticket, int groupPosition, int childPosition) {
        this.ticket = ticket;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
    }

}


