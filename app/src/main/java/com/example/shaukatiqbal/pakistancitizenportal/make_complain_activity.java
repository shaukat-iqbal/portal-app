package com.example.shaukatiqbal.pakistancitizenportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.concurrent.ExecutionException;

public class make_complain_activity extends AppCompatActivity  {
    EditText _roomNo,_details,_location;
    Button submit;
    Spinner _category,_department;
    String department="Computer Science";
    String category="Electrical";
    final String  type="makeComplain";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_complain_activity);
        _roomNo=(EditText)findViewById(R.id.roomNo);
        _details=(EditText)findViewById(R.id.detail);
        _location=(EditText)findViewById(R.id.location);
        _category=(Spinner)findViewById(R.id.category);
        _department=(Spinner)findViewById(R.id.department);

        _department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        _category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void makeComplain(View v)
    {
        String roomNo=_roomNo.getText().toString();
        String detail=_details.getText().toString();
        String location=_location.getText().toString();

        try {
            String response=new backgroundMakeComplaints().execute(type,main_activity.cnic,category,detail,department,roomNo,location,"In-Progress","Pending").get();
            Intent intent=new Intent(this,main_activity.class);
            intent.putExtra("response",response);
            setResult(RESULT_OK,intent);
            finish();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }





}
