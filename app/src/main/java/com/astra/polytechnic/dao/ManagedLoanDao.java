package com.astra.polytechnic.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.model.Booking;

import java.util.List;

public class ManagedLoanDao {
    private static final String TAG = "ManagedLoanDao";
    private MutableLiveData<List<Object[]>> booking = new MutableLiveData<>();
    private MutableLiveData<List<Object[]>> detailProfileBooking = new MutableLiveData<>();
    private MutableLiveData<List<Object[]>> detailBooksBooking = new MutableLiveData<>();


    public LiveData<List<Object[]>> getListBooking(){
        return booking;
    }
    public void setListBooking(List<Object[]> bookingList){
        this.booking.setValue(bookingList);
    }
    public LiveData<List<Object[]>> getDetailBooking(){
        return detailProfileBooking;
    }
    public void setDetailBooking(List<Object[]> detail){
        this.detailProfileBooking.setValue(detail);
    }
    public LiveData<List<Object[]>> getBooksBooking(){
        return detailBooksBooking;
    }
    public void setBooksBooking(List<Object[]> detail){
        Object[] obj = detail.get(0);
        Log.d(TAG, "setBooksBooking: " + obj[2].toString());
        this.detailBooksBooking.setValue(detail);
    }
}
