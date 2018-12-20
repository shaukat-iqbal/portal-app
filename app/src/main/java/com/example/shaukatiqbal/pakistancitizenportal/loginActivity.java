package com.example.shaukatiqbal.pakistancitizenportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class loginActivity extends AppCompatActivity {
    EditText cni, pass;
    static String cnic;
    public static final int REQ_CODE = 123;
    SQLiteDatabase db;
    Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        cni = (EditText) findViewById(R.id.cnic);
        pass = (EditText) findViewById(R.id.password);
        db = openOrCreateDatabase("UserDataBase", MODE_PRIVATE, null);
        database = new Database(db);

      //getCurrentUser();


    }

    public void createAccount(View v) {
        Intent intent = new Intent(this, sign_up_activity.class);
        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {

            cni.setText(data.getStringExtra("cnic"));
            pass.setText(data.getStringExtra("password"));


        }
    }

    public void resetPassword(View v) {

    }

    public void signIn(View v) {
        String type = "login";
        final String STATUS = "loggedIn";
        cnic = cni.getText().toString();
        String password = pass.getText().toString();
        if (cnic.equals("")) {
            cni.setError("Enter Email");

        } else if (password.equals("")) {
            pass.setError("Enter Password");

        } else {


            String result;

            try {

                result = new backgroundLogin(this).execute(type, cnic, password).get();
                if (result.equals("success")) {

                    new Database(db).execute(type, cnic, password, STATUS);
                    goToMainActivity();


                } else {
                    Toast.makeText(this, "Cnic or password is incorrect or check Internet Connection", Toast.LENGTH_LONG).show();

                    cni.requestFocus();

                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, main_activity.class);
        intent.putExtra("CurrentUser", cnic);
        Log.i("hey", "goToMainActivity: ");
        startActivity(intent);
        finish();

    }


    public void getCurrentUser() {

        Cursor c = db.rawQuery("SELECT * FROM user where status like 'loggedIn'", null);

        if (c.getCount() > 0) {

            int cnicIndex = c.getColumnIndex("cnic");
            c.moveToFirst();
            cnic = c.getString(cnicIndex);
        }

        Log.i("type", cnic);
        if (!(cnic.equals(""))) {
            goToMainActivity();
        }

    }

    public class backgroundLogin extends AsyncTask<String, Void, String> {
        Context context;

        public backgroundLogin(Activity context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... credentials) {
            String login_url = "https://myfblinks.000webhostapp.com/login.php";

            try {
                URL url = new URL(login_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream outputStream = connection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String credential_string = URLEncoder.encode("cnic", "UTF-8") + "=" + URLEncoder.encode(credentials[1], "UTF-8") +
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(credentials[2], "UTF-8");
                bufferedWriter.write(credential_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
//for response
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String response = "", line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Error";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

    }
}
