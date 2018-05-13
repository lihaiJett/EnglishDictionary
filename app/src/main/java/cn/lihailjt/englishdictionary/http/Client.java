package cn.lihailjt.englishdictionary.http;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class Client {
    private static OkHttpClient mOkHttpClient;
    private final static HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR =
            new HttpLoggingInterceptor(message -> Log.d("Http:", message))
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
    /**
     * 因为设置了读写超时，该client不适用于上传和下载
     */
    public static OkHttpClient get() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    //log 拦截器
                    .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .build();
        }
        return mOkHttpClient;
    }
}
