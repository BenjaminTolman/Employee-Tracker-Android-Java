package com.example.employeetracker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import com.example.employeetracker.helpers.DatabaseHelper;

public class EmployeeAdapter extends ResourceCursorAdapter {

    //Needs a constructor with context and Cursor
    public EmployeeAdapter(Context context, Cursor _cursor) {
        super(context, android.R.layout.simple_list_item_2, _cursor, 0);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        //This is how we get the text from simple list item 2. (ANDROID.R.id.text1 and 2)
        TextView tvTitle = view.findViewById(android.R.id.text1);
        TextView tvID = view.findViewById(android.R.id.text2);

        //Get the column index by title
        int titleIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTNAME);
        int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);

        String title = cursor.getString(titleIndex);
        String id = cursor.getString(idIndex);

        tvTitle.setText(title);
        tvID.setText(id);
    }
}