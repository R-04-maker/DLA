package com.astra.polytechnic.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.model.msMember;
import com.astra.polytechnic.repository.msMemberRepo;

public class msMemberDetailViewModel extends ViewModel {
    private static final String TAG = "UserDetailViewModel";

    private LiveData<msMember> mUserLiveData;
    private msMemberRepo mMsMemberRepo;
    private MutableLiveData<String> mIdMutableLiveData;

    public msMemberDetailViewModel(){
        mMsMemberRepo = msMemberRepo.get();
        mIdMutableLiveData = new MutableLiveData<String>();
        mUserLiveData = Transformations.switchMap(mIdMutableLiveData,
                userId -> mMsMemberRepo.getUser(userId));
    }
    public void loadUser(String userId){
        Log.i(TAG, "loadUser() called");
        mIdMutableLiveData.setValue(userId);
    }

    public LiveData<msMember> getmUserLiveData(){
        Log.i(TAG, "getUserLiveData() called");
        return mUserLiveData;
    }

}
