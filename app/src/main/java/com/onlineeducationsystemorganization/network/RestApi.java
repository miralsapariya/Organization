package com.onlineeducationsystemorganization.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onlineeducationsystemorganization.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This class is used to handling operation for Service
 */
public class RestApi {
   // private static String mAuth = Credentials.basic("33435c387e3e979614861126d9d4c16a", "a886c7b8d4a09b2de581e7b1fec64916");

    private static Retrofit sRetrofit, syncRetrofit;
    private static OkHttpClient sOkHttpClient, sSyncOkHttpClient;
    private static String language = "";

    private static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Request original = chain.request();
//                        int maxAge = 60 * 60 * 24; // tolerate 1 day

                            Request request = original.newBuilder()
                                   // .header("api-key", ServerConstents.API_KEY)
                                    .addHeader("Accept", ServerConstents.HEADER_ACCEPT)
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    });

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(logging);
            }
            sOkHttpClient = okHttpClientBuilder.build();
        }
        return sOkHttpClient;
    }

    private static Retrofit getClient(String url) {

        if (sRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(url)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getOkHttpClient())
                    .build();
        }
        return sRetrofit;
    }

    public static <T> T getConnection(Class<T> service, String url) {
        Log.d("URL :: ", ""+url);
        return getClient(url).create(service);
    }

}
