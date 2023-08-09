package com.astra.polytechnic.service;

import com.astra.polytechnic.model.msprodi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProdiService {

    @GET("/getAllProdi")
    Call <List<msprodi>> getAllProdi();

}
