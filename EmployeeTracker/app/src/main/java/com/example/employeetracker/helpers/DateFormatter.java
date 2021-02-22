package com.example.employeetracker.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class DateFormatter {

    final Context mContext;
    public DateFormatter(Context context) {
        mContext = context;
    }

    public String formatDate(String date){

        //Shared Prefs Code.
        SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(mContext);
        String value=(mSharedPreference.getString("list_preference", ""));

        String[] rawDate = date.split("\\t|-|/|\\s");

        String newDate = "";

        if(value.equals("Date Style 1v")){
            newDate = rawDate[1] + " / " + rawDate[2] + " / " + rawDate[0];
        }
        if(value.equals("Date Style 2v")){
            newDate = (getMonthName(rawDate[1]) + " " + rawDate[2] + ", " + rawDate[0]);

        }
        if(value.equals("Date Style 3v")){
            newDate = rawDate[1] + "." + rawDate[2] + "." + rawDate[0];
        }
        if(value.equals("")){
            newDate = rawDate[1] + " / " + rawDate[2] + " / " + rawDate[0];
        }

        return newDate;
    }

    private String getMonthName(String monthNum){

        String monthName;

        switch (monthNum){
            case "1":
                monthName = "January";
                break;
            case "2":
                monthName = "February";
                break;
            case "3":
                monthName = "March";
                break;
            case "4":
                monthName = "April";
                break;
            case "5":
                monthName = "May";
                break;
            case "6":
                monthName = "June";
                break;
            case "7":
                monthName = "July";
                break;
            case "8":
                monthName = "August";
                break;
            case "9":
                monthName = "September";
                break;
            case "10":
                monthName = "October";
                break;
            case "11":
                monthName = "November";
                break;
            case "12":
                monthName = "December";
                break;
            default:
                monthName = "";
                Log.d("Month Name Error : ", "Was unable to parse monthNum : " + monthNum);
        }
        return monthName;
    }
}
