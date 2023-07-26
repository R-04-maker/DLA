package com.astra.polytechnic.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.model.response.ListProdiResponse;
import com.astra.polytechnic.repository.msprodiRepository;
import com.astra.polytechnic.model.*;

import java.util.List;

public class msprodiViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";
    private msprodiRepository mRepository;

    public msprodiViewModel() {
        mRepository = msprodiRepository.get();
    }

    public LiveData<List<msprodi>> getAllProdi(){
        return mRepository.getAllProdi();
    }


}
