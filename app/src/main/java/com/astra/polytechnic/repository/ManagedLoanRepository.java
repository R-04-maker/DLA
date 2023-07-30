package com.astra.polytechnic.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.api.ApiUtils;
import com.astra.polytechnic.dao.ManagedLoanDao;
//import com.astra.polytechnic.model.response.Response;
import com.astra.polytechnic.model.response.ObjectResponse;
import com.astra.polytechnic.model.response.Responses;
import com.astra.polytechnic.service.ManagedLoanService;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagedLoanRepository {
    private static final String TAG = "ManagedLoanRepository";
    private static ManagedLoanRepository INSTANCE;
    private ManagedLoanService mManagedLoanService;
    private static ManagedLoanDao mManagedLoanDao;
    private ManagedLoanRepository(Context context){
        mManagedLoanService = ApiUtils.getManagedLoanService();
    }
    public static void initialize(Context context){
        if (INSTANCE == null) {
            mManagedLoanDao = new ManagedLoanDao();
            INSTANCE = new ManagedLoanRepository(context);
        }
    }

    public static ManagedLoanRepository get(){
        return INSTANCE;
    }
    public LiveData<List<Object[]>> getUnconfirmedBook(){
        Log.i(TAG, "getUnconfirmedBook() called");

        Call<ObjectResponse> call = mManagedLoanService.getUnconfirmedBook();
        call.enqueue(new Callback<ObjectResponse>() {
            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if(objectResponse.getResult() == 200){
                    mManagedLoanDao.setListBooking(objectResponse.getData());
                    Log.d(TAG, "getUnconfirmedBook.onResponse() called");
                }else {
                    Log.d(TAG, "getUnconfirmedBook.onResponse() called : " + objectResponse.getData());
                }
            }
            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Log.e(TAG, "getUnconfirmedBook.onFailure: " + t.getMessage());
            }
        });
        return mManagedLoanDao.getListBooking();
    }
    public LiveData<List<Object[]>> getConfirmedBook(){
        Log.i(TAG, "getConfirmedBook() called");

        Call<ObjectResponse> call = mManagedLoanService.getConfirmedBook();
        call.enqueue(new Callback<ObjectResponse>() {
            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if(objectResponse.getResult() == 200){
                    mManagedLoanDao.setListBooking(objectResponse.getData());
                    Log.d(TAG, "getConfirmedBook.onResponse() called");
                }else {
                    Log.d(TAG, "getConfirmedBook.onResponse() called : " + objectResponse.getData());
                }
            }
            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Log.e(TAG, "getConfirmedBook.onFailure: " + t.getMessage());
            }
        });
        return mManagedLoanDao.getListBooking();
    }
    public LiveData<List<Object[]>> getBorrowedBooking(){
        Log.i(TAG, "getBorrowedBooking() called");

        Call<ObjectResponse> call = mManagedLoanService.getBorrowedBooking();
        call.enqueue(new Callback<ObjectResponse>() {
            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if(objectResponse.getResult() == 200){
                    mManagedLoanDao.setListBooking(objectResponse.getData());
                    Log.d(TAG, "getBorrowedBooking.onResponse() called");
                }else {
                    Log.d(TAG, "getBorrowedBooking.onResponse() called : " + objectResponse.getData());
                }
            }
            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Log.e(TAG, "getBorrowedBooking.onFailure: " + t.getMessage());
            }
        });
        return mManagedLoanDao.getListBooking();
    }
    public LiveData<List<Object[]>> getAllHistory(){
        Log.i(TAG, "getAllHistory() called");

        Call<ObjectResponse> call = mManagedLoanService.getFinishedBooking();
        call.enqueue(new Callback<ObjectResponse>() {
            @Override
            public void onResponse(Call<ObjectResponse> call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if(objectResponse.getResult() == 200){
                    mManagedLoanDao.setListBooking(objectResponse.getData());
                    Log.d(TAG, "getAllHistory.onResponse() called");
                }else {
                    Log.d(TAG, "getAllHistory.onResponse() called : " + objectResponse.getData());
                }
            }
            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Log.e(TAG, "getAllHistory.onFailure: " + t.getMessage());
            }
        });
        return mManagedLoanDao.getListBooking();
    }
    public LiveData<List<Object[]>> getDetailBooking(int id){
        Log.i(TAG, "getDetailBooking() called");

        Call<ObjectResponse> call = mManagedLoanService.getDetailBooking(id);
        call.enqueue(new Callback<ObjectResponse>() {
            @Override
            public void onResponse(Call<ObjectResponse>call, Response<ObjectResponse> response) {
                ObjectResponse objectResponse = response.body();
                if(objectResponse.getResult() == 200){
                    mManagedLoanDao.setDetailBooking(objectResponse.getData());
                    mManagedLoanDao.setBooksBooking(objectResponse.getListdata());
                    Log.d(TAG, "onResponse: getDetailBooking.onResponse() Called" + objectResponse.getListdata());
                }
            }
            @Override
            public void onFailure(Call<ObjectResponse> call, Throwable t) {
                Log.e(TAG, "getDetailBooking.onFailure: " + t.getMessage());
            }
        });
        return mManagedLoanDao.getDetailBooking();
    }
    public LiveData<List<Object[]>> getDetailBooking(){
        Log.d(TAG, "onResponse: getDetailBooking2.onResponse() Called");
        return mManagedLoanDao.getBooksBooking();
    }
    public LiveData<String> updateDetailBooking(int idBooking, String status){
        Log.d(TAG, "onResponse: updateDetailBooking Called");
        MutableLiveData<String> objlist = new MutableLiveData<>();
        Call<Responses> call = mManagedLoanService.updateDetailBooking(idBooking, status);
        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, retrofit2.Response<Responses> response) {
                Responses dataResponses = response.body();
                Log.d(TAG, "onResponse: updateDetailBooking.onResponse() with message " + dataResponses.getData());
                if (dataResponses.getStatus() == 200) {
                    objlist.setValue(dataResponses.getData());
                    Log.d(TAG, "onResponse: updateDetailBooking.onResponse() Called" + dataResponses.getData());
                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                Log.d(TAG, "onFailure: Failed when respon update");
            }
        });
        return objlist;
    }
    public LiveData<String> updateGambar(MultipartBody.Part multipartBody, String status, int id){
        Log.d(TAG, "updateGambar: Called" + multipartBody);
        MutableLiveData<String> objlist = new MutableLiveData<>();
        Call<Responses> call = mManagedLoanService.updateGambar(multipartBody,status, id);
        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, retrofit2.Response<Responses> response) {
                Responses dataResponses = response.body();
                Log.d(TAG, "onResponse.updateGambar: Called");
                if (dataResponses.getStatus() == 200) {
                    objlist.setValue(dataResponses.getData());
                    Log.d(TAG, "onResponse: updateDetailBooking.onResponse() Called" + dataResponses.getData());
                }else{
                    Log.d(TAG, "onResponse: Failed update Gambar");
                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                Log.d(TAG, "onFailure: Failed when respon update");
            }
        });
        return objlist;
    }
}
