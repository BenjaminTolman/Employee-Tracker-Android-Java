package com.example.employeetracker.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.employeetracker.DetailsActivity;
import com.example.employeetracker.R;
import com.example.employeetracker.helpers.DatabaseHelper;
import com.example.employeetracker.helpers.DateFormatter;

public class DetailsFragment extends Fragment {

    public static final String ARG_ID = "ARG_ID";

    DatabaseHelper dbh;
    TextView firstName;
    TextView lastName;
    TextView eNumber;
    TextView hireDate;
    TextView eStatus;

    int idNumber;

    public static com.example.employeetracker.fragments.DetailsFragment newInstance(int employeeID) {

        Bundle args = new Bundle();
        args.putInt(ARG_ID, employeeID);
        com.example.employeetracker.fragments.DetailsFragment fragment = new com.example.employeetracker.fragments.DetailsFragment();
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
        View v = inflater.inflate(R.layout.details_fragment, container, false);

        setHasOptionsMenu(true);

        Bundle args = getArguments();
        assert args != null;
        idNumber = args.getInt(ARG_ID);

        firstName = v.findViewById(R.id.first_name_data);
        lastName = v.findViewById(R.id.last_name_data);
        eNumber = v.findViewById(R.id.employee_number_data);
        eStatus = v.findViewById(R.id.employment_status_data);
        hireDate = v.findViewById(R.id.hire_date_data);
        dbh = DatabaseHelper.getInstance(getContext());

        //Get the employee with the provided id passed in the bundle.

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
                String eStat = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEESTATUS));
                eStatus.setText(eStat);
            }
        }
        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.edit_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String id = item.toString();

        switch (id) {
            case "Delete": //Deletes from database by id number.

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete Employee");
                builder.setMessage("Do you really want to delete employee?");
                builder.setIcon(R.mipmap.ic_launcher);

                builder.setPositiveButton("Ok", (dialog, which) -> {
                    DatabaseHelper dbh = DatabaseHelper.getInstance(getContext());
                    dbh.deleteEntry(idNumber);
                    getActivity().finish();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show());

                builder.setCancelable(false);
                builder.show();

                break;

                case "Edit": //Opens input form with "updating" selected.
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("DETAILS_CHOICE", "Update");
                intent.putExtra("NUMBER", idNumber);
                startActivity(intent);
                this.getActivity().finish();

                break;

            default:
                //return false so that this is not consumed.
                return false;
        }
        return false;
    }
}
