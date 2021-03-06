package com.example.musicvideoapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicvideoapp.R;
import com.example.musicvideoapp.adapter.ThemeAdapter;
import com.example.musicvideoapp.adapter.TrendingAdapter;
import com.example.musicvideoapp.items.Theme;

import java.util.ArrayList;


public class TrendingFragment extends Fragment {
    private RecyclerView recyclerView;
    private TrendingAdapter trendingAdapter;
    private ArrayList<Theme> theme;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // recyclerView.setHasFixedSize(true);
        recyclerView = view.findViewById(R.id.recycleView);

        setDummyData();

        //set layout manager and adapter for "GridView"
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        trendingAdapter = new TrendingAdapter(view.getContext());
        trendingAdapter.setItems(theme);
        recyclerView.setAdapter(trendingAdapter);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }

    private void setDummyData() {
        theme = new ArrayList<>();
        theme.add(new Theme("#MagicLake", R.drawable.lake));
        theme.add(new Theme("#MagicLotus", R.drawable.lotus));
        theme.add(new Theme("#MagicNight", R.drawable.night));
        theme.add(new Theme("#Painting", R.drawable.painting));


    }

}