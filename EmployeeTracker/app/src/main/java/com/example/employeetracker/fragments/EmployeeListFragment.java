package com.example.employeetracker.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.employeetracker.DetailsActivity;
import com.example.employeetracker.adapters.EmployeeAdapter;
import com.example.employeetracker.helpers.DatabaseHelper;

public class EmployeeListFragment extends ListFragment {

    public static final String SORT_ARGS = "SORT_ARGS";
    public static final String DESC_ARGS = "DESC_ARGS";

    public static com.example.employeetracker.fragments.EmployeeListFragment newInstance(String sortArgs, String descArgs) {

        Bundle args = new Bundle();
        args.putString(SORT_ARGS, sortArgs);
        args.putString(DESC_ARGS, descArgs);
        com.example.employeetracker.fragments.EmployeeListFragment fragment = new com.example.employeetracker.fragments.EmployeeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseHelper dbh = DatabaseHelper.getInstance(getContext());

        String sort = getArguments().getString(SORT_ARGS);
        String desc = getArguments().getString(DESC_ARGS);

        if(sort != null && desc != null)
        {
            Cursor cursor = dbh.getAllEmployees(sort, desc);
            EmployeeAdapter adapter = new EmployeeAdapter(
                    getActivity(),
                    cursor);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        TextView tv2 = v.findViewById(android.R.id.text2);
        String text2 = tv2.getText().toString();

        //Open activity with these select box selections passed as strings.
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("DETAILS_CHOICE", "Details");
        int employeeID = Integer.parseInt(text2);
        intent.putExtra("NUMBER", employeeID);
        startActivity(intent);
    }
}
