package hu.ait.karen.bkktix.fragment;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hu.ait.karen.bkktix.R;
import hu.ait.karen.bkktix.camera.CameraSourcePreview;

public class QRReaderFragment extends Fragment {

    public static final int REQUEST_CODE_CAMERA_PERMISSION = 101;
    private TextView barcodeInfo;

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private CameraSourcePreview preview;

    public static final String TAG = "QRReaderFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qr_reader, container, false);

        barcodeInfo = (TextView) rootView.findViewById(R.id.codeInfo);
        preview = (CameraSourcePreview) rootView.findViewById(R.id.cameraSourcePreview);

        requestNeededPermission();



        return rootView;
    }


    private void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this.getContext(), "I need Camera", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_CAMERA_PERMISSION);
        } else {
            setupBarcodeDetector();
            setupCameraSource();
            startCameraSource();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this.getContext(), "CAMERA perm granted", Toast.LENGTH_SHORT).show();
                    setupBarcodeDetector();
                    setupCameraSource();
                    startCameraSource();
                } else {
                    Toast.makeText(this.getContext(), "CAMERA perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }





    @Override
    public void onResume() {
        super.onResume();
        startCameraSource();
    }


    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                preview.start(cameraSource);
            } catch (IOException e) {
                cameraSource.release();
                cameraSource = null;
            }
        }
    }


    private void setupBarcodeDetector() {
        barcodeDetector = new BarcodeDetector.Builder(this.getContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            String validity = "INVALID!";
                            Date validThruDate = null;
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us"));
                                validThruDate = sdf.parse(barcodes.valueAt(0).displayValue);
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                            if (validThruDate.getTime() > new Date(System.currentTimeMillis()).getTime())
                            {
                                validity = "VALID!";
                            }
                            barcodeInfo.setText(validity);
                        }
                    });
                }
            }
        });

        if (!barcodeDetector.isOperational()) {
            Log.w("TAG_QR", "Detector dependencies are not yet available.");
        }

    }

    private void setupCameraSource() {
        cameraSource = new CameraSource.Builder(this.getContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(15.0f)
                .setRequestedPreviewSize(640, 640)
                .build();
    }

    @Override
    public void onPause() {
        super.onPause();
        preview.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }
}
