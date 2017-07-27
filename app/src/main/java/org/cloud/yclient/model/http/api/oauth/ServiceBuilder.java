package org.cloud.yclient.model.http.api.oauth;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/23
 */

public class ServiceBuilder {

    public static final String API_BASE_URL = "@{SERVER_URL}";

    public static OauthToken oauthToken; // 로딩이나 어플리케이션 단에서 설정해준다.

    private static Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL) // Server Url
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    // oauth token
//    public static <S> S basicService(Class<S> serviceClass) {
//        return builder.client(
//                new OkHttpClient.Builder().addInterceptor(chain -> chain.proceed(requestBuild(chain.request(), OauthToken.basic()).build())).build
//                        ()).build().create(serviceClass);
//    }

    // oauth access token
    public static <S> S createService(Class<S> serviceClass) {
        return builder.client(
                new OkHttpClient.Builder()
                        .addInterceptor(new TokenInterceptor())
                        .build())
                .build().create(serviceClass);
    }

    private static class TokenInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Response response = chain.proceed(requestBuild(original, oauthToken.bearer()).build());
            if (response.code() == 401) { // 토큰이 만료되었다.
                // 이렇게 안하면 함수형처럼 response를 설정할 수 없어서 로우하게 만듬.
                Request newRequest = requestBuild(
                        new Request.Builder()
                                .url(String.format("%s/oauth/token?grant_type=refresh_token&refresh_token=%s", API_BASE_URL, oauthToken.getRefreshToken()))
                                .method("POST", RequestBody.create(MediaType.parse("application/json"), new byte[0]))
                                .build(), oauthToken.bearer()).build(); // create simple requestBuilder

                Response newResponse = chain.proceed(newRequest); // 동기, 순차적 작동.
                // 성공했을 경우에만 토큰을 새로 저장한다.
                if (newResponse.code() == 200) {
                    oauthToken = new GsonBuilder().create().fromJson(newResponse.body().string(), OauthToken.class);
                    response = chain.proceed(requestBuild(original, oauthToken.bearer()).build());
                }

                // else {} // 아니면 그냥 흘려보낸다.
            }

            return response;
        }
    }

    private static Request.Builder requestBuild(Request request, String auth) {
        return request.newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", auth)
                .method(request.method(), request.body());
    }
}
