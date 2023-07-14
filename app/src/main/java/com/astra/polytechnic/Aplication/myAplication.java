package com.astra.polytechnic.Aplication;

import android.app.Application;
import android.util.Log;

import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.repository.KoleksiRepository;
import com.astra.polytechnic.repository.ManagedLoanRepository;
import com.astra.polytechnic.repository.msMemberRepo;
public class myAplication extends Application {
    private  static final String TAG =  "UserApplication";

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(TAG, "UserApplication.onCreate() called");
        msMemberRepo.initialize(this);
        KoleksiRepository.initialize(this);
        ManagedLoanRepository.initialize(this);
    }
}
