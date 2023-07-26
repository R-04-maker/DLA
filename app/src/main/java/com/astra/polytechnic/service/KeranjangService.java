package com.astra.polytechnic.service;

import com.astra.polytechnic.model.Keranjang;
import com.astra.polytechnic.model.response.AddResponse;
import com.astra.polytechnic.model.response.ListKeranjangResponse;
import com.astra.polytechnic.model.response.ObjectResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KeranjangService {

    @POST("/saveKeranjang")
    Call<AddResponse> addKeranjang(@Body Keranjang keranjang);

    @GET("/getAllKeranjang/{email}")
    Call<ListKeranjangResponse> getAllKeranjang(@Path("email") String email);

    @POST("/deleteKeranjang/{id}")
    Call<AddResponse> deleteKeranjang(@Path("id") String id);



}
