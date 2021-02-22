package com.example.employeetracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.employeetracker.fragments.EmployeeListFragment;
import com.example.employeetracker.helpers.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner sortSpinner;
    Spinner descSpinner;
    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sortSpinner = findViewById(R.id.sort_spinner);
        descSpinner = findViewById(R.id.desc_spinner);
        sortSpinner.setOnItemSelectedListener(this);
        descSpinner.setOnItemSelectedListener(this);

        dbh = DatabaseHelper.getInstance(this);

        //Uncomment these to add Ben and Jim to the database on start up. -----
        //dbh.deleteAll();

//        Cursor cursor = dbh.getAllEmployees(DatabaseHelper.COLUMN_FIRSTNAME, " ASC ");
//        //if the cursor has data then this does not need to be added.
//        if(cursor.getCount() == 0)
//        {
//            //Date is year-month-day
//            dbh.insertEmployee("ben", "tolman", 3, "2016-10-01 00:11:22", "Active", false, 0);
//            dbh.insertEmployee("Jim", "Jones", 3, "2016-10-01 00:11:22", "Active", false, 0);
//        }

        //--------
    }

    void reloadList(){

        String sort = sortSpinner.getSelectedItem().toString();
        if (sort.equals("Sort by Status")) {
            sort = DatabaseHelper.COLUMN_EMPLOYEESTATUS;
        } else {
            sort = DatabaseHelper.COLUMN_EMPLOYEENUMBER;
        }

        String desc = descSpinner.getSelectedItem().toString();
        if (desc.equals("Ascending")) {
            desc = "ASC";
        } else {
            desc = "DESC";
        }

        getSupportFragmentManager().beginTransaction().replace(
                R.id.list_fragment_holder,
                EmployeeListFragment.newInstance(sort, desc)
        ).commit();
    }

    //Need to know when returning to reload the list.
    @Override
    protected void onResume() {
        super.onResume();
        reloadList();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        reloadList();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String id = item.toString();
        Intent intent;

        switch(id){
            case "Add":
                //Send this info to details activity and open according to extras.
                intent = new Intent(this, com.example.employeetracker.DetailsActivity.class);
                intent.putExtra("DETAILS_CHOICE", "Add");
                intent.putExtra("NUMBER", -1);
                startActivity(intent);
                break;

            case "Settings":
                //Opens the preferences activity holding the preference fragment.
                intent = new Intent(this, com.example.employeetracker.PreferencesActivity.class);
                startActivity(intent);
                break;

            default:
                return false;
        }
        return false;
    }
}