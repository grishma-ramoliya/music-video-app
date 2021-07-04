package com.example.musicvideoapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musicvideoapp.DataMusicOnlineRes;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.activity.SelectMusicActivity;
import com.example.musicvideoapp.activity.VideoActivity;
import com.example.musicvideoapp.activity.VideoSecondActivity;
import com.example.musicvideoapp.adapter.MusicAdapter;
import com.example.musicvideoapp.utils.AsyncHttpRequest;
import com.example.musicvideoapp.utils.AsyncResponseHandler;
import com.example.musicvideoapp.utils.Debug;
import com.example.musicvideoapp.utils.RequestParamsUtils;
import com.example.musicvideoapp.utils.URLs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import ir.mahdi.mzip.zip.ZipArchive;
import okhttp3.Call;
import okhttp3.FormBody;


public class FeaturedFragment extends Fragment {

    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private ArrayList<DataMusicOnlineRes.DataMusicOnline> dataMusicOnlines=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FeaturedFragment.AsyncTaskExample asyncTask=new FeaturedFragment.AsyncTaskExample();
        asyncTask.execute("/storage/emulated/0/Download/dreams_tonite.aac");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rcvMusic);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        musicAdapter=new MusicAdapter(view.getContext());
        musicAdapter.setDataMusicOnlines(dataMusicOnlines);
        recyclerView.setAdapter(musicAdapter);
        musicAdapter.setSlideMusicDownload(new MusicAdapter.SlideMusicDownload() {
            @Override
            public void OnClickMusicDownload(String url, String fileName) {
                new Download_music_from_url().execute(url+fileName,fileName);
            }
        });

        FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
        body.addEncoded(RequestParamsUtils.STYLE, "1");
        Call call = AsyncHttpRequest.newRequestPost(getActivity(), body.build(), URLs.GET_DATA_MUSIC_ONLINE());
        call.enqueue(new DataMusicOnlineResHandle(getActivity()));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_featured, container, false);
    }


    //region DataMusicOnlineRes Handler
    private class DataMusicOnlineResHandle extends AsyncResponseHandler {

        public DataMusicOnlineResHandle(Activity context) {
            super(context);
        }

        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(String response) {
            DataMusicOnlineRes res = new Gson().fromJson(response, new TypeToken<DataMusicOnlineRes>() {}.getType());
            if (response.length() > 0 && response != null) {
                if (res.getSuccess()==1) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dataMusicOnlines.addAll( res.getDataMusicOnline());
                            musicAdapter.setDataMusicOnlines(dataMusicOnlines);
//                        dialog.dismiss();
                            Toast.makeText(getContext(), "Data Found", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                        dialog.dismiss();
                            if(res.getDataMusicOnline().isEmpty()) {
                                Log.d("Tag","No Data Found");
                                //Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }

        @Override
        public void onFinish() {
//        dialog.dismiss();
        }

        @Override
        public void onFailure(Throwable e, String content) {
            Debug.e("onFailure", content);
        }
    }
    //endregion

    //region DownloadMusic
    class Download_music_from_url extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String ... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conexion.getContentLength();

                // downlod the file

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+f_url[1]);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

                /*URL url = new URL(f_url[0]);
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
                File sd = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+f_url[1]);//.getExternalStorageDirectory();
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
                input.close();*/
                return "Downloaded";

            } catch (Exception e) {
                Log.e("Error: ", e.toString());
                return "Failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    private class AsyncTaskExample extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... f_url) {
            MediaPlayer music =new  MediaPlayer();
            try {
                music.setAudioStreamType(AudioManager.STREAM_MUSIC);
                music.setDataSource(getContext(), Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+f_url[1]));
                music.prepare();
                music.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}