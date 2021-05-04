package com.example.musicvideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.arthenica.mobileffmpeg.Statistics;
import com.example.musicvideoapp.DialogUtil;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.Video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "mobile-ffmpeg-test";
    private String selectedCodec;
    private ArrayList<String> SelectedImagePath = new ArrayList<>();
    private static int VIDEO_DOWNLOAD=0;
    private File videoFile;
    private File audioWithVideoPath;
    private String audioPath;
    private VideoView videoView;
    private ImageView backArrow;
    private Button btnStart;
    private AlertDialog progressDialog;
    private Statistics statistics;
    private ProgressDialog progressBar;
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

    //region Encoded Video
    private void encodedVideo() throws IOException {
        videoFile = getVideoFile();

        videoView.stopPlayback();
        if (videoFile.exists()) {
            videoFile.delete();
        }

        final String videoCodec = selectedCodec;
        Log.d(TAG, String.format("Testing VIDEO encoding with '%s' codec", videoCodec));
        showProgressDialog();

        String filePath1 = SelectedImagePath.get(0);
        String filePath2 = SelectedImagePath.get(1);
        String filePath3 = SelectedImagePath.get(2);

        String ffmpegCommand = Video.generateEncodeVideoScript(filePath1, filePath2, filePath3, videoFile.getAbsolutePath(), getSelectedVideoCodec(), getCustomOptions());
        Log.d(TAG, String.format("FFmpeg process started with arguments\n'%s'.", ffmpegCommand));

        long executionId = FFmpeg.executeAsync(ffmpegCommand, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                Log.d(TAG, String.format("FFmpeg process exited with rc %d.", returnCode));

                Log.d(TAG, "FFmpeg process output:");

                Config.printLastCommandOutput(Log.INFO);
                hideProgressDialog();

                if (returnCode == RETURN_CODE_SUCCESS) {
                    Log.d("TAG", "Encode completed successfully; playing video.");
                    playVideo(videoFile.getAbsolutePath());
                    VIDEO_DOWNLOAD=1;
//                    rvFrameList.setVisibility(View.VISIBLE);
//                    btnSelectAudio.setVisibility(View.VISIBLE);
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(VideoActivity.this, "Encode failed. Please check log for the details.", Toast.LENGTH_LONG).show();
                    Log.d("TAG", String.format("Encode failed with rc=%d.", returnCode));
                }

            }

        });
        Log.d(TAG, String.format("Async FFmpeg process started with executionId %d.", executionId));
    }
    //endregion

    //region Add Audio in Video
    private void encodedVideoWithAudio() throws IOException {
        audioWithVideoPath=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/AudioWithVideo.mp4");
        if (audioWithVideoPath.exists()){
            audioWithVideoPath.delete();
        }
        showProgressDialog();

        String ffmpegCommand = Video.generateAddAudioInVideo(videoFile.getAbsolutePath(),audioPath,audioWithVideoPath.getAbsolutePath());
        Log.d(TAG, String.format("FFmpeg process started with arguments\n'%s'.", ffmpegCommand));

        long executionId = FFmpeg.executeAsync(ffmpegCommand, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {

                Config.printLastCommandOutput(Log.INFO);
                hideProgressDialog();

                if (returnCode == RETURN_CODE_SUCCESS) {
                    Log.d("TAG", "Encode completed successfully; playing video.");
                    playVideo(audioWithVideoPath.getAbsolutePath());
                    VIDEO_DOWNLOAD=2;
                } else {
                    Toast.makeText(VideoActivity.this, "Encode failed. Please check log for the details.", Toast.LENGTH_LONG).show();
                    Log.d("TAG", String.format("Encode failed with rc=%d.", returnCode));
                }

            }
        });
        Log.d(TAG, String.format("Async FFmpeg process started with executionId %d.", executionId));
    }
    //endregion

    private File getVideoFile() {
        String videoCodec = selectedCodec;

        final String extension;
        switch (videoCodec) {
            case "vp8":
            case "vp9":
                extension = "webm";
                break;
            case "aom":
                extension = "mkv";
                break;
            case "theora":
                extension = "ogv";
                break;
            case "hap":
                extension = "mov";
                break;
            default:

                // mpeg4, x264, x265, xvid, kvazaar
                extension = "mp4";
                break;
        }
        final String video = "video." + extension;
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), video);
    }

    private void playVideo(String absolutePath) {
    }

    private String getCustomOptions() {
        String videoCodec = selectedCodec;

        switch (videoCodec) {
            case "x265":
                return "-crf 28 -preset fast ";
            case "vp8":
                return "-b:v 1M -crf 10 ";
            case "vp9":
                return "-b:v 2M ";
            case "aom":
                return "-crf 30 -strict experimental ";
            case "theora":
                return "-qscale:v 7 ";
            case "hap":
                return "-format hap_q ";
            default:

                // kvazaar, mpeg4, x264, xvid
                return "";
        }
    }

    private String getSelectedVideoCodec() {
        // NOTE THAT MPEG4 CODEC IS ASSIGNED HERE
        String videoCodec = selectedCodec;

        // VIDEO CODEC SPINNER HAS BASIC NAMES, FFMPEG NEEDS LONGER AND EXACT CODEC NAMES.
        // APPLYING NECESSARY TRANSFORMATION HERE
        switch (videoCodec) {
            case "x264":
                videoCodec = "libx264";
                break;
            case "openh264":
                videoCodec = "libopenh264";
                break;
            case "x265":
                videoCodec = "libx265";
                break;
            case "xvid":
                videoCodec = "libxvid";
                break;
            case "vp8":
                videoCodec = "libvpx";
                break;
            case "vp9":
                videoCodec = "libvpx-vp9";
                break;
            case "aom":
                videoCodec = "libaom-av1";
                break;
            case "kvazaar":
                videoCodec = "libkvazaar";
                break;
            case "theora":
                videoCodec = "libtheora";
                break;
        }

        return videoCodec;
    }
    //region Progress Dialog show & hide
    private void showProgressDialog() {
        // CLEAN STATISTICS
        statistics = null;
        Config.resetStatistics();

        progressDialog.show();
    }

    protected void hideProgressDialog() {
        progressDialog.dismiss();

        MainActivity.addUIAction(new Callable<Object>() {

            @Override
            public Object call() {
                progressDialog = DialogUtil.createProgressDialog(VideoActivity.this, "Encoding video");
                return null;
            }
        });
    }
    //endregion






}