package com.astra.polytechnic.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.repository.ProdiRepository;
import com.astra.polytechnic.model.*;

import java.util.List;

public class ProdiViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";
    private ProdiRepository mRepository;

    public ProdiViewModel() {
        mRepository = ProdiRepository.get();
    }

    public LiveData<List<msprodi>> getAllProdi(){
        return mRepository.getAllProdi();
    }


}
