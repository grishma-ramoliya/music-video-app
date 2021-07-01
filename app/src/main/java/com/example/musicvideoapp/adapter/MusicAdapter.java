package com.example.musicvideoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicvideoapp.DataMusicOnlineRes;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.items.Constant;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private ArrayList<DataMusicOnlineRes.DataMusicOnline> dataMusicOnlines=new ArrayList<>();
    private Context context;

    public MusicAdapter(Context context)
    {
        this.context=context;
    }

    public void setDataMusicOnlines(ArrayList<DataMusicOnlineRes.DataMusicOnline> dataMusicOnlines){
        this.dataMusicOnlines = dataMusicOnlines;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_music,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicAdapter.ViewHolder viewHolder, int position) {
        DataMusicOnlineRes.DataMusicOnline viewList = dataMusicOnlines.get(position);
        viewHolder.txtSongName.setText(viewList.getName());
        viewHolder.txtAuthorName.setText(viewList.getAuthor());
        viewHolder.txtDuration.setText(viewList.getDuration());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtSongName,txtAuthorName,txtDuration;

        public ViewHolder(View view) {
            super(view);
            txtSongName=view.findViewById(R.id.txtSongName);
            txtAuthorName=view.findViewById(R.id.txtAuthorName);
            txtDuration=view.findViewById(R.id.txtDuration);
        }
    }
}
