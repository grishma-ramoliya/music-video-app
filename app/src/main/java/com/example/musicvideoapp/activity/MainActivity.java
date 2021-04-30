package com.example.musicvideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.musicvideoapp.adapter.FragmentAdapter;
import com.example.musicvideoapp.adapter.ThemeAdapter;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.items.Theme;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        private FragmentAdapter fragmentAdapter;
        private ViewPager viewPager;
        private TabLayout tabLayout;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initialize();

            fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
            viewPager.setAdapter(fragmentAdapter);

            tabLayout.setupWithViewPager(viewPager);

        }

    private void initialize() {
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
    }




}