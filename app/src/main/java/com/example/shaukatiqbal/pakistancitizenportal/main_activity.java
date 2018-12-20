package com.example.shaukatiqbal.pakistancitizenportal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class main_activity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_MAKE_COMPLAIN = 302;
    public static String cnic="3720396087251";
    TextView total, resolved, inprogress, positive, negative, pending;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = openOrCreateDatabase("UserDataBase", MODE_PRIVATE, null);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

       // getCurrentUser();
        total = (TextView) findViewById(R.id.totalComplaints);
        total.setText(noOfComplaints());
        Toast.makeText(this, cnic, Toast.LENGTH_LONG).show();
        //Set all Text View's Text
        //setTexts();

    }

    public void setTexts() {
        total = (TextView) findViewById(R.id.totalComplaints);
        resolved = (TextView) findViewById(R.id.resolvedComlaints);
        inprogress = (TextView) findViewById(R.id.inProgressComplaints);
        positive = (TextView) findViewById(R.id.positiveFeedback);
        negative = (TextView) findViewById(R.id.negativeFeedback);
        pending = (TextView) findViewById(R.id.pendingFeedbacks);

        try {
            total.setText(new Database(db).execute("total", cnic).get());
            Log.i("Text1", "setTexts: ");
            resolved.setText(new Database(db).execute("resolved", cnic).get());
            Log.i("Text2", "setTexts: ");
            inprogress.setText(new Database(db).execute("inProgress", cnic).get());
            positive.setText(new Database(db).execute("positiveFeedback", cnic).get());
            negative.setText(new Database(db).execute("negativeFeedback", cnic).get());
            pending.setText(new Database(db).execute("pendingFeedback", cnic).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, make_complain_activity.class);
        startActivityForResult(intent, REQUEST_CODE_MAKE_COMPLAIN);

    }

    public void complaintsList(View v) {
        Intent intent = new Intent(this, ComplaintsList.class);
        startActivity(intent);
    }


    public void inProgressComplaints(View view) {

    }

    public void resolvedComplaints(View view) {

    }

    public String noOfComplaints() {

        try {
            String response = new Authenticate(this).execute("readComplaints", cnic).get();
            if (!(response.equals("Error"))) {
                if (!(response.equals("empty"))) {
                    String[] eachRow = response.split("\\}");
                    return eachRow.length+"";
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return"0";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String response="";
        if (requestCode == REQUEST_CODE_MAKE_COMPLAIN) {
             response = data.getStringExtra("response");
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
            int a=(Integer.parseInt(total.getText().toString()));
            a++;
            total.setText(a+"");
        }
        else if(requestCode==10){
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();

        }
    }

   /* public void storeComplaints() {
        db.execSQL("INSERT INTO complaints (cnic, category, detail,Department,RoomNo,location,status,feedback)" +
                " VALUES ('1' ,'3720396087251', 'Admin', 'it is first complain','BBA','206','Infront of stairs','inprogress','pending')");
    }*/

    public void getCurrentUser() {

        Cursor c = db.rawQuery("SELECT * FROM user where status like 'loggedIn'", null);

        if (c.getCount() > 0) {

            int cnicIndex = c.getColumnIndex("cnic");
            c.moveToFirst();
            cnic = c.getString(cnicIndex);
        }

        Log.i("type", cnic);
        if (!(cnic.equals(""))) {

        }

    }

}
