package com.astra.polytechnic.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.dao.UserDao;
import com.astra.polytechnic.model.LoginModel;
import com.astra.polytechnic.model.msuser;
import com.astra.polytechnic.model.response.AddResponse;
import com.astra.polytechnic.model.response.LoginResponse;
import com.astra.polytechnic.repository.msuserRepo;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";
    private UserDao mDetailViewModelMsuser;
    private msuserRepo mUserRepository;

    public UserViewModel() {
        mUserRepository = msuserRepo.get();
    }

    public LiveData<LoginResponse> login(String nim, String password) {
        return mUserRepository.Login(nim, password);
    }

    public LiveData<AddResponse> register(msuser user) {
        return mUserRepository.addUser(user);
    }

    public LiveData<LoginModel> getUserLogin(){
        return mDetailViewModelMsuser.getUserLogin();
    }

}
