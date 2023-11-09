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
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;


    private EditTextPreference timePreference; // Deklarer timePreference som en klassevariabel
    private EditTextPreference messagePreference; // Deklarer messagePreference som en klassevariabel


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        BroadcastReceiver myBroadcastReceiver = new MinBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.service.MITTSIGNAL");
        filter.addAction("com.example.service.MITTSIGNAL");
        requireActivity().registerReceiver(myBroadcastReceiver, filter);

        Preference telefonPreference = findPreference("telefon");

        Preference notificationsPreference = findPreference("notifications");
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
        messagePreference = findPreference("message");
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
        Preference timePreference = findPreference("time");
        if (timePreference != null) {
            // Legg til en lytter for klikk på "time" preference
            timePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    showTimePickerDialog();
                    return true;
                }
            });
        }
}



    ///________________________________________________________________________

    private void showTimePickerDialog() {
        // Hent nåværende tid fra preferansene eller bruk standard tid
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String time = preferences.getString("time", "");

        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Analyser tiden fra preferansene hvis den er tilgjengelig
        if (!time.isEmpty()) {
            String[] timeParts = time.split(":");
            if (timeParts.length == 2) {
                currentHour = Integer.parseInt(timeParts[0]);
                currentMinute = Integer.parseInt(timeParts[1]);
            }
        }

        // Opprett og vis TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Oppdater tiden i preferansene
                        String formattedTime = String.format("%02d:%02d", hourOfDay, minute);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("time", formattedTime);
                        editor.apply();

                        // Oppdater preference summary
                        if (timePreference != null) {
                            timePreference.setText(formattedTime);
                        }
                    }
                },
                currentHour,
                currentMinute,
                DateFormat.is24HourFormat(getContext())
        );
        timePickerDialog.show();
    }




    //______________________________________________________________________________________________
    // Metode for å håndtere endringer i "notifications" preference
    private void handleNotificationsChange(boolean notificationsEnabled) {
        if (notificationsEnabled) {

                // Tillatelse allerede gitt, send SMS og start tjenester
                Log.d("CHECKBOXES", "huket på");
                startService();
                sendBroadcast();
                setPeriodisk();

                // Sjekk om knappen er huket av (her antar jeg at 'send' er en knapp)

        } else {
            Log.d("CHECKBOXES", "huket av");
            stoppService();
            stoppPeriodisk();
        }
    }


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

