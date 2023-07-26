package com.astra.polytechnic.service;

import com.astra.polytechnic.model.msuser;
import com.astra.polytechnic.model.response.AddResponse;
import com.astra.polytechnic.model.response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface msuserService {
        @GET("/getAllUser")
        Call<msuser> getUserById(@Query("nim") String nim);

        @GET("users")
        Call<List<msuser>> getUsers();

        @GET("/login")
        Call <LoginResponse> Login(@Query("nomor")String nim, @Query("password")String password);
        @POST("/saveUsers")
        Call<AddResponse> addUser(@Body msuser user);

        @PUT("user")
        Call<msuser> updateUser(@Body msuser user);

        @DELETE("user")
        Call<msuser> deleteUserById(@Query("id") String id);


}
