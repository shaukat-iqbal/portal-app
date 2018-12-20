package com.example.shaukatiqbal.pakistancitizenportal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

public class Database extends AsyncTask<String, Void, String> {

    private SQLiteDatabase db;

    public Database(SQLiteDatabase database) {
        db = database;
    }

    public void read(String tableName) {

        Cursor c = db.rawQuery("SELECT * FROM " + tableName, null);
        int nameIndex = c.getColumnIndex("cnic");
        int statusIndex = c.getColumnIndex("status");

        c.moveToFirst();
        Log.i("count", tableName + " has " + c.getCount() + "no. of lines");
        for (int i = 0; i < c.getCount(); i++) {

            String rName = c.getString(nameIndex);
            String rStatus = c.getString(statusIndex);
            c.moveToNext();
        }

    }

    public void addUser(String... credentials) {
    }

    public void executeQuery() {
    }


    public void createTable() {


        db.execSQL("CREATE TABLE IF NOT EXISTS user(cnic varchar(13) NOT NULL PRIMARY KEY, password varchar(20), status varchar(20))");

        db.execSQL("CREATE TABLE IF NOT EXISTS complaints(id int  PRIMARY KEY  , cnic varchar(20), category varchar(20),detail varchar(1000) ,Department varchar(30),RoomNo varchar(30),location varchar(1000),status varchar(30),feedback varchar(30))");

        Log.i("createTable", "createTable: ");
        read("user");
        read("complaints");
    }

    public void updateCurrentUser(String... params) {

    createTable();
        // write query to read user
        String cnic = params[1];

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE cnic like '" + cnic + "'", null);
        if ((cursor.getCount()) > 0) {
            db.execSQL("UPDATE user SET status = 'loggedIn' WHERE cnic like '" + cnic + "'");
            Log.i("if", "if statement: ");
        } else {
            db.execSQL("INSERT INTO user (cnic, password, status) VALUES ('" + params[1] + "' , '" + params[2] + "' , '" + params[3] + "')");
            Log.i("else", "else statement: ");

        }

    }



    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String cnic = params[1];
        if (type.equals("login")) {
            updateCurrentUser(params);
        } else if ((type.equals("total"))) {
            Cursor cursor = db.rawQuery("SELECT * FROM complaints WHERE cnic like '" + cnic + "'", null);
            return cursor.getCount() + "";
        } else if ((type.equals("inProgress"))) {
            Cursor cursor = db.rawQuery("SELECT * FROM complaints WHERE cnic like '" + cnic + "' and status like 'inProgress'", null);
            return cursor.getCount() + "";
        } else if ((type.equals("resolved"))) {
            Cursor cursor = db.rawQuery("SELECT * FROM complaints WHERE cnic like '" + cnic + "' and status like 'resolved'", null);
            return cursor.getCount() + "";
        } else if ((type.equals("positiveFeedback"))) {
            try {
                Cursor cursor = db.rawQuery("SELECT * FROM complaints WHERE cnic like '" + cnic + "' and status like 'resolved' and feedback like 'positive'", null);
                return cursor.getCount() + "";
            }catch (SQLiteException sqLiteException){

            }

        } else if ((type.equals("negativeFeedback"))) {
            try{Cursor cursor = db.rawQuery("SELECT * FROM complaints WHERE cnic like '" + cnic + "' and status like 'resolved' and feedback like 'negative'", null);
            return cursor.getCount() + "";}catch (SQLiteException sqLiteException){

        }
        } else if ((type.equals("pendingFeedback"))) {
            try{Cursor cursor = db.rawQuery("SELECT * FROM complaints WHERE cnic like '" + cnic + "' and status like 'resolved' and feedback like 'pending'", null);
            return cursor.getCount() + "";}catch (SQLiteException sqLiteException){

            }
        }
        return null;
    }
}
