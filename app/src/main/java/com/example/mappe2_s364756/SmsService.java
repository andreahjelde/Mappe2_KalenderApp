package com.example.mappe2_s364756;

import android.app.IntentService;
import android.content.Intent;
import android.telephony.SmsManager;

public class SmsService extends IntentService {
    public SmsService() {
        super("SmsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String phoneNumber = intent.getStringExtra("phoneNumber");
            String message = intent.getStringExtra("message");

            if (phoneNumber != null && message != null) {
                // Use SmsManager to send the SMS
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            }
        }
    }
}