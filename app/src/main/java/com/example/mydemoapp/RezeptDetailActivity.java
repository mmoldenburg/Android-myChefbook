package com.example.mydemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RezeptDetailActivity extends AppCompatActivity {

    private ImageView foodImage;
    private TextView title, timeWork, timeCook, timeTotal, preperation;
    private RecyclerView recyclerView;

    private ArrayList<RezeptModel> infoList;
    private DataBaseHelper dataBaseHelper;
    private RezeptZutatenAdapter adapter = null;

    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezept_detail);

        foodImage = (ImageView) findViewById(R.id.iv_foodImage);
        title = (TextView) findViewById(R.id.tv_title);
        timeWork = (TextView) findViewById(R.id.tv_timeWork);
        timeCook = (TextView) findViewById(R.id.tv_timeCook);
        timeTotal = (TextView) findViewById(R.id.tv_timeTotal);
        preperation = (TextView) findViewById(R.id.tv_prepaire);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("Pos");

        dataBaseHelper = new DataBaseHelper(RezeptDetailActivity.this);
        infoList = dataBaseHelper.getRecipe();

        //Helper
        byte[] picture = infoList.get(position).getmImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);

        String[] menge1 = convertStringToArray(infoList.get(position).getmMenge());
        String[] zutat1 = convertStringToArray(infoList.get(position).getmZutat());


        // Übergabe
        foodImage.setImageBitmap(bitmap);
        title.setText(infoList.get(position).getmTitel());
        timeWork.setText(infoList.get(position).getmVorZeit());
        timeCook.setText(infoList.get(position).getmKochZeit());
        timeTotal.setText(infoList.get(position).getmGesamtZeit());
        preperation.setText(infoList.get(position).getmZubereitung());

        RezeptZutatenAdapter adapterDetail = new RezeptZutatenAdapter(this, menge1, zutat1);
        recyclerView.setAdapter(adapterDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    //von String in Array
    // Stringseperator
    public static String strSeparator = "__,__";

    public static String[] convertStringToArray(String str) {
        String[] arr = str.split(strSeparator);
        return arr;
    }
}
