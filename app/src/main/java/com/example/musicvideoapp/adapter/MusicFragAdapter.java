package com.example.musicvideoapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.musicvideoapp.fragment.FeaturedFragment;
import com.example.musicvideoapp.fragment.LocalFragment;


public class MusicFragAdapter extends FragmentPagerAdapter {
    public MusicFragAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        if(position == 0)
            fragment = new LocalFragment();
        else if(position == 1)
            fragment = new FeaturedFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
