package com.example.musicvideoapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.musicvideoapp.DataMusicOnlineRes;
import com.example.musicvideoapp.R;
import com.example.musicvideoapp.TableMagicSlideshowRes;
import com.example.musicvideoapp.adapter.MusicAdapter;
import com.example.musicvideoapp.utils.AsyncResponseHandler;
import com.example.musicvideoapp.utils.Debug;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SelectMusicActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private ArrayList<DataMusicOnlineRes.DataMusicOnline> dataMusicOnlines=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_music);

        recyclerView = (RecyclerView) findViewById(R.id.rcvMusic);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        musicAdapter=new MusicAdapter();
        musicAdapter.setDataMusicOnlines(dataMusicOnlines);
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

}