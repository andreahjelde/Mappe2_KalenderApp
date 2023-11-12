package com.example.mappe2_s364756;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Calendar;


public class  SettingsFragment extends PreferenceFragmentCompat {
    private EditTextPreference timePreference; // Deklarer timePreference som en klassevariabel
    private EditTextPreference messagePreference; // Deklarer messagePreference som en klassevariabel

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);


        Preference notificationsPreference = findPreference("notifications");
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
        if (timePreference != null) {
            // Legg til en lytter for klikk på "time" preference
            timePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    // Check if the clicked preference is the "time" preference
                    if (preference.getKey().equals("time")) {
                        showTimePickerDialog();
                        return true; // Consume the click event
                    }
                    return false;
                }
            });
        }
}

    ///_________________________Dialogboks for å velge klokkeslett_________________________________

    private void showTimePickerDialog() {
        // Get the time from the EditTextPreference
        String time = timePreference.getText();

        // If EditTextPreference value is empty, use the current time
        if (time.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            // Analyze the time from preferences if available
            String[] timeParts = time.split(":");
            if (timeParts.length == 2) {
                currentHour = Integer.parseInt(timeParts[0]);
                currentMinute = Integer.parseInt(timeParts[1]);
            }

            // Create and show TimePickerDialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            // Update the time in preferences
                            String formattedTime = String.format("%02d:%02d", hourOfDay, minute);
                            timePreference.setText(formattedTime);
                        }
                    },
                    currentHour,
                    currentMinute,
                    DateFormat.is24HourFormat(getContext())
            );
            timePickerDialog.show();
        }
    }


    //__________________________Håndterer checkbox__________________________________________________
    private void handleNotificationsChange(boolean notificationsEnabled) {
        if (notificationsEnabled) {
            // Sjekk om SMS-tillatelsen er gitt
                // Hvis tillatelse er gitt, utfør handlingene
                Log.d("CHECKBOXES", "huket på");
                startService();
                sendBroadcast();
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

