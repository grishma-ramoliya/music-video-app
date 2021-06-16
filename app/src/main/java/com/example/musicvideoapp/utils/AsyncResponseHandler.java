package com.example.musicvideoapp.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;


public abstract class AsyncResponseHandler implements Callback {

    private Activity context;

    public AsyncResponseHandler(Activity context) {
        this.context = context;
    }

    public AsyncResponseHandler(Context context) {
        onStart();
    }

    abstract public void onStart();

    abstract public void onSuccess(String content);

    abstract public void onFinish();
    //
    abstract public void onFailure(Throwable e, String content);

    @Override
    public void onFailure(Call call, IOException e) {
        onFailure(e, "");
    }

    @Override
    public void onResponse(Call call, okhttp3.Response response) throws IOException {
        onFinish();

        Debug.e("onResponse", "" + response.code());

        if (response.code() == 401) {

            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(response.body().source().inputStream()));
            String read;

            while ((read = br.readLine()) != null) {
                sb.append(read);
            }

            br.close();

            try {
                if (!TextUtils.isEmpty(sb.toString())) {
                    onFailure(new Throwable(""), "" + sb.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            Utils.clearLoginCredentials(context);
//            LocalBroadcastManager.getInstance(context)
//                    .sendBroadcast(
//                            new Intent(Constant.FINISH_ACTIVITY));

//            Intent intent = new Intent(context,
//                    LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            context.startActivity(intent);
//            return;
        }
        if (response.isSuccessful()) {

            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(response.body().source().inputStream()));
            String read;

            while ((read = br.readLine()) != null) {
                sb.append(read);
            }
            br.close();
//            if (response.code() == 401) {
//
//                Utils.clearLoginCredetials(context);
//
//                LocalBroadcastManager.getInstance(context)
//                        .sendBroadcast(
//                                new Intent(Constant.FINISH_ACTIVITY));
//
//                Intent intent = new Intent(context,
//                        LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                context.startActivity(intent);
//                return;
//            }
            try {
                onSuccess(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            if (response.code() == 500) {
//
//                Utils.clearLoginCredetials(context);
//
//                LocalBroadcastManager.getInstance(context)
//                        .sendBroadcast(
//                                new Intent(Constant.FINISH_ACTIVITY_WITHOUT_HOME));
//
//                Intent intent = new Intent(context,
//                        LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                context.startActivity(intent);
//                return;
//            }
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(response.body().source().inputStream()));
            String read;

            while ((read = br.readLine()) != null) {
                sb.append(read);
            }

            br.close();


            onFailure(new Throwable(""), "" + sb.toString());
        }
    }
}
