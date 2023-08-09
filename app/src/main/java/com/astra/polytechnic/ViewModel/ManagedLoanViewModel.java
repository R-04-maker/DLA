package com.astra.polytechnic.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.dao.ManagedLoanDao;
import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.repository.ManagedLoanRepository;

import java.util.List;

import okhttp3.MultipartBody;

public class ManagedLoanViewModel extends ViewModel {
    private static final String TAG = "ManagedLoanViewModel";
    private ManagedLoanRepository mManagedLoanRepository;
    private ManagedLoanDao mManagedLoanDao = new ManagedLoanDao();
    public ManagedLoanViewModel(){
        mManagedLoanRepository = ManagedLoanRepository.get();
    }
    public LiveData<List<Object[]>> getUnconfirmedBooking(){
        return mManagedLoanRepository.getUnconfirmedBook();
    }
    public LiveData<List<Object[]>> getConfirmedBooking(){
        return mManagedLoanRepository.getConfirmedBook();
    }
    public LiveData<List<Object[]>> getBorrowedBooking(){
        return mManagedLoanRepository.getBorrowedBooking();
    }
    public LiveData<List<Object[]>> getAllHistory(){
        return mManagedLoanRepository.getAllHistory();
    }

    public LiveData<List<Object[]>> getHistoryMember(String email){
        return mManagedLoanRepository.getHistoryMember(email);
    }

    public LiveData<List<Object[]>> getValidasiAddKeranjang(String email){
        return mManagedLoanRepository.getValidasiAddKeranjang(email);
    }

    public LiveData<List<Object[]>> getDetailBooking(int id){
        return mManagedLoanRepository.getDetailBooking(id);
    }
    public LiveData<List<Object[]>> getBookDetailBooking(){
        return mManagedLoanRepository.getDetailBooking();
    }
    public LiveData<String> updateDetailBooking(int idBooking, String status){
        return mManagedLoanRepository.updateDetailBooking(idBooking, status);
    }
    public LiveData<String> updateGambar(MultipartBody.Part multipartBody, String status, int idBooking){
//        Log.d(TAG, "updateGambar: " + multipartBody + status + idBooking);
        return mManagedLoanRepository.updateGambar(multipartBody, status, idBooking);
    }
 }
