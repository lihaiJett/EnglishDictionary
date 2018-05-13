package cn.lihailjt.englishdictionary.http.shanbei;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ShanbeiApi {
        @GET("/test/getServerInfo")
        Observable<Token> getServerInfo();
}
