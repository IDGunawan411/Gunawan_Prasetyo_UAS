package com.example.gunawan_prasetyo_uas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.CardViewHolder> {
    private List<DevGame> devGames;
    private Context context;
    static MyDatabase db;

    public DataAdapter(List<DevGame> devGames, Context context){
        this.devGames = devGames;
        this.context = context;
    }

    public List<DevGame> getProduct(){
        return devGames;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dev_game_layout, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return devGames.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaDev;
        TextView tvNamaGame;
        TextView tvAddedGame;
        ImageView imgImage;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaDev = (TextView) itemView.findViewById(R.id.tx_nama_dev);
            tvNamaGame = (TextView) itemView.findViewById(R.id.tx_nama_game);

            tvAddedGame = (TextView) itemView.findViewById(R.id.tx_added_game);
            imgImage = (ImageView) itemView.findViewById(R.id.im_game);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        final String dev,image, game, added;
        dev  = devGames.get(position).getDev();
        game  = devGames.get(position).getGame();
        added  = devGames.get(position).getAdded();
        image = devGames.get(position).getImage();

        final int id = devGames.get(position).getId();

        holder.tvNamaDev.setText(dev);
        holder.tvNamaGame.setText(game);
        holder.tvAddedGame.setText(added);
        Glide.with(context).load(image).into(holder.imgImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Informasi Game");
                alertDialog.setMessage("Yakin Akan Menghapus " + dev + "");
                alertDialog.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Delete_Pos(position);
                        Toast.makeText(v.getContext(),"Data" + dev + " Berhasil Dihapus",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

    }

    private void Delete_Pos(int position){

        MainActivity.db.dataDao().delete(devGames.get(position));
        devGames.remove(position); //hapus data
        notifyItemRemoved(position); //refresh data
        notifyDataSetChanged();

    }

}
