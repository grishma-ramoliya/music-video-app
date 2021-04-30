package com.example.musicvideoapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.musicvideoapp.fragment.MostLikeFragment;
import com.example.musicvideoapp.fragment.TodayFragment;
import com.example.musicvideoapp.fragment.TrendingFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        if(position == 0)
            fragment = new TodayFragment();
        else if(position == 1)
            fragment = new TrendingFragment();
        else if(position == 2)
            fragment = new MostLikeFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Today";
        else if (position == 1)
            title = "Trending";
        else if (position == 2)
            title = "Most Like";
        return title;
    }
}
