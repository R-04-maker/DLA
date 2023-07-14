package com.astra.polytechnic.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.model.LoginModel;
import com.astra.polytechnic.model.msuser;
import com.astra.polytechnic.model.response.LoginResponse;

import java.util.List;

public class UserDao {
    private static final String TAG = "Detail";

    private MutableLiveData<LoginResponse> mLoginResponse = new MutableLiveData<>();
    private MutableLiveData<LoginModel> mUserLogin = new MutableLiveData<>();
    private MutableLiveData<List<msuser>> listUser = new MutableLiveData<>();

    public LiveData<LoginResponse> getLogin() {
        return mLoginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        mLoginResponse.setValue(loginResponse);
    }

    public MutableLiveData<LoginModel> getUserLogin() {
        return mUserLogin;
    }

    public void setUserLogin(LoginModel userLogin) {
        mUserLogin.setValue(userLogin);
        Log.d(TAG, "setUserLogin: " + mUserLogin.getValue());
    }
}
