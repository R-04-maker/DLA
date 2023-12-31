package com.astra.polytechnic.Aplication;

import android.app.Application;
import android.util.Log;

import com.astra.polytechnic.repository.*;
import com.astra.polytechnic.repository.ManagedLoanRepository;
import com.google.firebase.FirebaseApp;

public class ApplicationDLA extends Application {
    private  static final String TAG =  "UserApplication";

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(TAG, "UserApplication.onCreate() called");
        KoleksiRepository.initialize(this);
        ManagedLoanRepository.initialize(this);
        UserRepository.initialize(this);
        ProdiRepository.initialize(this);
        KeranjangRepository.initialize(this);
        BannerRepository.initialize(this);
        BookingRepository.initialize(this);
        FirebaseApp.initializeApp(this);
    }
}
