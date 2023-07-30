package com.astra.polytechnic.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.model.Banner;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.repository.BannerRepository;

import java.util.List;

public class BannerViewModel extends ViewModel {
    private static final String TAG = "BannerViewModel";
    private BannerRepository mBannerRepository;
    public BannerViewModel(){
        mBannerRepository = BannerRepository.get();
    }
    public LiveData<List<Banner>> getAllBanner(){
        return mBannerRepository.getAllBanner();
    }
}
