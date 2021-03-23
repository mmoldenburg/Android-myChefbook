package com.example.mydemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    // Variablen
    public static String url = "https://www.chefkoch.de/";

    private Button mChefkoch, mRezepte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialsieren button
        init();

        //button chefkoch
        mChefkoch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

        mRezepte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RezeptBookAktivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Methoden
     */

    private void init() {
        mChefkoch = (Button) findViewById(R.id.btn_Cefkoch);
        mRezepte = (Button) findViewById(R.id.btn_Rezept);

    }
}