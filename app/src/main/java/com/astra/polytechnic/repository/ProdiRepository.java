package com.astra.polytechnic.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.api.ApiUtils;
import com.astra.polytechnic.service.*;
import com.astra.polytechnic.model.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdiRepository {

    private static final String TAG = "msprodiRepository";

    private static ProdiRepository INSTANCE;

    private ProdiRepository mRepository;
    private ProdiService mService;
    private ProdiRepository(Context context) {
        mService = ApiUtils.getAllProdi();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ProdiRepository(context);
        }
    }

    public static ProdiRepository get() {
        return INSTANCE;
    }

    public LiveData<List<msprodi>> getAllProdi() {
        MutableLiveData<List<msprodi>> prodi = new MutableLiveData<>();

        Call<List<msprodi>> call = mService.getAllProdi();
        call.enqueue(new Callback<List<msprodi>>() {
            @Override
            public void onResponse(Call<List<msprodi>> call, Response<List<msprodi>> response) {
                if (response.isSuccessful()) {
                    prodi.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<msprodi>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

        return prodi;
    }
}
