package com.astra.polytechnic.api;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Class untuk mengatur Konfigurasi Retorifit untuk pemanggilan API
public class RetrofitClient {
    private static Retrofit sRetrofit = null;

    public static Retrofit getClient(String url) {
        if (sRetrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                String authToken = getAuthToken();
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", authToken)
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            sRetrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return sRetrofit;
    }
    // Method untuk basics Authentication, mendefinisikan username dan password untuk mengakses API
    public static String getAuthToken() {
        String username = "admin";
        String password = "password";
        return Credentials.basic(username, password);
    }}
