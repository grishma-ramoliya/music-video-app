package com.example.musicvideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Environment;

import com.example.musicvideoapp.adapter.FragmentAdapter;
import com.example.musicvideoapp.adapter.ThemeAdapter;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.items.Theme;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.example.musicvideoapp.DialogUtil.unzip;


public class MainActivity extends AppCompatActivity {

        private FragmentAdapter fragmentAdapter;
        private ViewPager viewPager;
        private TabLayout tabLayout;
        protected static final Queue<Callable<Object>> actionQueue = new ConcurrentLinkedQueue<>();
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initialize();

            fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
            viewPager.setAdapter(fragmentAdapter);

            tabLayout.setupWithViewPager(viewPager)  ;

// call the unzip folder
        File sd = Environment.getExternalStorageDirectory();
        if (sd.canWrite()) {
            final File backupDBFolder = new File(sd.getPath());
            try {
                unzip("/Theme_64.zip",backupDBFolder.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    //region FOR ADD UI ACTION CALL FOR ENCODING
    public static void addUIAction(final Callable<Object> callable) {
        actionQueue.add(callable);
    }
    //endregion
    private void initialize() {
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
    }




}