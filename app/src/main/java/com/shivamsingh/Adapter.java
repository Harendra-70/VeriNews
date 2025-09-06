package com.shivamsingh;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shivamsingh.verinews.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<Model>arrayList;

    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        holder.newsTitle.setText(arrayList.get(position).getTitle());
        holder.newsDescription.setText(arrayList.get(position).getDescription());
        holder.newsAuthor.setText(arrayList.get(position).getAuthor());
        holder.newsPublishedAt.setText(arrayList.get(position).getPublishedAt());
        holder.newsSource.setText(arrayList.get(position).getSource().getName());
        Glide.with(context).load(arrayList.get(position).getUrlToImage()).into(holder.newsImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, WebViewActivity.class);
                intent.putExtra("url",arrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView newsTitle,newsDescription,newsPublishedAt,newsAuthor,newsSource;
        ImageView newsImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsAuthor=itemView.findViewById(R.id.newsAuthor);
            newsDescription=itemView.findViewById(R.id.newsDescription);
            newsPublishedAt=itemView.findViewById(R.id.newsPublishedAt);
            newsTitle=itemView.findViewById(R.id.newsTitle);
            newsSource=itemView.findViewById(R.id.newsSource);
            newsImage=itemView.findViewById(R.id.newsImage);

        }
    }
}
