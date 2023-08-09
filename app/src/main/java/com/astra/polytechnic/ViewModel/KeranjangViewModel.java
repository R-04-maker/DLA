package com.astra.polytechnic.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.model.*;
import com.astra.polytechnic.model.response.AddResponse;
import com.astra.polytechnic.model.response.ListKeranjangResponse;
import com.astra.polytechnic.model.response.ObjectResponse;
import com.astra.polytechnic.repository.*;

import java.util.List;

public class KeranjangViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";
    private KeranjangRepository mKeranjangRepository;

    public KeranjangViewModel() {
        mKeranjangRepository = KeranjangRepository.get();
    }

    public LiveData<AddResponse> addKeranjang(Keranjang keranjang) {
        return mKeranjangRepository.SaveKeranjang(keranjang);
    }

    public LiveData<List<Keranjang>> getKeranjangbyemail(String email) {
        return mKeranjangRepository.getAllKeranjang(email);
    }

    public LiveData<AddResponse> deleteKeranjang(int id) {
        return mKeranjangRepository.DeleteKeranjang(id);
    }


    public LiveData<AddResponse> cekKeranjang(String email, String idKoleksi) {
        return mKeranjangRepository.cekKeranjang(email,idKoleksi);
    }
}
