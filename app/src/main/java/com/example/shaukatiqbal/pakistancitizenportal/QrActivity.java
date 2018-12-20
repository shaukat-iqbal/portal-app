package com.example.shaukatiqbal.pakistancitizenportal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class QrActivity extends AppCompatActivity {
 ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        imageView= (ImageView) findViewById(R.id.qrImage);
        Bitmap bitmap=getIntent().getParcelableExtra("image");
    imageView.setImageBitmap(bitmap);
    }
    public void back(View v){

    }
}
