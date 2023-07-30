package com.astra.polytechnic.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.api.ApiUtils;
import com.astra.polytechnic.dao.KeranjangDao;
import com.astra.polytechnic.dao.KoleksiDao;
import com.astra.polytechnic.model.Keranjang;
import com.astra.polytechnic.model.response.AddResponse;
import com.astra.polytechnic.model.response.ListKeranjangResponse;
import com.astra.polytechnic.model.response.ObjectResponse;
import com.astra.polytechnic.service.KeranjangService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangRepository {
    private static final String TAG = "keranjangRepository";
    private static KeranjangRepository INSTANCE;
    private KeranjangService mKeranjangService;
    private static KeranjangDao mKeranjangDao;

    private KeranjangRepository(Context context){
        mKeranjangService = ApiUtils.getKeranjang();
    }

    public static void initialize(Context context){
        if (INSTANCE == null) {
            mKeranjangDao = new KeranjangDao();
            INSTANCE = new KeranjangRepository(context);
        }
    }

    public static KeranjangRepository get(){
        return INSTANCE;
    }

    public LiveData<AddResponse> SaveKeranjang(Keranjang keranjang) {
        MutableLiveData<AddResponse> keranjangs = new MutableLiveData<>();

        Log.d(TAG, "addUser: Called");
        Call<AddResponse> call = mKeranjangService.addKeranjang(keranjang);
        call.enqueue(new Callback<AddResponse>() {
            @Override
            public void onResponse(Call<AddResponse> call, Response<AddResponse> response) {
                AddResponse addResponse= response.body();
                if(addResponse.getStatus() == 200){
                    Log.d(TAG, "onResponse: Data Berhasil Disimpan " + addResponse);
                }else {
                    Log.d(TAG, "onResponse: Data Gagal Disimpan " + addResponse);

                }
            }

            @Override
            public void onFailure(Call<AddResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return keranjangs;
    }

    public LiveData<List<Keranjang>> getAllKeranjang(String email){
        Log.i(TAG, "getDetailBook() called");

        Call<ListKeranjangResponse> call = mKeranjangService.getAllKeranjang(email);
        call.enqueue(new Callback<ListKeranjangResponse>() {
            @Override
            public void onResponse(Call<ListKeranjangResponse> call, Response<ListKeranjangResponse> response) {
                ListKeranjangResponse listKeranjangResponse = response.body();
                if (listKeranjangResponse.getResult() == 200) {
                    mKeranjangDao.setListKoleksi(listKeranjangResponse.getData());
                    Log.i(TAG, "onResponse: " + listKeranjangResponse);
                }
                else {
                    Log.i(TAG, "onResponse: " + listKeranjangResponse);
                }
            }

            @Override
            public void onFailure(Call<ListKeranjangResponse> call, Throwable t) {

            }
        });
        return mKeranjangDao.getListKeranjang();
    }

    public LiveData<AddResponse> DeleteKeranjang(int id){
        MutableLiveData<AddResponse> objlist = new MutableLiveData<>();
        Call<AddResponse> call = mKeranjangService.deleteKeranjang(id);
        call.enqueue(new Callback<AddResponse>() {
            @Override
            public void onResponse(Call<AddResponse> call, Response<AddResponse> response) {

            }

            @Override
            public void onFailure(Call<AddResponse> call, Throwable t) {

            }
        });
        return objlist;
    }
}
