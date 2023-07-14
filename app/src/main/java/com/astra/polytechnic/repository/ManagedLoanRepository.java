package com.astra.polytechnic.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.astra.polytechnic.api.ApiUtils;
import com.astra.polytechnic.dao.ManagedLoanDao;
import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.model.response.BookingResponse;
import com.astra.polytechnic.model.response.ListKoleksiResponse;
import com.astra.polytechnic.service.ManagedLoanService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagedLoanRepository {
    private static final String TAG = "ManagedLoanRepository";
    private static ManagedLoanRepository INSTANCE;
    private ManagedLoanService mManagedLoanService;
    private static ManagedLoanDao mManagedLoanDao;
    private ManagedLoanRepository(Context context){
        mManagedLoanService = ApiUtils.getManagedLoanService();
    }
    public static void initialize(Context context){
        if (INSTANCE == null) {
            mManagedLoanDao = new ManagedLoanDao();
            INSTANCE = new ManagedLoanRepository(context);
        }
    }

    public static ManagedLoanRepository get(){
        return INSTANCE;
    }
    public LiveData<List<Booking>> getUnconfirmedBook(){
        Log.i(TAG, "getUnconfirmedBook() called");

        Call<BookingResponse> call = mManagedLoanService.getUnconfirmedBook();
        call.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                BookingResponse bookingResponse = response.body();
                if(bookingResponse.getResult() == 200){
                    mManagedLoanDao.setListBooking(bookingResponse.getData());
                    Log.d(TAG, "getUnconfirmedBook.onResponse() called");
                }else {
                    Log.d(TAG, "getUnconfirmedBook.onResponse() called : " + bookingResponse.getData());

                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "getUnconfirmedBook.onFailure: " + t.getMessage());
            }
        });
        return mManagedLoanDao.getListBooking();
    }
}
