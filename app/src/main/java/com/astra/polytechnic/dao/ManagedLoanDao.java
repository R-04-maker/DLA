package com.astra.polytechnic.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.model.Booking;

import java.util.List;

public class ManagedLoanDao {
    private static final String TAG = "ManagedLoanDao";
    private MutableLiveData<List<Booking>> booking = new MutableLiveData<>();
    public LiveData<List<Booking>> getListBooking(){
        return booking;
    }
    public void setListBooking(List<Booking> bookingList){
        this.booking.setValue(bookingList);
    }
}
