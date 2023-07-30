package com.astra.polytechnic.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.api.ApiUtils;
import com.astra.polytechnic.dao.KoleksiDao;
import com.astra.polytechnic.model.Dashboard;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.model.Prodi;
import com.astra.polytechnic.model.response.DashboardResponse;
import com.astra.polytechnic.model.response.ListKoleksiResponse;
import com.astra.polytechnic.service.KoleksiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KoleksiRepository {
    private static final String TAG = "KoleksiRepository";
    private static KoleksiRepository INSTANCE;
    private KoleksiService mKoleksiService;
    private static KoleksiDao mKoleksiDao;

    private KoleksiRepository(Context context){
        mKoleksiService = ApiUtils.getKoleksiService();
    }

    public static void initialize(Context context){
        if (INSTANCE == null) {
            mKoleksiDao = new KoleksiDao();
            INSTANCE = new KoleksiRepository(context);
        }
    }

    public static KoleksiRepository get(){
        return INSTANCE;
    }

    public LiveData<List<Koleksi>> getKoleksiNewest(){
        Log.i(TAG, "getKoleksiNewest() called");

        Call<ListKoleksiResponse> call = mKoleksiService.getNewestKoleksi();
        call.enqueue(new Callback<ListKoleksiResponse>() {
            @Override
            public void onResponse(Call<ListKoleksiResponse> call, Response<ListKoleksiResponse> response) {
                ListKoleksiResponse listKoleksiResponse = response.body();
                if(listKoleksiResponse.getResult() == 200){
                    mKoleksiDao.setListKoleksi(listKoleksiResponse.getData());
//                    List<Koleksi> obj = listKoleksiResponse.getData();
//                    Prodi prodi = obj.get(0).getIdProdi();
                    Log.d(TAG, "getKoleksiNewest.onResponse() called");
                }
            }

            @Override
            public void onFailure(Call<ListKoleksiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return mKoleksiDao.getListKoleksi();
    }

    public LiveData<List<Koleksi>> getNewestReleased(){
        Log.i(TAG, "getNewestReleased() called");

        Call<ListKoleksiResponse> call = mKoleksiService.getNewestReleased();
        call.enqueue(new Callback<ListKoleksiResponse>() {
            @Override
            public void onResponse(Call<ListKoleksiResponse> call, Response<ListKoleksiResponse> response) {
                ListKoleksiResponse listKoleksiResponse = response.body();
                if(listKoleksiResponse.getResult() == 200){
                    mKoleksiDao.setListNewestCollection(listKoleksiResponse.getData());
                    Log.d(TAG, "getNewestCollection.onResponse() called");
                }
            }

            @Override
            public void onFailure(Call<ListKoleksiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return mKoleksiDao.getListNewestCollection();
    }

    public LiveData<List<Object[]>> getDataDashboard(){
        Log.i(TAG, "getDataDashboard() called");
        MutableLiveData<List<Object[]>> objlist = new MutableLiveData<>();
        Call<List<Object[]>> call = mKoleksiService.getDataDashboard();
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if(response.isSuccessful()){
                    objlist.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {

            }
        });
        return objlist;
    }
    public LiveData<List<Object[]>> getDataDashboardMember(String email){
        Log.i(TAG, "getDataDashboardMember() called");
        MutableLiveData<List<Object[]>> objlist = new MutableLiveData<>();
        Call<List<Object[]>> call = mKoleksiService.getDataDashboardMember(email);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if(response.isSuccessful()){
                    objlist.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {

            }
        });
        return objlist;
    }
    public LiveData<List<Object[]>> getDetailBook(int id){
        Log.i(TAG, "getDetailBook() called");
        MutableLiveData<List<Object[]>> objlist = new MutableLiveData<>();
        Call<List<Object[]>> call = mKoleksiService.getDetailBook(id);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if(response.isSuccessful()){
                    objlist.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {

            }
        });
        return objlist;
    }
    public LiveData<List<Object[]>> getDetailAtribut(int id){
        Log.i(TAG, "getDetailAtribut() called");
        MutableLiveData<List<Object[]>> objlist = new MutableLiveData<>();
        Call<List<Object[]>> call = mKoleksiService.getDetailAtribut(id);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if(response.isSuccessful()){
                    objlist.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {

            }
        });
        return objlist;
    }
    public LiveData<List<Object[]>> getKlasifikasiDetail(int id){
        Log.i(TAG, "getDetailAtribut() called");
        MutableLiveData<List<Object[]>> objlist = new MutableLiveData<>();
        Call<List<Object[]>> call = mKoleksiService.getKlasifikasiDetail(id);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                if(response.isSuccessful()){
                    objlist.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {

            }
        });
        return objlist;
    }
    public LiveData<List<Koleksi>> getBukuByNama(){
        Log.i(TAG, "getBukuByNama() called");

        Call<ListKoleksiResponse> call = mKoleksiService.getBukuByNama();
        call.enqueue(new Callback<ListKoleksiResponse>() {
            @Override
            public void onResponse(Call<ListKoleksiResponse> call, Response<ListKoleksiResponse> response) {
                ListKoleksiResponse listKoleksiResponse = response.body();
                if(listKoleksiResponse.getResult() == 200){
                    mKoleksiDao.setBukuByNama(listKoleksiResponse.getData());
                    Log.d(TAG, "getBukuByNama.onResponse() called");
                }
            }

            @Override
            public void onFailure(Call<ListKoleksiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return mKoleksiDao.getListNewestCollection();
    }

    public MutableLiveData<List<Koleksi>> getBukuByNamaMt(){
        Log.i(TAG, "getBukuByNama() called");
        MutableLiveData<List<Koleksi>> respon = new MutableLiveData<>();
        Call<ListKoleksiResponse> call = mKoleksiService.getBukuByNama();
        call.enqueue(new Callback<ListKoleksiResponse>() {
            @Override
            public void onResponse(Call<ListKoleksiResponse> call, Response<ListKoleksiResponse> response) {
                ListKoleksiResponse listKoleksiResponse = response.body();
                if(listKoleksiResponse.getResult() == 200){
                    respon.setValue(listKoleksiResponse.getData());
                    Log.d(TAG, "getBukuByNama.onResponse() called");
                }
            }

            @Override
            public void onFailure(Call<ListKoleksiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return respon;
    }
}
