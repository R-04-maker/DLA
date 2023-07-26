package com.astra.polytechnic.service;

import com.astra.polytechnic.model.msprodi;
import com.astra.polytechnic.model.response.ListProdiResponse;
import com.astra.polytechnic.model.response.ObjectResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface msprodiService {

    @GET("/getAllProdi")
    Call <List<msprodi>> getAllProdi();

}
