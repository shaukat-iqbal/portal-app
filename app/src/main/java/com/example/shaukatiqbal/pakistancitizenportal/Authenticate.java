package com.example.shaukatiqbal.pakistancitizenportal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Authenticate extends AsyncTask<String ,Void, String> {

     String type=null;
    Context context;

    Authenticate(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... credentials) {
         type=credentials[0];
        String response="";
      if(type.equals("login")){
        response= login(credentials);

      }else if(type.equals("signUp")){

          response= signUp(credentials);
      }else if(type.equals("makeComplain")){
          response=makeComplain(credentials);
      }else if(type.equals("readComplaints")){
          response=readComplaints(credentials);
      }

        return response;
    }

   /* @Override
    protected void onPostExecute(String response) {
        if(type.equals("login")){

        }
        Intent intent=new Intent(context,main_activity.class);
        context.startActivity(intent);

    }*/

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    public String login(String... credentials){

        String login_url="https://myfblinks.000webhostapp.com/login.php";

        try {
            URL url=new URL(login_url);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream=connection.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            String credential_string=URLEncoder.encode("cnic","UTF-8")+"="+URLEncoder.encode(credentials[1],"UTF-8")+
                    "&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(credentials[2],"UTF-8");
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
    public String signUp(String... credentials){

        String signUp_url="https://myfblinks.000webhostapp.com/signUp.php";

        try {
            URL url=new URL(signUp_url);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream=connection.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            String values=signUp_string_encoder(credentials);
            bufferedWriter.write(values);
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
         // I have changed it

        return "Error: something went wrong pease check your connection";
    }
    public String makeComplain(String... credentials){

        String signUp_url="https://myfblinks.000webhostapp.com/complain.php";

        try {
            URL url=new URL(signUp_url);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream=connection.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            String values=complain_string_encoder(credentials);
            bufferedWriter.write(values);
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

        return "Error: something went wrong pease check your connection";
    }
    public String readComplaints(String... credentials){

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
    public String signUp_string_encoder(String... credentials) throws UnsupportedEncodingException {

          String values= URLEncoder.encode("cnic","UTF-8")+"="+URLEncoder.encode(credentials[1],"UTF-8")+
            "&"+URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(credentials[2],"UTF-8")+
                  "&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(credentials[3],"UTF-8")+
                  "&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(credentials[4],"UTF-8")+
                  "&"+URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(credentials[5],"UTF-8")+
                  "&"+URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(credentials[6],"UTF-8")+
                  "&"+URLEncoder.encode("passportNo","UTF-8")+"="+URLEncoder.encode(credentials[7],"UTF-8");


          return values;
}
    public String complain_string_encoder(String... credentials) throws UnsupportedEncodingException {

        String values= URLEncoder.encode("cnic","UTF-8")+"="+URLEncoder.encode(credentials[1],"UTF-8")+
                "&"+URLEncoder.encode("category","UTF-8")+"="+URLEncoder.encode(credentials[2],"UTF-8")+
                "&"+URLEncoder.encode("detail","UTF-8")+"="+URLEncoder.encode(credentials[3],"UTF-8")+
                "&"+URLEncoder.encode("Department","UTF-8")+"="+URLEncoder.encode(credentials[4],"UTF-8")+
                "&"+URLEncoder.encode("RoomNo","UTF-8")+"="+URLEncoder.encode(credentials[5],"UTF-8")+
                "&"+URLEncoder.encode("location","UTF-8")+"="+URLEncoder.encode(credentials[6],"UTF-8")+
                "&"+URLEncoder.encode("status","UTF-8")+"="+URLEncoder.encode(credentials[7],"UTF-8")+
                "&"+URLEncoder.encode("feedback","UTF-8")+"="+URLEncoder.encode(credentials[8],"UTF-8");


        return values;
    }

   /* public String encoder(String... variables) throws UnsupportedEncodingException {

        String value=URLEncoder.encode(variables[1],"UTF-8")+"="+URLEncoder.encode(variables[2],"UTF-8");
        if(variables.length>2){
            for (int i = 3; i < variables.length; ) {
                if(variables[i+1]!=null)
            value+="&"+URLEncoder.encode(variables[i],"UTF-8")+"="+URLEncoder.encode(variables[i+1],"UTF-8");
            i+=2;
            }
        }
        return value;
    }*/

}
