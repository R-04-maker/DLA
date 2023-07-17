package com.astra.polytechnic.service;

import com.astra.polytechnic.model.response.BookingResponse;
import com.astra.polytechnic.model.response.ObjectResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ManagedLoanService {
    @GET("getUnconfirmedBooking")
    Call<BookingResponse> getUnconfirmedBook();
    @GET("getDetailBooking/{id}")
    Call<ObjectResponse> getDetailBooking(@Path("id") int id);
}
