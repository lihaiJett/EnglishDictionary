package cn.lihailjt.englishdictionary.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofits {
    private static Retrofit mRetrofit;
    public static Retrofit get() {
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Config.ShanbeyToken)
                    .client(Client.get())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
    public static <T> T get(Class<T> tClass){
        return get().create(tClass);
    }
}
