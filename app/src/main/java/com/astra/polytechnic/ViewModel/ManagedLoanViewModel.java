package com.astra.polytechnic.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.repository.ManagedLoanRepository;

import java.util.List;

public class ManagedLoanViewModel extends ViewModel {
    private static final String TAG = "ManagedLoanViewModel";
    private ManagedLoanRepository mManagedLoanRepository;
    public ManagedLoanViewModel(){
        mManagedLoanRepository = ManagedLoanRepository.get();
    }
    public LiveData<List<Booking>> getUnconfirmedBooking(){
        return mManagedLoanRepository.getUnconfirmedBook();
    }
}
