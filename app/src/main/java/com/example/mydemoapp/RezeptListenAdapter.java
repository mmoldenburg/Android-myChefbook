package com.example.mydemoapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RezeptListenAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<RezeptModel> recipeList;

    public RezeptListenAdapter(Context context, int layout, ArrayList<RezeptModel> recipeList) {
        this.context = context;
        this.layout = layout;
        this.recipeList = recipeList;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.textView = (TextView) row.findViewById(R.id.tv_rezeptTitel);
            holder.imageView = (ImageView) row.findViewById(R.id.iv_foodImg);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        RezeptModel recipeModel = recipeList.get(position);

        holder.textView.setText(recipeModel.getmTitel());

        byte[] foodImage = recipeModel.getmImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
