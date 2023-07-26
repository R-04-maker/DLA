package com.astra.polytechnic.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.model.Dashboard;
import com.astra.polytechnic.model.Keranjang;
import com.astra.polytechnic.model.Koleksi;

import java.util.List;

public class KeranjangDao {
    private static final String TAG = "KoleksiDao";
    private MutableLiveData<List<Keranjang>> keranjang = new MutableLiveData<>();
    private MutableLiveData<List<Keranjang>> keranjang2 = new MutableLiveData<>();
    private MutableLiveData<Dashboard> dataDashboard = new MutableLiveData<>();

    public LiveData<List<Keranjang>> getListKeranjang(){
        return keranjang;
    }
    public LiveData<List<Keranjang>> getListNewestCollection(){
        return keranjang2;
    }
    public LiveData<Dashboard> getDataDashboard() {
        return dataDashboard;
    }

    public void setListKoleksi(List<Keranjang> keranjangs){
        this.keranjang.setValue(keranjangs);
    }
    public void setListNewestCollection(List<Keranjang> keranjangs){
        this.keranjang2.setValue(keranjangs);
    }
    public void setDataDashboard(Dashboard dataDashboard) {
        this.dataDashboard.setValue(dataDashboard);
    }
}
