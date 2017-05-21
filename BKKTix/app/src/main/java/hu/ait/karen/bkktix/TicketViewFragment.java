package hu.ait.karen.bkktix;


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

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import hu.ait.karen.bkktix.data.Ticket;
import hu.ait.karen.bkktix.qr.Contents;
import hu.ait.karen.bkktix.qr.QRCodeEncoder;

import static hu.ait.karen.bkktix.qr.AesCbcWithIntegrity.*;


public class TicketViewFragment extends Fragment {

    public static final String TAG = "TicketViewFragment";
    private Ticket ticket;
    private int groupPosition;
    private int childPosition;

    private static String PASSWORD = "this passphrase will generate the key for a BKKTix ticket!";


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
            gcal.add(Calendar.SECOND, minutesToAdd);
            Date validUntil = gcal.getTime();

            tvValidatedOrNot.setText("Valid until: " + validUntil);

            CipherTextIvMac encryptedDate = encryptDate(validUntil);
            QRCodeEncoder qrCodeEncoder = makeQRCodeEncoder(encryptedDate.toString(), 500);
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

    private CipherTextIvMac encryptDate(Date validUntil) {

        CipherTextIvMac civ = null;

        try {
            SecretKeys key;
            String salt = saltString(generateSalt());
            key = generateKeyFromPassword(PASSWORD, salt);

            String dateToEncrypt = validUntil.toString();

            civ = encrypt(dateToEncrypt, key);

            //String decryptedDate = decryptString(civ, key);
            //textToEncrypt.equals(decryptedText);

        } catch (GeneralSecurityException | UnsupportedEncodingException e) {}

        return civ;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(ticket.getDateValidated() != null){
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


