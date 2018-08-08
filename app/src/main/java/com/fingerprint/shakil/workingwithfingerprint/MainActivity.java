package com.fingerprint.shakil.workingwithfingerprint;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView paragraphLabel;
    ImageView finger_image;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paragraphLabel = (TextView) findViewById(R.id.processTextView);
        finger_image = (ImageView) findViewById(R.id.fingerprint_image);

        //TODO CHECK 1:Android version should be greater or equal to mmarshmello
        //TODO CHECK 2:Device must have fingerprint scanner
        //TODO CHECK 3:App must have the permission to use fingerprint
        //TODO CHECK 4:LockScreen should be locked with at least on lock
        //TODO CHECK 5:At least one fingerprint is registered

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager= (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            if (!fingerprintManager.isHardwareDetected()) {
                paragraphLabel.setText("Fingerprint scanner not detected into the device.");
            }
            else if(!keyguardManager.isKeyguardSecure()){
                paragraphLabel.setText("Add lock to your phone so that it may have minimum security.");
            }
            else if(!fingerprintManager.hasEnrolledFingerprints()){
                paragraphLabel.setText("No fingerprint is dectected into the device.");
            }
            else{
                paragraphLabel.setText("Place your finger into the scanner to scan the finger.");

                FingerprintHandler fingerprintHandler=new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager,null);
            }
        }
    }
}
