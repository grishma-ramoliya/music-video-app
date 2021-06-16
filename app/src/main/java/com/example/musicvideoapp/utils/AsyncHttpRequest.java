package com.example.musicvideoapp.utils;

import android.content.Context;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AsyncHttpRequest extends OkHttpClient {

    public static Call newRequestPost(Context context, RequestBody body,String url){
        Builder builder=new Builder();
        OkHttpClient client=builder.build();
        Request.Builder request=new Request.Builder()
                .addHeader("Content-Type","application/json")
                .url(url)
                .post(body);
        Call call=client.newCall(request.build());
        return call;
    }
    public static Call newRequestPut(Context context, RequestBody body, String url) {
        Builder builder = new Builder();

        OkHttpClient client = builder.build();
        Request.Builder request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
//                .header(RequestParamsUtils.hAUTHORIZATION, "Bearer " + Utils.getUserToken(context))
                .url(url)
                .put(body);

//        if (Utils.isUserLoggedIn(context)) {
//            request.addHeader(RequestParamsUtils.AUTHORIZATION, Utils.getUserToken(context));
////            request.addHeader(RequestParamsUtils.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoIHVzZXIgaWQiLCJjdXN0b21lcl9pZCI6NTYxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwMDAvYXBpL3YxL2xvZ2luIiwiaWF0IjoxNTEzMjUzND E4LCJleHAiOjE1MTMyNTcwMTgsIm5iZiI6MTUxMzI1MzQxOCwianRpIjoiMEh6Zk9jbHBGdmVqVHNa MCJ9.Gd6xqym4gOzGMwQ4TRsh-q0HraaJ-gJUqMes-wBcuvQ");
////        request.addHeader(RequestParamsUtils.AUTHORIZATION, Constant.AUTH);
////        request.addHeader(RequestParamsUtils.APIKEY, Constant.APIKEY);
//        }

        Call call = client.newCall(request.build());
        return call;
    }

//    public static Call newRequestPut(Context context, RequestBody body, String url) {
//
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        OkHttpClient client = builder.build();
//        Request.Builder request = new Request.Builder()
//                .url(url)
//                .put(body);
//        Call call = client.newCall(request.build());
//        return call;
//    }

    public static Call newRequestPatch(Context context, RequestBody body, String url) {

        Builder builder = new Builder();

        OkHttpClient client = builder.build();
        Request.Builder request = new Request.Builder()
                .url(url)
                .patch(body);
        Call call = client.newCall(request.build());
        return call;
    }

    public static Call newRequestDelete(Context context, RequestBody body, String url) {

        Builder builder = new Builder();

        OkHttpClient client = builder.build();
        Request.Builder request = new Request.Builder()
                .url(url)
                .delete(body);
        Call call = client.newCall(request.build());
        return call;
    }

    public static Call newRequestGet(Context context, String url) throws IOException {
        Builder builder = new Builder();
////
        OkHttpClient client = builder.build();
        Request.Builder request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
//                .addHeader(RequestParamsUtils.APIKEY, Constant.APIKEY)
//                .header(RequestParamsUtils.hAUTHORIZATION, "Bearer " + Utils.getUserToken(context))
                .addHeader("apikey", "744f2139debe81cb62382f557b9266ee03b19e00")
//                .addHeader(RequestParamsUtils.AUTHORIZATION, "Bearer " + Utils.getUserToken(context))
//                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoIHVzZXIgaWQiLCJjdXN0b21lcl9pZCI6NjA2LCJpc3MiOiJodHRwOi8vb3JkZXJwaW4uY28udWsvaW50ZXJuZXRwb3MvYXBpL3YxL2xvZ2luIiwiaWF0IjoxNTMyMDgyNzMzLCJleHAiOjE1MzIwODYzMzMsIm5iZiI6MTUzMjA4MjczMywianRpIjoiOFAyMnRZOHVUZnFOQzVLRyJ9.PyGjmfG6tSzPjV1LTeIJxJ70erM_zasj8lHXv8Ff_Gw")
                .url(url)
                .get();
//        if (Utils.isUserLoggedIn(context)) {
//            request.addHeader(RequestParamsUtils.AUTHORIZATION, Utils.getUserToken(context));
////            request.addHeader(RequestParamsUtils.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoIHVzZXIgaWQiLCJjdXN0b21lcl9pZCI6NjA2LCJpc3MiOiJodHRwOi8vb3JkZXJwaW4uY28udWsvaW50ZXJuZXRwb3MvYXBpL3YxL2xvZ2luIiwiaWF0IjoxNTMyMDAzMzIyLCJleHAiOjE1MzIwMDY5MjIsIm5iZiI6MTUzMjAwMzMyMiwianRpIjoic0dkY2tZOXJaUzJpM2ZwZSJ9.8uSVtaWW0uLUsDVz2IhCfSISv1ZT5oZyI8U-l_sv8L4");
//        }
        Call call = client.newCall(request.build());
//        if (Utils.isUserLoggedIn(context)) {
//        Debug.e("Authorization", Utils.getUserToken(context));
//        }

//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("http://orderpin.co.uk/internetpos/api/v1/cart_status")
//                .get()
//                .addHeader("apikey", "744f2139debe81cb62382f557b9266ee03b19e00")
//                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoIHVzZXIgaWQiLCJjdXN0b21lcl9pZCI6NjA2LCJpc3MiOiJodHRwOi8vb3JkZXJwaW4uY28udWsvaW50ZXJuZXRwb3MvYXBpL3YxL2xvZ2luIiwiaWF0IjoxNTMyMDgyNzMzLCJleHAiOjE1MzIwODYzMzMsIm5iZiI6MTUzMjA4MjczMywianRpIjoiOFAyMnRZOHVUZnFOQzVLRyJ9.PyGjmfG6tSzPjV1LTeIJxJ70erM_zasj8lHXv8Ff_Gw")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Cache-Control", "no-cache")
//                .addHeader("User-Agent", "7d5bc0c7-cc90-462d-8ea7-1d7d07637521")
//                .build();
//
//        return client.newCall(request);
        return call;
    }

    public static Call newRequestGet_(Context context, String url) throws IOException {
        Builder builder = new Builder();
////
        OkHttpClient client = builder.build();

        Request.Builder request = new Request.Builder()
//                .addHeader("Content-Type", "application/json")
//                .addHeader("apikey", "744f2139debe81cb62382f557b9266ee03b19e00")
////                .addHeader(RequestParamsUtils.hAUTHORIZATION, "Bearer " + Utils.getUserToken(context))
//                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoIHVzZXIgaWQiLCJjdXN0b21lcl9pZCI6NjA2LCJpc3MiOiJodHRwOi8vb3JkZXJwaW4uY28udWsvaW50ZXJuZXRwb3MvYXBpL3YxL2xvZ2luIiwiaWF0IjoxNTMyMDgyNzMzLCJleHAiOjE1MzIwODYzMzMsIm5iZiI6MTUzMjA4MjczMywianRpIjoiOFAyMnRZOHVUZnFOQzVLRyJ9.PyGjmfG6tSzPjV1LTeIJxJ70erM_zasj8lHXv8Ff_Gw")
                .url(url)
                .get();
//        if (Utils.isUserLoggedIn(context)) {
//            request.addHeader(RequestParamsUtils.AUTHORIZATION, Utils.getUserToken(context));
////            request.addHeader(RequestParamsUtils.AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoIHVzZXIgaWQiLCJjdXN0b21lcl9pZCI6NjA2LCJpc3MiOiJodHRwOi8vb3JkZXJwaW4uY28udWsvaW50ZXJuZXRwb3MvYXBpL3YxL2xvZ2luIiwiaWF0IjoxNTMyMDAzMzIyLCJleHAiOjE1MzIwMDY5MjIsIm5iZiI6MTUzMjAwMzMyMiwianRpIjoic0dkY2tZOXJaUzJpM2ZwZSJ9.8uSVtaWW0uLUsDVz2IhCfSISv1ZT5oZyI8U-l_sv8L4");
//        }
        Call call = client.newCall(request.build());
//        if (Utils.isUserLoggedIn(context)) {
//        }


//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("http://orderpin.co.uk/internetpos/api/v1/cart_status")
//                .get()
//                .addHeader("apikey", "744f2139debe81cb62382f557b9266ee03b19e00")
//                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdXRoIHVzZXIgaWQiLCJjdXN0b21lcl9pZCI6NjA2LCJpc3MiOiJodHRwOi8vb3JkZXJwaW4uY28udWsvaW50ZXJuZXRwb3MvYXBpL3YxL2xvZ2luIiwiaWF0IjoxNTMyMDgyNzMzLCJleHAiOjE1MzIwODYzMzMsIm5iZiI6MTUzMjA4MjczMywianRpIjoiOFAyMnRZOHVUZnFOQzVLRyJ9.PyGjmfG6tSzPjV1LTeIJxJ70erM_zasj8lHXv8Ff_Gw")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Cache-Control", "no-cache")
//                .addHeader("User-Agent", "7d5bc0c7-cc90-462d-8ea7-1d7d07637521")
//                .build();
//        return client.newCall(request);
        return call;
    }
}

