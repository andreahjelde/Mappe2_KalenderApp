package com.example.mappe2_s364756;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceManager;

import java.util.Calendar;

public class SettPeriodiskService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SettPeriodiskService", "onStartCommand started");

        // Hent tid fra SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String time = preferences.getString("time", "");

        // Hvis tid ikke er angitt, avslutt tjenesten
        if (time.isEmpty()) {
            stopSelf();
            return START_NOT_STICKY;
        }

        Log.d("SettPeriodiskService", "Time from preferences: " + time);
        // Analyser tid fra preferansene
        String[] timeParts = time.split(":");
        if (timeParts.length != 2) {
            // Ugyldig format, avslutt tjenesten
            stopSelf();
            return START_NOT_STICKY;
        }

        // Hent time og minutt
        int hourOfDay = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Opprett kalender for dagens dato
        java.util.Calendar cal = Calendar.getInstance();

        // Sett klokkeslett for alarmen
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);

        // Opprett Intent for MinService
        Intent i = new Intent(this, MinService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Sett tjenesten til å kjøre en gang på det angitte klokkeslettet
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);

        return super.onStartCommand(intent, flags, startId);
    }
}