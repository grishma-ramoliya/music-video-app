package com.example.musicvideoapp.utils;
import android.content.Context;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestParamsUtils {
    public static final String STYLE="style";

    public static RequestBody newRequestBody(Context context, String json) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        return body;
    }

    public static FormBody.Builder newRequestFormBody(Context c) {
        FormBody.Builder builder = new FormBody.Builder();
        return builder;
    }

    public static HttpUrl.Builder newRequestUrlBuilder(Context c, String url) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        return urlBuilder;

    }
}

