package com.example.musicvideoapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

//import com.example.musicvideoapp.Decompress;
import com.example.musicvideoapp.GetPathFromUri;
import com.example.musicvideoapp.TableMagicSlideshowRes;
import com.example.musicvideoapp.adapter.FragmentAdapter;
import com.example.musicvideoapp.adapter.ThemeAdapter;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.items.Theme;
import com.example.musicvideoapp.utils.AsyncResponseHandler;
import com.example.musicvideoapp.utils.Debug;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import ir.mahdi.mzip.zip.ZipArchive;

public class MainActivity extends AppCompatActivity {

    //how to get images from the device programmatically in android
    //https://stackoverflow.com/questions/9324103/download-and-extract-zip-file-in-android

        public static final int STORAGE_PERMISSION = 100;
        private FragmentAdapter fragmentAdapter;
        private ViewPager viewPager;
        private TabLayout tabLayout;
        private Button button;
        private static final int SELECT_ZIP=1010;
        private ImageView ivHome,ivMyVideo,ivFavourite,ivSetting;
        protected static final Queue<Callable<Object>> actionQueue = new ConcurrentLinkedQueue<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


        initialize();

//Call the StoragePermission
        isStoragePermissionGranted();

//Call the CreateFolder
        createAppFolders();

            fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
            viewPager.setAdapter(fragmentAdapter);
            tabLayout.setupWithViewPager(viewPager);

            ivHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentHome = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intentHome);
                }
            });
    }

//  Create Folder



    private void createAppFolders()
    {
        String root = Environment.getExternalStorageDirectory().toString();

        File myDir = new File(root + "/MusicVideo"+"/source_effect");
        File image_ex=new File(root + "/MusicVideo"+"/image_ex");
        File music_online=new File(root + "/MusicVideo"+"/music_online");
        File Music_Magic=new File(root + "/MusicVideo"+"/Music_Magic");
        File music_trim_temp=new File(root + "/MusicVideo"+"/music_trim_temp");
        File out_temp_video=new File(root + "/MusicVideo"+"/out_temp_video");
        File video_ex=new File(root + "/MusicVideo"+"/video_ex");



        myDir.mkdirs();
        image_ex.mkdirs();
        music_online.mkdirs();
        Music_Magic.mkdirs();
        music_trim_temp.mkdirs();
        out_temp_video.mkdirs();
        video_ex.mkdirs();


        }

//
//        File musicVideo=new File(Environment.getExternalStorageDirectory(),"MusicVideo");
//        if(!musicVideo.exists() || musicVideo.isFile()){
//            if(musicVideo.isFile()){
//                Toast.makeText(getApplicationContext(), "'MyFolder' exists as file", Toast.LENGTH_LONG).show();
//                return;
//            }
//            try{
//                musicVideo.mkdir();
//                File source_effect=new File(musicVideo.getAbsolutePath()+File.separator+"source_effect");
//                source_effect.mkdir();
//
//                Toast.makeText(getApplicationContext(), "Directories created successfully", Toast.LENGTH_SHORT).show();
//            } catch(Exception e){
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//        }
  //  }


    //region FOR ADD UI ACTION CALL FOR ENCODING
    public static void addUIAction(final Callable<Object> callable) {
        actionQueue.add(callable);
    }
    //endregion
    private void initialize() {
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        ivHome=findViewById(R.id.ivHome);
        ivMyVideo=findViewById(R.id.ivMyVideo);
        ivFavourite=findViewById(R.id.ivFavourite);
        ivSetting=findViewById(R.id.ivSetting);
    }

//Storage Permission

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }
}