package com.astra.polytechnic.service;

import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.model.response.DashboardResponse;
import com.astra.polytechnic.model.response.ListKoleksiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface KoleksiService {
    @GET("getNewestCollection")
    Call<ListKoleksiResponse> getNewestCollection();

    @GET("getNewestReleased")
    Call<ListKoleksiResponse> getNewestReleased();

    @GET("getDataDashboard")
    Call<List<Object[]>> getDataDashboard();

    @GET("getDataDashboardMember/{email}")
    Call<List<Object[]>> getDataDashboardMember(@Path("email") String email);

    @GET("getDetailKoleksi/{id}")
    Call<List<Object[]>> getDetailBook(@Path("id") int idKoleksi);

    @GET("getDetailAtribut/{id}")
    Call<List<Object[]>> getDetailAtribut(@Path("id") int idKoleksi);

    @GET("getKlasifikasiDetail/{id}")
    Call<List<Object[]>> getKlasifikasiDetail(@Path("id") int idKoleksi);
    @GET("getBukuByNama")
    Call<ListKoleksiResponse> getBukuByNama();
}
