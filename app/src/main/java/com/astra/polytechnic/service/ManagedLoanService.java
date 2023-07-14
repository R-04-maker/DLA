package com.astra.polytechnic.service;

import com.astra.polytechnic.model.response.BookingResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ManagedLoanService {
    @GET("getUnconfirmedBooking")
    Call<BookingResponse> getUnconfirmedBook();
}
