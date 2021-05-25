package com.example.musicvideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.musicvideoapp.R;
import com.example.musicvideoapp.items.Constant;

public class PreviewShareActivity extends AppCompatActivity {
    private String downloadVideoPath=null;
    private VideoView Video_Preview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_share);
        initialize();
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            downloadVideoPath=extra.getString(Constant.DOWNLOAD_VIDEO_FILE_PATH,"");
        }
        playVideo(downloadVideoPath);

    }

    private void initialize() {
        Video_Preview=findViewById(R.id.Video_Preview);
    }

    //region Play video
    protected void playVideo(String videoFilePath) {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(Video_Preview);
        Video_Preview.setVideoURI(Uri.parse("file://" + videoFilePath));
        Video_Preview.setMediaController(mediaController);
        Video_Preview.requestFocus();
        Video_Preview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                Video_Preview.setBackgroundColor(0x00000000);
            }
        });
        Video_Preview.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Video_Preview.stopPlayback();
                return false;
            }
        });
        Video_Preview.start();
    }
    //endregion
}