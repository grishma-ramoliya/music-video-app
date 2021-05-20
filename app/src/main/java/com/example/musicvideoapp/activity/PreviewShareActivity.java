package com.example.musicvideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.musicvideoapp.R;
import com.example.musicvideoapp.items.Constant;

public class PreviewShareActivity extends AppCompatActivity {
    private String downloadVideoPath=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_preview_share);
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            downloadVideoPath=extra.getString(Constant.DOWNLOAD_VIDEO_FILE_PATH,"");
        }
    }
}