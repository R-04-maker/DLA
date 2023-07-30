package com.astra.polytechnic.service;

import com.astra.polytechnic.model.Banner;
import com.astra.polytechnic.model.response.BannerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BannerService {
    @GET("getBanners")
    Call<List<Banner>> getAllBanner();
}
