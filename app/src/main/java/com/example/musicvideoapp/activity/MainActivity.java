package com.example.musicvideoapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//import com.example.musicvideoapp.Decompress;
import com.example.musicvideoapp.GetPathFromUri;
import com.example.musicvideoapp.adapter.FragmentAdapter;
import com.example.musicvideoapp.adapter.ThemeAdapter;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.items.Theme;
import com.google.android.material.tabs.TabLayout;

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
//
//            if(isStoragePermissionGranted()){
        initialize();
//            }

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
// call the unzip folder
//        File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//.getExternalStorageDirectory();
//            final File backupDBFolder = new File(sd.getPath(),"abhi");
//        if (!backupDBFolder.exists()) {
//            backupDBFolder.mkdirs();
//        }
//        try {
//           InputStream stream=getAssets().open("Theme_64.zip");
//           unzip(stream,backupDBFolder.getPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
    public static void unzip(InputStream stream, String destination) {
        dirChecker(destination, "");
        byte[] buffer = new byte[1024 * 10];
        try {
            ZipInputStream zin = new ZipInputStream(stream);
            ZipEntry ze = null;

            while ((ze = zin.getNextEntry()) != null) {
                Log.v("TAG", "Unzipping " + ze.getName());

                if (ze.isDirectory()) {
                    dirChecker(destination, ze.getName());
                } else {
                    File f = new File(destination, ze.getName());
                    if (!f.exists()) {
                        boolean success = f.createNewFile();
                        if (!success) {
                            Log.w("TAG", "Failed to create file " + f.getName());
                            continue;
                        }
                        FileOutputStream fout = new FileOutputStream(f);
                        int count;
                        while ((count = zin.read(buffer)) != -1) {
                            fout.write(buffer, 0, count);
                        }
                        zin.closeEntry();
                        fout.close();
                    }
                }

            }
            zin.close();
        } catch (Exception e) {
            Log.e("TAG", "unzip", e);
        }

    }
    private static void dirChecker(String destination, String dir) {
        File f = new File(destination, dir);

        if (!f.isDirectory()) {
            boolean success = f.mkdirs();
            if (!success) {
                Log.w("TAG", "Failed to create folder " + f.getName());
            }
        }
    }
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
        button=findViewById(R.id.btnGetFile);
    }

//    //region FOR GET STORAGE PERMISSION
//    public boolean isStoragePermissionGranted() {
//        int ACCESS_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if ((ACCESS_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
//            return false;
//        }
//        return true;
//    }
//
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == STORAGE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            initialize();
//        }
//    }
    //endregion
}