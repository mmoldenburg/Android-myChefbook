package com.example.mydemoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RezeptZutatenAdapter extends RecyclerView.Adapter<RezeptZutatenAdapter.ViewHolder> {

    Context context;
    String[] amount;
    String[] ingridient;

    public RezeptZutatenAdapter(Context context, String[] amount, String[] ingridient) {
        this.context = context;
        this.amount = amount;
        this.ingridient = ingridient;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.details_helper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.menge.setText(amount[position]);
        holder.zutat.setText(ingridient[position]);
    }

    @Override
    public int getItemCount() {
        return amount.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView menge, zutat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menge = itemView.findViewById(R.id.tv_amount);
            zutat = itemView.findViewById(R.id.tv_ingridient);
        }
    }
}
