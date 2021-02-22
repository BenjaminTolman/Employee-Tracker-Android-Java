package com.example.employeetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.employeetracker.fragments.DetailsFragment;
import com.example.employeetracker.fragments.InputFragment;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String intentString = b.getString("DETAILS_CHOICE");
        int intentInt = b.getInt("NUMBER");

        if(intentString.equals("Add")){
            //Open as standard add employee.
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.details_fragment_holder,
                    InputFragment.newInstance(intentInt)
            ).commit();
        }

        if(intentString.equals("Details")){
            //Open the details fragment.
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.details_fragment_holder,
                    DetailsFragment.newInstance(intentInt)
            ).commit();
        }
        if(intentString.equals("Update")){
            //Open input fragment with intentInt that is not -1 to
            //trigger an update version.
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.details_fragment_holder,
                    InputFragment.newInstance(intentInt)
            ).commit();
        }
    }
}