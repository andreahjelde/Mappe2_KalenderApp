package com.example.mappe2_s364756;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class MinDialog extends DialogFragment {
    private MittInterface callback;
    public interface MittInterface {
        public void onYesClick();
        public void onNoClick();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (MittInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Kallende klasse må implementere interfacet!");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new
                AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_Dialog)
                .setPositiveButton(R.string.btn_addEvent, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                callback.onYesClick();
                            }
                        })
                .setNegativeButton(R.string.btn_exit, new
                        DialogInterface.OnClickListener() {
                            public void onClick (DialogInterface dialog,int whichButton){
                                callback.onNoClick(); }
                        })
                .create();
    }
}





