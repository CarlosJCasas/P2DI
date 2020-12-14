package com.example.p2di.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p2di.R;
import com.example.p2di.core.Planta;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    ItemClickListener clickListener;
    ItemLongClickListener itemLongClickListener;
    LayoutInflater mInflater;
    ArrayList<Planta> listaPlantas;

    public CustomAdapter(Context context, ArrayList<Planta> listaPlantas) {
        this.mInflater = LayoutInflater.from(context);
        this.listaPlantas = listaPlantas;
    }
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {

        holder.imagen.setImageResource(listaPlantas.get(position).getImagen());
        holder.nombreImagen.setText(listaPlantas.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return listaPlantas.size();
    }


    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imagen;
        TextView nombreImagen;

        ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imageCardView);
            nombreImagen = itemView.findViewById(R.id.nameCardView);
            itemView.setOnClickListener(this::onClick);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this::onLongClick);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemCLick(v, getAdapterPosition());
            }
        }


        @Override
        public boolean onLongClick(View v) {
            if (itemLongClickListener != null) {
                itemLongClickListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemCLick(View view, int position);
    }

    public void setLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
