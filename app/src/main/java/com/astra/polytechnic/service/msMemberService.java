package com.astra.polytechnic.service;

import com.astra.polytechnic.model.msMember;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface msMemberService {
    @GET("getMembers/")
    Call<msMember> getUserById(@Query("nim") String nim);

    @GET("users")
    Call<List<msMember>> getUsers();

    @GET("/login")
    Call <msMember>Login(@Query("mbr_username")String username,@Query("mbr_password")String password);
    @POST("/saveMember")
    Call<msMember> addUser(@Body msMember member);

    @PUT("user")
    Call<msMember> updateUser(@Body msMember member);

    @DELETE("user")
    Call<msMember> deleteUserById(@Query("id") String id);


}
