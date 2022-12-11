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

public class StocksCryptoRecyclerAdapter extends RecyclerView.Adapter<StocksCryptoRecyclerAdapter.ViewHolder> {

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    Context context;
    ArrayList<Asset> assets;
    private final ItemClickListener clickListener;

    public StocksCryptoRecyclerAdapter(Context context, ArrayList<Asset> assets, ItemClickListener itemClickListener) {
        this.context = context;
        this.assets = assets;
        this.clickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(context);
        View view = myInflater.inflate(R.layout.stocks_crypto_recycler_row, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ticker.setText(assets.get(position).name);
        holder.quantity.setText(String.valueOf(assets.get(position).quantity));
        String total = context.getResources().getString(R.string.dollar_sign) + String.valueOf(assets.get(position).getTotal());
        holder.total.setText(total);
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    public Asset getItem(int id) { return assets.get(id); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ticker;
        public TextView quantity;
        public TextView total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ticker = itemView.findViewById(R.id.stocks_crypto_ticker);
            quantity = itemView.findViewById(R.id.stocks_crypto_quantity);
            total = itemView.findViewById(R.id.stocks_crypto_total);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }
}
