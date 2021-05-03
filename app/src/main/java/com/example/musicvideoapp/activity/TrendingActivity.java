package com.example.musicvideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.musicvideoapp.R;
import com.example.musicvideoapp.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class TrendingActivity extends AppCompatActivity {
    private FragmentAdapter fragmentAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        initialize();

        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);

        tabLayout.setupWithViewPager(viewPager)  ;

    }

    private void initialize() {
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
    }
}