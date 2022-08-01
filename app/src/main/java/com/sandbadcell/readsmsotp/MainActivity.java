package com.sandbadcell.readsmsotp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_USER_CONSENT = 200;
    private SmsBroadcastReceiver smsBroadcastReceiver;
    EditText editTextPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPin = findViewById(R.id.editTextPin);
        startSmartUserConsent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                Log.d("SHAHPAR", "----------------- message = " + message);
                getOtpCodeFromMessage(message);
                String otp = getOtpCodeFromMessage(message);
                if (otp.length() != 0)
                    editTextPin.setText(otp);
                else
                    editTextPin.setText(message);

            }
        }
    }

    private String getOtpCodeFromMessage(String message) {
        Pattern otpPattern = Pattern.compile("\\b\\d{5}\\b");
        Matcher matcher = otpPattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    private void startSmartUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);
    }

    private void registerBroadcastReceiver() {

        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.setSmsListener(new SmsBroadcastReceiver.SmsListener() {
            @Override
            public void onReceivedSms(Intent intent) {
                Log.d("SANBADCELL", "-------------------- onReceivedSms Success");
                startActivityForResult(intent, REQ_USER_CONSENT);
            }
        });

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }
}