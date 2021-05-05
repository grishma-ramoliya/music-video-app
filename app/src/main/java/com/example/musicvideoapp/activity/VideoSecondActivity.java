package com.example.musicvideoapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.Statistics;
import com.example.musicvideoapp.DialogUtil;
import com.example.musicvideoapp.GetPathFromUri;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.Video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;
import static com.example.musicvideoapp.items.Constant.RESULT_LOAD_IMAGE;

public class VideoSecondActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "mobile-ffmpeg-test";
    private String selectedCodec;
    private ArrayList<String> SelectedImagePath = new ArrayList<>();
    private static int VIDEO_DOWNLOAD=0;
    private File videoFile;
    private File imageWithVideoPath;
    private File audioWithVideoPath;
    private String audioPath;
    private VideoView videoView;
    private ImageView backArrow;
    private String[] projection = {MediaStore.MediaColumns.DATA};
    private Button btnStart;
    private AlertDialog progressDialog;
    private Statistics statistics;
    private String path;
    private ProgressDialog progressBar;
    private LinearLayout llAddImage,llAddMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosecond);

        initialize();

        llAddImage.setOnClickListener(this);
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
        llAddImage=findViewById(R.id.llAddImage);
        llAddMusic=findViewById(R.id.llAddMusic);
        progressDialog = DialogUtil.createProgressDialog(this, "Encoding video");

    }

    //region Encoded Video
    private void encodedVideo() throws IOException {
        imageWithVideoPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/FrameWithVideo.mp4");
        if (imageWithVideoPath.exists()) {
            imageWithVideoPath.delete();
        }

        final String videoCodec = selectedCodec;
        Log.d(TAG, String.format("Testing VIDEO encoding with '%s' codec", videoCodec));
        showProgressDialog();

        String ffmpegCommand = Video.generateAddImageOnVideo("/storage/emulated/0/Download/video1.mp4",path,imageWithVideoPath.getAbsolutePath());
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
                    playVideo(imageWithVideoPath.getAbsolutePath());
                    VIDEO_DOWNLOAD=1;
                } else {
                    Toast.makeText(VideoSecondActivity.this, "Encode failed. Please check log for the details.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(VideoSecondActivity.this, "Encode failed. Please check log for the details.", Toast.LENGTH_LONG).show();
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

    //region Play video
    protected void playVideo(String videoFilePath) {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setVideoURI(Uri.parse("file://" + videoFilePath));
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.setBackgroundColor(0x00000000);
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                videoView.stopPlayback();
                return false;
            }
        });
        videoView.start();
    }
    //endregion


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
                progressDialog = DialogUtil.createProgressDialog(VideoSecondActivity.this, "Encoding video");
                return null;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llAddImage:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
        }
    }
    //endregion


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            path = GetPathFromUri.getPathFromUri(VideoSecondActivity.this, uri);
            Log.e("Image Path",path);
            try {
                encodedVideo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}