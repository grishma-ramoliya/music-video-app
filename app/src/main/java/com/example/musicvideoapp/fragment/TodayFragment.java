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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
        themeAdapter.setSlideVideoDownload(new ThemeAdapter.SlideVideoDownload() {
            @Override
            public void OnClickVideoDownload(String url,String zipName) {
                new Download_file_from_url().execute(url,zipName);
            }
        });
        recyclerView.setAdapter(themeAdapter);


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

    class Download_file_from_url extends AsyncTask<String,String,String>{

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
                input.close();

                // call the unzip folder
                File makeDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"/"+f_url[1]);//.getExternalStorageDirectory();
                if (!makeDir.exists()) {
                    makeDir.mkdirs();
                }
                try {
                    InputStream stream=new FileInputStream(sd);
                    unzip(stream,makeDir.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

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