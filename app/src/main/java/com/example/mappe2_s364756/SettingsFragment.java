package com.example.mappe2_s364756;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Calendar;


public class  SettingsFragment extends PreferenceFragmentCompat {
    private EditTextPreference timePreference; // Deklarer timePreference som en klassevariabel
    private EditTextPreference messagePreference; // Deklarer messagePreference som en klassevariabel
    private CheckBoxPreference notificationsPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        notificationsPreference = findPreference("notifications");
        messagePreference = findPreference("message");
        timePreference = findPreference("time");

        // Få tilgang til "notification" (checkbox) preference
        if (notificationsPreference != null) {
            // Legg til en lytter for endringer i "notifications" preference
            notificationsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean notificationsEnabled = (Boolean) newValue;
                    handleNotificationsChange(notificationsEnabled);
                    return true; // True for å lagre endringen i preferansene
                }
            });
        }

        // Få tilgang til "message" preference
        if (messagePreference != null) {
            // Legg til en lytter for klikk på "message" preference
            messagePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    return true;
                }
            });
        }

        // Få tilgang til "time" preference
        timePreference.setOnPreferenceClickListener(preference -> {
            if (preference.getKey().equals("time")) {
                Log.d("timePreference", "time: " );
                return true; // Consume the click event

            }
            return false;

        });
        timePreference.setOnPreferenceChangeListener((a,b)->{
            if(notificationsPreference.isChecked())
            {
                Log.d("71", "71 ");
                Log.d("71", "72 " +b);
                setPeriodisk();
            }




            return  true;
        });
}

    //__________________________Håndterer checkbox__________________________________________________
    private void handleNotificationsChange(boolean notificationsEnabled) {
        if (notificationsEnabled) {
            // Sjekk om SMS-tillatelsen er gitt
                // Hvis tillatelse er gitt, utfør handlingene
                Log.d("CHECKBOXES", "huket på");
                startService();
                sendBroadcast();

                //Den nye verdien til timePreference vil bare oppdateres hver gang setPeriodisk() kjøres på nytt.
            // Så får å oppdatere tiden må man huke av og på checkboxen
                setPeriodisk();

        } else {
            // Hvis "notificationsEnabled" er falsk, stopp tjeneste og periodisk
            Log.d("CHECKBOXES", "huket av");
            stoppService();
            stoppPeriodisk();
        }
    }


    //-------------------------Metoder for broadcast-----------------------------------------------

    public void startService() {
        Intent intent = new Intent(requireContext(), MinService.class);
        requireContext().startService(intent);
    }

    public void stoppService() {
        Intent i = new Intent(requireContext(), MinService.class);
        requireContext().stopService(i);
    }

    public void sendBroadcast() {
        Intent intent = new Intent("com.example.service.MITTSIGNAL");
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent);
    }

    public void setPeriodisk() {
        Intent intent = new Intent(requireContext(), SettPeriodiskService.class);
        requireContext().startService(intent);
    }

    public void stoppPeriodisk() {
        Intent i = new Intent(requireContext(), MinService.class);
        PendingIntent pintent = PendingIntent.getService(requireContext(), 0, i,
                PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarm = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        if (alarm != null) {
            alarm.cancel(pintent);
        }
    }

}

