package com.example.employeetracker.fragments;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.example.employeetracker.R;
import com.example.employeetracker.helpers.DatabaseHelper;

public class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        if (preference.getKey().equals("dialog_preference")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Delete All Data");
            builder.setMessage("Do you really want to delete all of the data?");
            builder.setIcon(R.mipmap.ic_launcher);

            builder.setPositiveButton("Ok", (dialog, which) -> {
                Toast.makeText(getContext(), "All data deleted.", Toast.LENGTH_SHORT).show();
                DatabaseHelper dbh = DatabaseHelper.getInstance(getContext());
                dbh.deleteAll();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show());

            builder.setCancelable(false);
            builder.show();
        }

        return super.onPreferenceTreeClick(preference);
    }
}