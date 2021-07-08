 package com.example.musicvideoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
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
import com.example.musicvideoapp.fragment.TodayFragment;
//import com.example.musicvideoapp.adapter.SliderImageViewPagerAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import ir.mahdi.mzip.zip.ZipArchive;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class VideoActivity extends AppCompatActivity {

    private ImageView backArrow,ivImages;
    private VideoView videoView;
    private Button btnStart;
    private String videoPath="";
    private String folderName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initialize();

//        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video);

//        File file=new File(videoPath);
//        Uri uri = FileProvider.getUriForFile(this, "com.example.musicvideoapp.fileProvider",file);
        MediaController controller=new MediaController(this);
        controller.setAnchorView(videoView);
        Uri uri=Uri.parse(videoPath);
        videoView.setMediaController(controller);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();



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
                new VideoActivity.Download_image_file_from_url().execute("http://108.61.220.32/public_html/android_ads/MAGIC_SLIDESHOW_SOURCE"+folderName+folderName+".ip",folderName);

            }
        });
    }


    class Download_image_file_from_url extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String ... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
//
                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = connection.getContentLength();
                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);
                // Output stream
                File sd = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+f_url[1]+".zip");//.getExternalStorageDirectory();
                OutputStream output = new FileOutputStream(sd.getPath());

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

                // call the unzip folder
                 ZipArchive zipArchive = new ZipArchive();
                zipArchive.unzip("/storage/emulated/0/Download"+f_url[1]+".zip","/storage/emulated/0/Download","");

                //delete the zip
                File file=new File("/storage/emulated/0/Download"+f_url[1]+".zip");
                if(file.exists()){
                    file.delete();
                }
                Intent i = new Intent(VideoActivity.this, VideoSecondActivity.class);
                File file1=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+f_url[1]+"/data");
                i.putExtra("themeFolderPath",file1.getAbsolutePath().toString());
                startActivity(i);

                return "Downloaded";

            } catch (Exception e) {
                Log.e("Error: ", e.toString());
                return "Failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
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


    private void initialize() {

        videoView=findViewById(R.id.Video_View); 
        backArrow=findViewById(R.id.backArrow);
        ivImages=findViewById(R.id.ivImages);
        btnStart=findViewById(R.id.btnStart);
        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            videoPath=extra.getString("videoPath");
            folderName=extra.getString("folder");
        }

    }

}