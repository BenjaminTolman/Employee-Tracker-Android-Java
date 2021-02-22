package com.example.employeetracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.employeetracker.fragments.PreferenceFragment;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.preferences_fragment_holder,
                new PreferenceFragment()
        ).commit();
    }
}