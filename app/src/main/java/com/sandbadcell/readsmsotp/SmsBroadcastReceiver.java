package com.sandbadcell.readsmsotp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private SmsListener smsListener;

    void setSmsListener(SmsListener smsListener) {
        this.smsListener = smsListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == SmsRetriever.SMS_RETRIEVED_ACTION) {
            Bundle extras = intent.getExtras();
            Status smsRetrieveStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
            switch (smsRetrieveStatus.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                    smsListener.onReceivedSms(consentIntent);
                    break;
                case CommonStatusCodes.TIMEOUT:
                    break;
            }
        }
    }

    public interface SmsListener {
        void onReceivedSms(Intent intent);
    }
}
