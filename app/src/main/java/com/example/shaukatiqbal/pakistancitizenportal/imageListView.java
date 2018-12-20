package com.example.shaukatiqbal.pakistancitizenportal;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class imageListView extends ArrayAdapter<Bitmap> {
    Activity context;
    ArrayList<complain> complaintsList;
    ArrayList<Bitmap> qrCodes;

    public imageListView(Activity context,  ArrayList<Bitmap> qrCodesArray) {
        super(context, R.layout.complain_row,qrCodesArray);
        this.context=context;
        this.complaintsList=complaintsList;
        qrCodes=qrCodesArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listRowItem = inflater.inflate(R.layout.image_row,null,true);
        /*TextView detail=(TextView)listRowItem.findViewById(R.id.complaintDetail);
        TextView category=(TextView)listRowItem.findViewById(R.id.complainCategory);
        TextView department=(TextView)listRowItem.findViewById(R.id.complainDepartment);
        TextView roomNo=(TextView)listRowItem.findViewById(R.id.complainRoomNo);
        TextView status=(TextView)listRowItem.findViewById(R.id.complaintStatus);
        LinearLayout linearLayout=(LinearLayout)listRowItem.findViewById(R.id.rowLinearLayout);
        int random=(int)(Math.random()*3-0);
        detail.setText(complaintsList.get(position).detail);
        category.setText(complaintsList.get(position).category);
        department.setText(complaintsList.get(position).department);
        roomNo.setText(complaintsList.get(position).roomNo);
        status.setText(complaintsList.get(position).status);
*/
        ImageView imageView=(ImageView) listRowItem.findViewById(R.id.imageItem);
                imageView.setImageBitmap(qrCodes.get(position));
                return listRowItem;
                }

                }
