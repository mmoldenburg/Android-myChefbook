package com.example.mydemoapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class JSoupChefkoch extends AsyncTask <String, Integer, String > {

    //JSoup-Ziel-HTML-Daten
    private final String url = "https://www.chefkoch.de/";
    private final String jsoupTitle = "main > article > div > h1";
    private final String jsoupPreperation = "main  > article.ds-or-3 div.ds-box";
    private final String jsoupAmount = ".td-left >span";
    private final String jsoupIngredients = ".td-right >span";
    private final String jsoupTimes = "small.ds-recipe-meta.rds-recipe-meta > span";
    private final String jsoupPicture = "amp-img";
    private final String jsoupRating = ".ds-rating-avg > span";
    private ProgressDialog progressDialog;
    private Context context;

    public JSoupChefkoch(WebViewActivity ctx){
        context = ctx;
    }

    @Override
    protected void onPreExecute() {

        //Dialog während des Downloads
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Das Rezept wird deinem myChefkochBook hinzugefügt");
        progressDialog.setMessage("Download läuft....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(0);
        progressDialog.setMax(100);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String ... urls) {

        //Variablen zur
        String currentUrl = (String) urls[0];
        String title;
        String prepaire;
        String amount;
        String ingredients;
        String timeWork;
        String timeCook;
        String timeTotal;
        String imageUrl;
        byte[] foodPicture = new byte[0];
        String[] amountStrings;
        String[] ingredientsString;
        String[] timesString;
        String[] selectImageUrlString;


        try {
            Document doc = Jsoup.connect(currentUrl).get();
            title = doc.select(jsoupTitle).text();
            prepaire = doc.select(jsoupPreperation).first().html().replaceAll("<br> ", "");
            Elements elementsAmount = doc.select(jsoupAmount);
            Elements elementsIngredients = doc.select(jsoupIngredients);
            Elements elementsTimes = doc.select(jsoupTimes);
            Elements elementsPicture = doc.getElementsByTag(jsoupPicture);

            //Initialisieren der StringArrays
            amountStrings = new String[(elementsAmount.size())];
            ingredientsString = new String[(elementsIngredients.size())];
            timesString = new String[(elementsTimes.size())];
            selectImageUrlString = new String[(elementsPicture.size())];

            //Elemente auslesen --> Menge, Zutaten, Zeiten, Image
            for (Element element1 : elementsAmount) {
                for (int i = 0; i < elementsAmount.size(); i++) {
                    amountStrings[i] = elementsAmount.get(i).text();
                }
            }
            for (Element element2 : elementsIngredients) {
                for (int i = 0; i < elementsIngredients.size(); i++) {
                    ingredientsString[i] = elementsIngredients.get(i).text();
                }
            }
            for (Element element3 : elementsTimes) {
                for (int i = 0; i < elementsTimes.size(); i++) {
                    timesString[i] = elementsTimes.get(i).text();
                }
            }
            for (Element element4 : elementsPicture) {
                for (int i = 0; i < elementsPicture.size(); i++) {
                    selectImageUrlString[i] = elementsPicture.get(i).absUrl("src");
                    ;
                }
                imageUrl = selectImageUrlString[7];
                foodPicture = getImageBytes(imageUrl);
            }

            amount = convertArrayToString(amountStrings);
            ingredients = convertArrayToString(ingredientsString);

            //erforderlicher try-Schritt, da unterschiedliche Einzelangaben (nicht immer alle Zeiten, teilweise nur 1 od. 2...)


            timeWork = timesString[0].substring(2);

            try {
                timeCook = timesString[1].substring(2);
            }catch (Exception e){
                timeCook = "";
            }
            try {
                timeTotal = timesString[2].substring(2);
            }catch (Exception e){
                timeTotal = "";
            }


            // Übergabe Parameter Jsoup an Rezept
            RezeptModel recipeModel = new RezeptModel(-1, title, amount, ingredients, prepaire, timeWork, timeCook, timeTotal, foodPicture);

            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            dataBaseHelper.addOne(recipeModel);

           /* Log.i("Ergebnis", "titel: " + recipeModel.getmTitel());
            Log.i("Ergebnis", "menge: " + recipeModel.getmMenge());
            Log.i("Ergebnis", "zutat: " + recipeModel.getmZutat());
            Log.i("Ergebnis", "zubereitung: " + recipeModel.getmZubereitung());
            Log.i("Ergebnis", "gesamt: " + recipeModel.getmGesamtZeit());*/


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Methodenteil


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgressStyle(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        Intent intent = new Intent(context, RezeptBookAktivity.class);
        context.startActivity(intent);

    }




// Stringseperator
public static String strSeparator = "__,__";

//von Array in String
public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i <array.length; i++) {
        str = str + array[i];
        // kein Komma am Ende des letzten Elements
        if(i < array.length-1){
        str = str + strSeparator;
        }
        }
        return str;
        }
//von String in Array
public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
        }

//Image in Byte-Array für SQLite
private static byte[] getImageBytes(String imageUrl) throws IOException
        {
        URL url = new URL(imageUrl);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream stream = url.openStream())
        {
        byte[] buffer = new byte[4096];

        while (true)
        {
        int bytesRead = stream.read(buffer);
        if (bytesRead < 0) { break; }
        output.write(buffer, 0, bytesRead);
        }
        }
        return output.toByteArray();
        }


}
