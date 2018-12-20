package com.example.shaukatiqbal.pakistancitizenportal;

import android.os.AsyncTask;

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

public class backgroundReadComplaints extends AsyncTask<String , Void , String> {

    @Override
    protected String doInBackground(String... credentials) {


            String login_url="https://myfblinks.000webhostapp.com/readComplaints.php";

            try {
                URL url=new URL(login_url);
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream outputStream=connection.getOutputStream();
                OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                String credential_string=URLEncoder.encode("cnic","UTF-8")+"="+URLEncoder.encode(credentials[1],"UTF-8");
                bufferedWriter.write(credential_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
//for response
                InputStream inputStream=connection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                String response="",line;
                while ((line=bufferedReader.readLine())!=null){
                    response+=line;
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
}
