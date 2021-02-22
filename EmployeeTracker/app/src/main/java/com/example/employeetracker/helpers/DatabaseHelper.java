package com.example.employeetracker.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Contract variables
    public static final String DATABASE_FILE = "database.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "employees";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_EMPLOYEENUMBER = "employeenumber";
    public static final String COLUMN_HIREDATE = "hiredate";
    public static final String COLUMN_EMPLOYEESTATUS = "employeestatus";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIRSTNAME + " TEXT, " +
            COLUMN_LASTNAME + " TEXT, " +
            COLUMN_EMPLOYEENUMBER + " INT, " +
            COLUMN_HIREDATE + " DATETIME, " +
            COLUMN_EMPLOYEESTATUS + " TEXT) ";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    public void deleteAll(){

        //database.execSQL("DROP TABLE IF EXISTS employees;");
        database.delete(TABLE_NAME, null, null);

        //This is a way to reset the auto increment ids if you delete all.
        database.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='employees';");
    }

    public void deleteEntry(int id){
        String deleteString = ("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id + ";");
        database.execSQL(deleteString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Instance of this class.
    private static com.example.employeetracker.helpers.DatabaseHelper instance = null;

    //Use singleton pattern for this class.
    public static DatabaseHelper getInstance(Context _context){
        if(instance == null)
        {
            //Construct new instance if it's null.
            instance = new com.example.employeetracker.helpers.DatabaseHelper(_context);
        }

        return instance;
    }

    private final SQLiteDatabase database;

    //This is the constructor for this class.
    private DatabaseHelper(Context _context){
        //Call super and give it context, filename, factory cursor and database version.
        super(_context, DATABASE_FILE, null, DATABASE_VERSION);

        //Once this executes it will cause onCreate(SQLiteDatabase db) to fire.
        database = getWritableDatabase();
    }

    public void insertEmployee(String _firstName, String _lastName, int _employeeNumber,
                               String _hireDate, String _employeeStatus, boolean updating, int actualID) {

        if(!updating)
        {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_FIRSTNAME, _firstName);
            cv.put(COLUMN_LASTNAME, _lastName);
            cv.put(COLUMN_EMPLOYEENUMBER, _employeeNumber);
            cv.put(COLUMN_HIREDATE, _hireDate);
            cv.put(COLUMN_EMPLOYEESTATUS, _employeeStatus);
            database.insert(TABLE_NAME, null, cv);
        }
        else
        {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_FIRSTNAME, _firstName);
            cv.put(COLUMN_LASTNAME, _lastName);
            cv.put(COLUMN_EMPLOYEENUMBER, _employeeNumber);
            cv.put(COLUMN_HIREDATE, _hireDate);
            cv.put(COLUMN_EMPLOYEESTATUS, _employeeStatus);
            String where = (COLUMN_ID + " = " + actualID + ";");
            database.update(TABLE_NAME, cv, where, null);
        }
    }

    public Cursor getAllEmployees(String orderString, String descOrderString){

        return database.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                orderString + " " + descOrderString + ";",
                null);
    }

    public Cursor getEmployeeByID(int id)
    {
        String selection = COLUMN_ID + " = " + id;
        return database.query(TABLE_NAME,
                null,
                selection,
                null,
                null,
                null,
                null,
                null);
    }
}
