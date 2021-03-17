package com.example.mydemoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String REZEPT_TABLE = "REZEPT_TABLE";
    public static final String COLUMN_TITEL = "TITLE";
    public static final String COLUMN_MENGE = "AMOUNT";
    public static final String COLUMN_ARTIKEL = "INGRIEDENTS";
    public static final String COLUMN_ZUBEREITUNG = "PREPAIRE";
    public static final String COLUMN_ARBEITSZEIT = "TIMEWORK";
    public static final String COLUMN_KOCHZEIT = "TIMECOOK";
    public static final String COLUMN_GESAMTZEIT = "TIMETOTAL";
    public static final String COLUMN_IMAGE = "FOODIMMAGE";
    public static final String COLUMN_ID = "ID";


    public DataBaseHelper(@Nullable Context context) {

        super(context, "rezeptedemo1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + REZEPT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITEL + " TEXT," +
                COLUMN_MENGE + " TEXT," +
                COLUMN_ARTIKEL + " TEXT," +
                COLUMN_ZUBEREITUNG + " TEXT ," +
                COLUMN_ARBEITSZEIT + " TEXT," +
                COLUMN_KOCHZEIT + " TEXT," +
                COLUMN_GESAMTZEIT + " TEXT," +
                COLUMN_IMAGE + " BLOB)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(RezeptModel rezeptModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITEL, rezeptModel.getmTitel());
        cv.put(COLUMN_MENGE, rezeptModel.getmMenge());
        cv.put(COLUMN_ARTIKEL,rezeptModel.getmZutat());
        cv.put(COLUMN_ZUBEREITUNG, rezeptModel.getmZubereitung());
        cv.put(COLUMN_ARBEITSZEIT, rezeptModel.getmVorZeit());
        cv.put(COLUMN_KOCHZEIT, rezeptModel.getmKochZeit());
        cv.put(COLUMN_GESAMTZEIT, rezeptModel.getmGesamtZeit());
        cv.put(COLUMN_IMAGE, rezeptModel.getmImage());


        long insert = db.insert(REZEPT_TABLE, "null", cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }

    }

    public ArrayList<RezeptModel> getRecipe(){
        ArrayList<RezeptModel> returnList = new ArrayList<>();
        //get Data from Database
        String queryString = "SELECT * FROM " + REZEPT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int recipeID = cursor.getInt(0);
                String recipeTitle = cursor.getString(1);
                String recipeAmount = cursor.getString(2);
                String recipeIngridients = cursor.getString(3);
                String recipePrepaire = cursor.getString(4);
                String recipeTimeWork = cursor.getString(5);
                String recipeTimeCook = cursor.getString(6);
                String recipeTimeTotal = cursor.getString(7);
                byte[] recipeFoodPicture = cursor.getBlob(8);


                RezeptModel rezeptModel = new RezeptModel(recipeID, recipeTitle, recipeAmount,recipeIngridients, recipePrepaire, recipeTimeWork, recipeTimeCook, recipeTimeTotal, recipeFoodPicture);
                returnList.add(rezeptModel);

            }while (cursor.moveToNext());

        }else {
            //empty db
            cursor.close();
            db.close();
        }
        return returnList;
    }


    public boolean deleteOne(RezeptModel rezeptModel){
        //find the recipe in the database - if found, delete it and return true
        //if not exists in database - return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + REZEPT_TABLE + " WHERE " + COLUMN_ID + " = " + rezeptModel.getmId();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }
}
