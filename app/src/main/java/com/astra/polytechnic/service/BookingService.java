package com.astra.polytechnic.service;

import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.model.BookingDetail;
import com.astra.polytechnic.model.response.AddResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingService {

    @GET("/getIdBooking/{id}")
    Call<List<Booking>> getIdBooking(@Path("bookingonline") String id);

    @GET("/getBooking")
    Call<List<Booking>> getBooking();

    @POST("/saveBooking")
    Call<AddResponse> saveBooking(@Body Booking booking);

    @POST("/saveBookDetail")
    Call<AddResponse> saveBookingDetail(@Body BookingDetail booking);

    //delete
    @POST("//deleteBooking/{id}")
    Call<AddResponse> deleteBooking(@Path("id") String id);

}
