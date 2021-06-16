 package com.example.musicvideoapp.fragment;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musicvideoapp.R;
import com.example.musicvideoapp.TableMagicSlideshowRes;
import com.example.musicvideoapp.activity.MainActivity;
import com.example.musicvideoapp.adapter.ThemeAdapter;
import com.example.musicvideoapp.items.Theme;
import com.example.musicvideoapp.utils.AsyncHttpRequest;
import com.example.musicvideoapp.utils.AsyncResponseHandler;
import com.example.musicvideoapp.utils.Debug;
import com.example.musicvideoapp.utils.RequestParamsUtils;
import com.example.musicvideoapp.utils.URLs;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.arthenica.mobileffmpeg.Config.getPackageName;
import static com.example.musicvideoapp.items.Constant.CAMERA_REQUEST_CODE;
import static com.example.musicvideoapp.items.Constant.PICK_IMAGE;
import static com.example.musicvideoapp.items.Constant.STORAGE_REQUEST_CODE;


 public class TodayFragment extends Fragment {

    private RecyclerView recyclerView;
    private ThemeAdapter themeAdapter;
    private ArrayList<TableMagicSlideshowRes.TableMagicSlideshow> tableMagicSlideshows=new ArrayList<>();
    private static String file_url = "http://108.61.220.32/public_html/android_ads/MAGIC_SLIDESHOW_SOURCE/";//theme47/icon_theme.jpg";


     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // recyclerView.setHasFixedSize(true);
        if (checkUsesPermission(CAMERA_REQUEST_CODE)) {
//            capturePictureFromCamera();
        } else {
            requestUsesPermissions(CAMERA_REQUEST_CODE);
        }
        if (checkUsesPermission(STORAGE_REQUEST_CODE)) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, PICK_IMAGE);
        } else {
            requestUsesPermissions(STORAGE_REQUEST_CODE);
        }
        recyclerView = view.findViewById(R.id.recycleView);

        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        themeAdapter = new ThemeAdapter(view.getContext());
        themeAdapter.setTableMagicSlideshows(tableMagicSlideshows);
        recyclerView.setAdapter(themeAdapter);

        new Download_file_from_url().execute(file_url);

        FormBody.Builder body = RequestParamsUtils.newRequestFormBody(getActivity());
        body.addEncoded(RequestParamsUtils.STYLE, "1");
        Call call = AsyncHttpRequest.newRequestPost(getActivity(), body.build(), URLs.GET_ALL_MAGIC_THEME());
        call.enqueue(new TableMagicSlideshowResHandle(getActivity()));


        //set layout manager and adapter for "GridView"
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    //region TableMagicSlideshowRes Handler
    private class TableMagicSlideshowResHandle extends AsyncResponseHandler {

        public TableMagicSlideshowResHandle(Activity context) {
            super(context);
        }

        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(String response) {
            TableMagicSlideshowRes res = new Gson().fromJson(response, new TypeToken<TableMagicSlideshowRes>() {}.getType());
            if (response.length() > 0 && response != null) {
                if (res.getSuccess()==1) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           tableMagicSlideshows.addAll( res.getTableMagicSlideshow());
                            themeAdapter.setTableMagicSlideshows(tableMagicSlideshows);
//                        dialog.dismiss();
                                Toast.makeText(getContext(), "Data Found", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                        dialog.dismiss();
                            if(res.getTableMagicSlideshow().isEmpty()) {
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
//
//    private String download_file(String f_url){
//        int count;
//        try {
//            URL url = new URL(f_url);
//            URLConnection connection = url.openConnection();
//            connection.connect();
//            // this will be useful so that you can show a tipical 0-100%
//            // progress bar
//            int lenghtOfFile = connection.getContentLength();
//            // download the file
//            InputStream input = new BufferedInputStream(url.openStream(),
//                    8192);
//            // Output stream
//            OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
//
//            byte data[] = new byte[1024];
//            long total = 0;
//            while ((count = input.read(data)) != -1) {
//                total += count;
//                // publishing the progress....
//                // After this onProgressUpdate will be called
////                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
//
//                // writing data to file
//                output.write(data, 0, count);
//            }
//            // flushing output
//            output.flush();
//
//            // closing streams
//            output.close();
//            input.close();
//            return "Downloaded";
//
//        } catch (Exception e) {
//            Log.e("Error: ", e.toString());
//            return "Failed";
//        }
//    }

    class Download_file_from_url extends AsyncTask<String,String,String>{
//        String manager;
//        String downloadDirPath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"";
//        File file=new File(downloadDirPath);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String ... f_url) {
//            int count;
           try {
//                URL url = new URL(f_url[0]+"theme51/theme51_video_ex.ip");
//               URLConnection connection = url.openConnection();
//               connection.connect();
//
               DownloadManager manager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
               Uri uri = Uri.parse(f_url[0]+"theme51/theme51_video_ex.ip");
               DownloadManager.Request request = new DownloadManager.Request(uri);
               request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
               long reference = manager.enqueue(request);
//
//
//                // this will be useful so that you can show a tipical 0-100%
//                // progress bar
//                int lenghtOfFile = connection.getContentLength();
//                // download the file
//                InputStream input = new BufferedInputStream(url.openStream(),
//                        8192);
//                // Output stream
//                File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//.getExternalStorageDirectory();
////                final File backupDBFolder = new File(sd.getPath());
////                if (!backupDBFolder.exists()) {
////                    backupDBFolder.getParentFile().mkdirs();
////                }
//                OutputStream output = new FileOutputStream(sd.getPath());
//
//                byte data[] = new byte[1024];
//                long total = 0;
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//                    // publishing the progress....
//                    // After this onProgressUpdate will be called
//                   publishProgress("" + (int) ((total * 100) / lenghtOfFile));
//
//                    // writing data to file
//                    output.write(data, 0, count);
//                }
//                // flushing output
//                output.flush();
//
//                // closing streams
//                output.close();
//                input.close();
//                return "Downloaded";

            } catch (Exception e) {
                Log.e("Error: ", e.toString());
                return "Failed";
            }
            return "download";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    //region Permission
    private boolean checkUsesPermission(int REQUEST_CODE) {
        if (CAMERA_REQUEST_CODE == REQUEST_CODE) {
            if (ContextCompat.checkSelfPermission(getActivity(), CAMERA) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        } else if (STORAGE_REQUEST_CODE == REQUEST_CODE) {
            if (ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                    ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    private void requestUsesPermissions(int REQUEST_CODE) {
        if (REQUEST_CODE == CAMERA_REQUEST_CODE) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CODE);
        } else if (REQUEST_CODE == STORAGE_REQUEST_CODE) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getContext(), "Camera Permission Grant", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Allow Permission Manually in Setting ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                } else {
                    Toast.makeText(getContext(), "Allow Permission Manually in Setting ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
                break;
        }
    }

    //endregion
}