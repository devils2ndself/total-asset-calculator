package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>{

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    Context context;
    ArrayList<NewsArticle> news;
    private final NewsRecyclerAdapter.ItemClickListener clickListener;

    public NewsRecyclerAdapter(Context context, ArrayList<NewsArticle> news, NewsRecyclerAdapter.ItemClickListener itemClickListener) {
        this.context = context;
        this.news = news;
        this.clickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NewsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInflater = LayoutInflater.from(context);
        View view = myInflater.inflate(R.layout.news_recycler_row, parent,false);

        return new NewsRecyclerAdapter.ViewHolder(view);
    }

    @SuppressLint({"DefaultLocale", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerAdapter.ViewHolder holder, int position) {
        holder.title.setText(news.get(position).title);
        holder.description.setText(news.get(position).description);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        holder.posted.setText(df.format(news.get(position).posted));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public NewsArticle getItem(int id) { return news.get(id); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView description;
        public TextView posted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            description = itemView.findViewById(R.id.news_description);
            posted = itemView.findViewById(R.id.news_posted);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }
}
