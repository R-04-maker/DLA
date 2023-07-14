package com.astra.polytechnic.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.api.ApiUtils;
import com.astra.polytechnic.model.msMember;
import com.astra.polytechnic.service.msMemberService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class msMemberRepo{
    private static final String TAG = "UserRepository";

    private static msMemberRepo INSTANCE;

    private msMemberService mMsMemberService;

    private msMemberRepo(Context context) {
        mMsMemberService = ApiUtils.getMemberService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new msMemberRepo(context);
        }
    }

    public static msMemberRepo get() {
        return INSTANCE;
    }

    public MutableLiveData<List<msMember>> getUsers() {
        MutableLiveData<List<msMember>> users = new MutableLiveData<>();

        Call<List<msMember>> call = mMsMemberService.getUsers();
        call.enqueue(new Callback<List<msMember>>() {
            @Override
            public void onResponse(Call<List<msMember>> call, Response<List<msMember>> response) {
                if (response.isSuccessful()) {
                    users.setValue(response.body());
                    Log.d(TAG, "getmembers.onResponse() called");
                }
            }

            @Override
            public void onFailure(Call<List<msMember>> call, Throwable t) {
                Log.e("Error API cal : ", t.getMessage());
            }
        });

        return users;
    }
    public MutableLiveData<msMember> getUser(String userId) {
        MutableLiveData<msMember> user = new MutableLiveData<>();

        Call<msMember> call = mMsMemberService.getUserById(userId);
        call.enqueue(new Callback<msMember>() {
            @Override
            public void onResponse(Call<msMember> call, Response<msMember> response) {
                if (response.isSuccessful()) {
                    user.setValue(response.body());
                    Log.d(TAG, "getUserById.onResponse() called");
                }
            }

            @Override
            public void onFailure(Call<msMember> call, Throwable t) {
                Log.e("Error API call : ", t.getMessage());
            }
        });

        return user;
    }

    public void addUser(msMember member) {
        Log.i(TAG, "addUser() called");
        Call<msMember> call = mMsMemberService.addUser(member);
        call.enqueue(new Callback<msMember>() {
            @Override
            public void onResponse(Call<msMember> call, Response<msMember> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "User added " + member.getUsername());
                }
            }

            @Override
            public void onFailure(Call<msMember> call, Throwable t) {
                Log.e("Error API call : ", t.getMessage());
            }
        });
    }

    public void Login(String username,String password){

        Log.i(TAG, "Login() called");
        Call<msMember> call =mMsMemberService.Login(username,password);
        call.enqueue(new Callback<msMember>() {
            @Override
            public void onResponse(Call<msMember> call, Response<msMember> response) {
//                String perusahaan = user.getUsr_perusahaan();
                Log.d(TAG, "getUserById.onResponse() called");
            }

            @Override
            public void onFailure(Call<msMember> call, Throwable t) {
                Log.e("Error API call : ", t.getMessage());
            }
        });

        }
    }


