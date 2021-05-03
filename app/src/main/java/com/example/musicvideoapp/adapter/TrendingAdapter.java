package com.example.musicvideoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicvideoapp.R;
import com.example.musicvideoapp.activity.VideoActivity;
import com.example.musicvideoapp.items.Theme;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {
    private List<Theme> items;
    private Context context;
    public TrendingAdapter(Context context)
    {
        this.context=context;
    }
    public void setItems(List<Theme> items)
    {
        this.items=items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid,viewGroup,false);
        return new TrendingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Theme viewItem = items.get(position);
        viewHolder.imageView.setImageResource(viewItem.getImageId());
        viewHolder.textView.setText(viewItem.getName());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, VideoActivity.class);
                context.startActivity(i);
            }
       });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View view) {
            super(view);
            cardView=view.findViewById(R.id.card_view);
            textView = (TextView)view.findViewById(R.id.name);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }
}
