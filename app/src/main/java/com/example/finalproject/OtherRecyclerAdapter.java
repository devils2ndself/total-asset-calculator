package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OtherRecyclerAdapter extends RecyclerView.Adapter<OtherRecyclerAdapter.ViewHolder> {

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    Context context;
    ArrayList<Asset> assets;
    private final ItemClickListener clickListener;

    public OtherRecyclerAdapter(Context context, ArrayList<Asset> assets, ItemClickListener itemClickListener) {
        this.context = context;
        this.assets = assets;
        this.clickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(context);
        View view = myInflater.inflate(R.layout.other_assets_recycler_row, parent,false);

        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(assets.get(position).name);
        holder.type.setText(assets.get(position).type);
        String total = context.getResources().getString(R.string.dollar_sign) + String.format("%.2f", assets.get(position).getTotal());
        holder.total.setText(total);
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    public Asset getItem(int id) { return assets.get(id); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView type;
        public TextView total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.other_assets_name);
            type = itemView.findViewById(R.id.other_assets_type);
            total = itemView.findViewById(R.id.other_assets_total);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }
}
