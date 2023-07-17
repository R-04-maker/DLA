package com.astra.polytechnic.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.dao.ManagedLoanDao;
import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.repository.ManagedLoanRepository;

import java.util.List;

public class ManagedLoanViewModel extends ViewModel {
    private static final String TAG = "ManagedLoanViewModel";
    private ManagedLoanRepository mManagedLoanRepository;
    private ManagedLoanDao mManagedLoanDao = new ManagedLoanDao();
    public ManagedLoanViewModel(){
        mManagedLoanRepository = ManagedLoanRepository.get();
    }
    public LiveData<List<Booking>> getUnconfirmedBooking(){
        return mManagedLoanRepository.getUnconfirmedBook();
    }
    public LiveData<List<Object[]>> getDetailBooking(int id){
        return mManagedLoanRepository.getDetailBooking(id);
    }
    public LiveData<List<Object[]>> getBookDetailBooking(){
        return mManagedLoanRepository.getDetailBooking();
    }
}
