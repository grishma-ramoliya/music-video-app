 package com.example.musicvideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.arthenica.mobileffmpeg.Statistics;
import com.bumptech.glide.Glide;
import com.example.musicvideoapp.DialogUtil;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.Video;
//import com.example.musicvideoapp.adapter.SliderImageViewPagerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class VideoActivity extends AppCompatActivity {

    private ImageView backArrow,ivImages;
    private VideoView videoView;
    private Button btnStart;
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

//        Glide.with(this).load("/storage/emulated/0/Download/abhi/theme64/data/d1.jpg").into(ivImages);
//        int[] i = new int[270];
//        for (int j=1;j<270;j++){
//            i[j]=j;
//        }
//
//        AsyncTaskExample asyncTask=new AsyncTaskExample();
//        asyncTask.execute("/storage/emulated/0/Download/abhi/theme64/data/sound.aac");
//        new CountDownTimer(500,1777){
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//                Glide.with(VideoActivity.this).load("/storage/emulated/0/Download/abhi/theme64/data/d"+ i[0] +".jpg").into(ivImages);
//                int j=i[0]++;
//                if (j==270){
//                    i[0] =0;}
//                start();
//            }
//        }.start();



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
        ivImages=findViewById(R.id.ivImages);
        btnStart=findViewById(R.id.btnStart);

    }

//    private class AsyncTaskExample extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            MediaPlayer music =new  MediaPlayer();
//            try {
//                music.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                music.setDataSource(getApplicationContext(),Uri.parse(strings[0]));
//                music.prepare();
//                music.start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//    }
}