package com.example.gunawan_prasetyo_uas;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.CardViewHolder> {
    private List<Product> products;
    private Context context;
    static MyDatabase db;

    public DataAdapter(List<Product> products,Context context){
        this.products = products;
        this.context = context;
    }

    public List<Product> getProduct(){
        return products;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        final String nama,image, desc;
        nama  = products.get(position).getNama();
        desc  = products.get(position).getDesc();
        image = products.get(position).getImage();
        final int id = products.get(position).getId();
        holder.tvNama.setText(nama);
        holder.tvDesc.setText(desc);
        Glide.with(context).load(image).into(holder.imgImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Informasi Produk");
                alertDialog.setMessage("Yakin Akan Menghapus " + id);
                alertDialog.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        db.dataDao().delete(new Product(id));

                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama;
        TextView tvDesc;
        ImageView imgImage;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.tx_nama);
            tvDesc = (TextView) itemView.findViewById(R.id.tx_desc);
            imgImage = (ImageView) itemView.findViewById(R.id.im_product);
        }
    }
}