package com.example.musicvideoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.TableMagicSlideshowRes;
import com.example.musicvideoapp.items.Constant;
import com.example.musicvideoapp.items.Theme;
import com.example.musicvideoapp.activity.VideoActivity;

import java.util.ArrayList;
import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter <ThemeAdapter.ViewHolder>{
    private ArrayList<TableMagicSlideshowRes.TableMagicSlideshow> tableMagicSlideshows=new ArrayList<>();
    private Context context;
    private SlideVideoDownload mSlideVideoDownload;

    public ThemeAdapter( Context context) {

        this.context = context;
    }

    public void setTableMagicSlideshows(ArrayList<TableMagicSlideshowRes.TableMagicSlideshow> tableMagicSlideshows) {
        this.tableMagicSlideshows = tableMagicSlideshows;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ThemeAdapter.ViewHolder viewHolder, int position) {
        TableMagicSlideshowRes.TableMagicSlideshow viewItem = tableMagicSlideshows.get(position);
//        viewHolder.imageView.setImageResource(viewItem.get());
        Glide.with(context).load(Constant.BASE_PATH_ICON +viewItem.getLink()+"/"+viewItem.getIcon()).into(viewHolder.imageView);
        viewHolder.textView.setText(viewItem.getTextName());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(context, VideoActivity.class);
//                context.startActivity(i);i
                if (mSlideVideoDownload!=null){
                    mSlideVideoDownload.OnClickVideoDownload(Constant.BASE_PATH_VIDEO_EX+viewItem.getLink()+"/"+viewItem.getLink()+"_video_ex.ip");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableMagicSlideshows.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View view) {
            super(view);
            cardView=view.findViewById(R.id.card_view);
            textView = (TextView)view.findViewById(R.id.name);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }
    public interface SlideVideoDownload{
        void OnClickVideoDownload(String url);
    }

    public void setSlideVideoDownload(SlideVideoDownload mSlideVideoDownload) {
        this.mSlideVideoDownload = mSlideVideoDownload;
    }
}
