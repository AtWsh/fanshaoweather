package com.study.wsh.fanshaoweather.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by wenshenghui on 2017/6/22.
 * 封装okhttp的网络请求
 */

public class HttpUtil {

    public static void sendOKHttpRequest(String url, okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
