package com.example.musicvideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.example.musicvideoapp.R;

import java.io.File;
import java.io.IOException;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    ImageView backArrow;
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initialize();

        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video);

        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();

        MediaPlayer music = MediaPlayer.create(VideoActivity.this,R.raw.audio);
        music.start();



        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VideoActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VideoActivity.this, VideoSecondActivity.class);
                startActivity(i);
            }
        });
    }

    private void initialize() {
        videoView=findViewById(R.id.Video_View);
        backArrow=findViewById(R.id.backArrow);
        btnStart=findViewById(R.id.btnStart);

    }
}