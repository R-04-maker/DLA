package com.astra.polytechnic.service;

import com.astra.polytechnic.model.Banner;
import com.astra.polytechnic.model.response.BannerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

// Service class merupakan class untuk berkomunikasi dengan server (API)
public interface BannerService {
    @GET("getBanners")
    Call<List<Banner>> getAllBanner();
}
