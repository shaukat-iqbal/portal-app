package com.example.shaukatiqbal.pakistancitizenportal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ComplaintsList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayList<Bitmap> qrCodes=new ArrayList<Bitmap>();
    CustomComplaintsList customList;
    ArrayList<complain> listOfComplaints = new ArrayList<complain>();
    TextView noComplainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_list);
        noComplainText = (TextView) findViewById(R.id.noComplaints);
        createListOfComplaints();
        customList = new CustomComplaintsList(this, listOfComplaints,qrCodes);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customList);
        listView.setOnItemClickListener(this);
    }

    public void createListOfComplaints() {
        try {
            String response = new backgroundReadComplaints().execute("readComplaints", main_activity.cnic).get();
            if (!(response.equals("Error"))) {
                if (!(response.equals("empty"))) {
                    String[] eachRow = response.split("\\}");
                    for (int i = 0; i < eachRow.length; i++) {
                            generateQrCode(eachRow[i]);
                        String[] columnValues = eachRow[i].split("\\>");

                        complain c = new complain(columnValues[0], columnValues[1], columnValues[2], columnValues[3], columnValues[4]);
                        listOfComplaints.add(c);
                    }
                } else {
                    noComplainText.setVisibility(View.VISIBLE);
                }

            }
            Toast.makeText(this, "No of rows " + listOfComplaints.size(), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void generateQrCode(String complaintString){


            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(complaintString, BarcodeFormat.QR_CODE, 200, 200);
                BarcodeEncoder encode = new BarcodeEncoder();
                Bitmap bitmap = encode.createBitmap(bitMatrix);
                qrCodes.add(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bitmap bitmap=qrCodes.get(position);
        if(bitmap!=null){
            Intent intent=new Intent(this,QrActivity.class);

            intent.putExtra("image",bitmap);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"No Bitmap",Toast.LENGTH_LONG).show();
        }



    }
}
