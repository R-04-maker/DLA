package com.astra.polytechnic.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.api.ApiUtils;
import com.astra.polytechnic.dao.KeranjangDao;
import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.model.BookingDetail;
import com.astra.polytechnic.model.Keranjang;
import com.astra.polytechnic.model.response.AddResponse;
import com.astra.polytechnic.service.BookingService;
import com.astra.polytechnic.service.KeranjangService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingRepository {

    private static final String TAG = "keranjangRepository";
    private static BookingRepository INSTANCE;
    private BookingService mBookingService;
    private static KeranjangDao mKeranjangDao;

    private BookingRepository(Context context){
        mBookingService = ApiUtils.getBooking();
    }

    public static void initialize(Context context){
        if (INSTANCE == null) {
            INSTANCE = new BookingRepository(context);
        }
    }

    public static BookingRepository get(){
        return INSTANCE;
    }

    public LiveData<List<Booking>> getBooking() {
        MutableLiveData<List<Booking>> booking = new MutableLiveData<>();

        Log.d(TAG, "GEt: Called");
        Call<List<Booking>> call = mBookingService.getBooking();
        call.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                List<Booking> addResponse= response.body();
                Log.d(TAG, "onResponse:  " + addResponse);
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return booking;
    }

    public LiveData<AddResponse> SaveBooking(Booking booking) {
        MutableLiveData<AddResponse> booking1 = new MutableLiveData<>();

        Log.d(TAG, "addUser: Called");
        Call<AddResponse> call = mBookingService.saveBooking(booking);
        call.enqueue(new Callback<AddResponse>() {
            @Override
            public void onResponse(Call<AddResponse> call, Response<AddResponse> response) {
                Log.d(TAG, "onResponse:  " + response.body());
            }

            @Override
            public void onFailure(Call<AddResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return booking1;
    }

    public LiveData<AddResponse> SaveBookingDetail(BookingDetail booking) {
        MutableLiveData<AddResponse> booking1 = new MutableLiveData<>();

        Log.d(TAG, "methodSvBookDetail: Called");
        Call<AddResponse> call = mBookingService.saveBookingDetail(booking);
        call.enqueue(new Callback<AddResponse>() {
            @Override
            public void onResponse(Call<AddResponse> call, Response<AddResponse> response) {
                Log.d(TAG, "onResponse:  " + response.body());
            }

            @Override
            public void onFailure(Call<AddResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return booking1;
    }

}
