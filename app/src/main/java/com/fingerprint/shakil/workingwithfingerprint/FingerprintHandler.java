package com.fingerprint.shakil.workingwithfingerprint;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shakil on 6/28/2018.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;


    public FingerprintHandler(Context context) {
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There is an error :"+errString,false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("There is an failure",false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error:"+helpString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("You have authentication :"+result,true);
    }

    public void update(String s,boolean bol){
        TextView paralabel= (TextView)((Activity)context).findViewById(R.id.processTextView);
        ImageView image=(ImageView)((Activity)context).findViewById(R.id.fingerprint_image);

        paralabel.setText(s);

        if (bol==false){
            paralabel.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
        }
        else{
            paralabel.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            image.setImageResource(R.mipmap.done_icon);
        }
    }
}
