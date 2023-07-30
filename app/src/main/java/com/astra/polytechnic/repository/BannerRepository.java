package com.astra.polytechnic.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.api.ApiUtils;
import com.astra.polytechnic.dao.KoleksiDao;
import com.astra.polytechnic.model.Banner;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.model.response.ListKoleksiResponse;
import com.astra.polytechnic.service.BannerService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerRepository {
    private static final String TAG = "BannerRepository";
    private static BannerRepository INSTANCE;
    private BannerService mBannerService;

    private BannerRepository(Context context){
        mBannerService = ApiUtils.getBannerService();
    }

    public static void initialize(Context context){
        if (INSTANCE == null) {
            INSTANCE = new BannerRepository(context);
        }
    }

    public static BannerRepository get(){
        return INSTANCE;
    }

    public LiveData<List<Banner>> getAllBanner(){
        Log.i(TAG, "getAllBanner() called");
        MutableLiveData<List<Banner>> objlist = new MutableLiveData<>();
        Call<List<Banner>> call = mBannerService.getAllBanner();
        call.enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                if(response.isSuccessful()){
                    objlist.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return objlist;
    }
}
