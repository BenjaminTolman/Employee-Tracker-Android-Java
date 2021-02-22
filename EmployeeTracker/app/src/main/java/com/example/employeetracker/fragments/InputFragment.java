package com.example.employeetracker.fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.employeetracker.R;
import com.example.employeetracker.helpers.DatabaseHelper;
import com.example.employeetracker.helpers.DateFormatter;

import java.util.Calendar;

public class InputFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_ID = "ARG_ID";

    private DatabaseHelper dbh;
    private EditText firstName;
    private EditText lastName;
    private EditText eNumber;
    private EditText eStatus;
    private TextView hireDate;

    private int idNumber;
    private boolean isUpdating;
    private String rawDate;

    public static com.example.employeetracker.fragments.InputFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        com.example.employeetracker.fragments.InputFragment fragment = new com.example.employeetracker.fragments.InputFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.input_fragment, container, false);

        setHasOptionsMenu(true);

        Button saveButton = v.findViewById(R.id.button);
        saveButton.setOnClickListener(this);

        dbh = DatabaseHelper.getInstance(getContext());

        Bundle args = getArguments();
        assert args != null;
        idNumber = args.getInt(ARG_ID);

        firstName = v.findViewById(R.id.first_name);
        lastName = v.findViewById(R.id.last_name);
        eNumber = v.findViewById(R.id.employee_number);
        eStatus = v.findViewById(R.id.employee_status);
        hireDate = v.findViewById(R.id.hire_date_data_text);

        if(idNumber == -1){
            isUpdating = false;
            hireDate.setText(R.string.select_date);
        }

        else{
            isUpdating = true;

            try (Cursor cursor = dbh.getEmployeeByID(idNumber)) {
                while (cursor.moveToNext()) {

                    String fName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTNAME));
                    firstName.setText(fName);
                    String lName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LASTNAME));
                    lastName.setText(lName);
                    int indexOfTitle = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEENUMBER);
                    String eid = cursor.getString(indexOfTitle);
                    eNumber.setText(eid);
                    String hDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_HIREDATE));
                    DateFormatter df = new DateFormatter(getContext());
                    hireDate.setText(df.formatDate(hDate));
                    rawDate = hDate;
                    String eStat = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEESTATUS));
                    eStatus.setText(eStat);
                }
            }
        }

        return v;
    }

    @Override
    public void onClick(View v) {

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {

                    rawDate = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " 00:11:22";
                    DateFormatter df = new DateFormatter(getContext());
                    hireDate.setText(df.formatDate(rawDate));
                }, year, month, day);
        datePicker.show();
    }

    private void updateDatabase() {

        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        int eNum = -1;

        try {
            eNum = Integer.parseInt(eNumber.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String eSta = eStatus.getText().toString();
        String hireD = hireDate.getText().toString();

        //Check if updating then either update or create database entry.
        //Note that the date argument is rawData to avoid complications from
        //whatever date format is selected.

        if (!eSta.equals("") && !fName.equals("") && !lName.equals("") && eNum != -1 && !hireD.equals("Select A Date")) {

            if(!isUpdating)
            {
                dbh.insertEmployee(fName, lName, eNum, rawDate, eSta,false,0);
            }
            else
            {
                dbh.insertEmployee(fName, lName, eNum, rawDate, eSta,true,idNumber);
            }

            getActivity().finish();
        }

        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Incomplete");
            builder.setMessage("Please complete all form fields");
            builder.setIcon(R.mipmap.ic_launcher);

            builder.setPositiveButton("Ok", (dialog, which) -> {

            });

            builder.setCancelable(false);
            builder.show();
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.input_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String id = item.toString();

        if ("Save".equals(id)) {
            updateDatabase();
        }
        return false;
    }
}