package com.astra.polytechnic.service;

import com.astra.polytechnic.model.response.ObjectResponse;
import com.astra.polytechnic.model.response.Responses;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ManagedLoanService {
    @GET("getUnconfirmedBooking")
    Call<ObjectResponse> getUnconfirmedBook();
    @GET("getConfirmedBooking")
    Call<ObjectResponse> getConfirmedBook();
    @GET("getBorrowedBooking")
    Call<ObjectResponse> getBorrowedBooking();
    @GET("getDetailBooking/{id}")
    Call<ObjectResponse> getDetailBooking(@Path("id") int id);
    @GET("updatePengajuan/{id}/{status}")
    Call<Responses> updateDetailBooking(@Path("id") int id, @Path("status") String status);
/*    @POST("updateGambar")
    Call<Responses> updateGambar(@Query("encodedImage") String encodedImage); */
    @Multipart
    @POST("updateGambar")
    Call<Responses> updateGambar(@Part MultipartBody.Part file, @Part("status") String status, @Part("id") int id);
}