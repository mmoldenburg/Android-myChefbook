package com.example.mydemoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RezeptBookAktivity extends AppCompatActivity {

    ListView listView;


    ArrayList<RezeptModel> rezeptList;
    RezeptListenAdapter adapter = null;
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezept_book_aktivity);

        listView = (ListView) findViewById(R.id.lv_listViewRezept);



        dataBaseHelper = new DataBaseHelper(RezeptBookAktivity.this);
        rezeptList = dataBaseHelper.getRecipe();
        adapter = new RezeptListenAdapter(this, R.layout.rezeptliste_helper, rezeptList);
        listView.setAdapter(adapter);



//Rezept löschen aus database
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog alertDialog = new AlertDialog.Builder(RezeptBookAktivity.this).create();
                alertDialog.setTitle("Meine Rezepte");
                alertDialog.setMessage("Möchten Sie wirklich dieses Rezept löschen?");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "JA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        RezeptModel clickedRecipeModel = (RezeptModel) parent.getItemAtPosition(position);
                        dataBaseHelper.deleteOne(clickedRecipeModel);

                        Intent intent = new Intent(getApplicationContext(), RezeptBookAktivity.class);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), "Das Rezept wurde gelöscht!", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NEIN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();

                return true;
            }
        });

//Rezeptdetails anzeigen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), RezeptDetailActivity.class);
                intent.putExtra("Pos", position);
                startActivity(intent);

            }
        });

    }

    //convert from String to Array
    // used stringseperator
    public static String strSeparator = "__,__";

    public static String[] convertStringToArray(String str) {
        String[] arr = str.split(strSeparator);
        return arr;
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);


    }
}

