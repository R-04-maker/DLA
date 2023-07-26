package com.astra.polytechnic.Aplication;

import android.app.Application;
import android.util.Log;

import com.astra.polytechnic.repository.*;
import com.astra.polytechnic.repository.ManagedLoanRepository;

public class myAplication extends Application {
    private  static final String TAG =  "UserApplication";

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(TAG, "UserApplication.onCreate() called");
        KoleksiRepository.initialize(this);
        ManagedLoanRepository.initialize(this);
        msuserRepo.initialize(this);
        msprodiRepository.initialize(this);
        KeranjangRepository.initialize(this);
    }
}
