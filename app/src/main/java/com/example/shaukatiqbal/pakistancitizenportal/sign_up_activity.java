package com.example.shaukatiqbal.pakistancitizenportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class sign_up_activity extends AppCompatActivity {
    EditText _cnic, _name, _email, _password, _phone, _city, _passportNo;
    String cnic, name, email, password, phone, city, passportNo;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    ProgressDialog progress;
    final int back_pressed=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_form);
        _cnic = (EditText) findViewById(R.id.cnic);
        _name = (EditText) findViewById(R.id.name);
        _email = (EditText) findViewById(R.id.email);
        _password = (EditText) findViewById(R.id.password);
        _phone = (EditText) findViewById(R.id.phone);
        _city = (EditText) findViewById(R.id.city);
        _passportNo = (EditText) findViewById(R.id.passport);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    public void signUp(View view) {
        cnic = _cnic.getText().toString();
        name = _name.getText().toString();
        email = _email.getText().toString();
        password = _password.getText().toString();
        phone = _phone.getText().toString();
        city = _city.getText().toString();
        passportNo = _passportNo.getText().toString();
        if (!checkFields()) {
            if (!isCnicGood()) {
                return;
            }
            int index=email.indexOf("@");
            if (!(email.contains("@"))||(index+3>=email.length())||(email.contains(" "))) {
                _email.setError("Enter valid email");
                return;

            }
            if ((password.length() < 6)) {
                _password.setError("Password length should be more than 5");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            String type = "signUp";
            Authenticate authenticate = new Authenticate(this);
            try {

                String response = authenticate.execute(type, cnic, name, email, password, phone, city, passportNo).get();
                if (response.equals("success")) {
                    Intent intent = new Intent();
                    intent.putExtra("cnic",cnic);
                    intent.putExtra("password",password);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public boolean checkFields() {
        boolean error = false;
        if (cnic.equals("")) {
            error = true;
            _cnic.setError("Please enter cnic");
        }
        if (name.equals("")) {
            error = true;
            _name.setError("Please enter name");
        }
        if (email.equals("")) {
            error = true;
            _email.setError("Please enter email");
        }
        if (password.equals("")) {
            error = true;
            _password.setError("Please enter password");
        }
        if (phone.equals("")) {
            error = true;
            _phone.setError("Please enter phone number");
        }
        if (city.equals("")) {
            error = true;
            _city.setError("Please enter city");
        }
        return error;

    }
    @Override
    public void onBackPressed()
    {
        setResult(back_pressed);
        super.onBackPressed();
    }

    public boolean isCnicGood() {

        if (cnic.length() != 13) {
            _cnic.setError("Cnic must contain 13 digits");
            return false;
        } else {
            try {
                double a = Double.parseDouble(cnic);
            } catch (Exception ex) {
                _cnic.setError("Not valid try again");
                return false;
            }

        }
        return true;
    }
}
