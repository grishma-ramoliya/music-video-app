package com.example.musicvideoapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicvideoapp.DataMusicOnlineRes;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.TableMagicSlideshowRes;
import com.example.musicvideoapp.adapter.FragmentAdapter;
import com.example.musicvideoapp.adapter.MusicAdapter;
import com.example.musicvideoapp.adapter.MusicFragAdapter;
import com.example.musicvideoapp.fragment.TodayFragment;
import com.example.musicvideoapp.utils.AsyncHttpRequest;
import com.example.musicvideoapp.utils.AsyncResponseHandler;
import com.example.musicvideoapp.utils.Debug;
import com.example.musicvideoapp.utils.RequestParamsUtils;
import com.example.musicvideoapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;

public class SelectMusicActivity extends AppCompatActivity {

    private MusicFragAdapter musicFragAdapter;
    private ViewPager viewPager;
    private TextView btnLocal , btnFeatured;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_music);

        initialize();

        musicFragAdapter=new MusicFragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(musicFragAdapter);
        btnLocal.setBackgroundColor(Color.BLACK);
        btnFeatured.setBackgroundColor(Color.WHITE);
        btnLocal.setTextColor(Color.WHITE);
        btnFeatured.setTextColor(Color.BLACK);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position==0) {
                    btnLocal.setBackgroundColor(Color.BLACK);
                    btnFeatured.setBackgroundColor(Color.WHITE);
                    btnLocal.setTextColor(Color.WHITE);
                    btnFeatured.setTextColor(Color.BLACK);
                }else {
                    btnLocal.setBackgroundColor(Color.WHITE);
                    btnFeatured.setBackgroundColor(Color.BLACK);
                    btnLocal.setTextColor(Color.BLACK);
                    btnFeatured.setTextColor(Color.WHITE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initialize() {
        btnLocal=findViewById(R.id.btnLocal);
        btnFeatured=findViewById(R.id.btnFeatured);
        viewPager=findViewById(R.id.vpMusic);
    }



}