package com.example.musicvideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.musicvideoapp.R;

public class VideoSecondActivity extends AppCompatActivity {
    VideoView videoView;
    ImageView backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosecond);

        initialize();

        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video);

        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();

        MediaPlayer music = MediaPlayer.create(VideoSecondActivity.this,R.raw.audio);
        music.start();



        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VideoSecondActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void initialize() {
        videoView=findViewById(R.id.Video_View);
        backArrow=findViewById(R.id.backArrow);
    }
}