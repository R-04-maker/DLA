package com.astra.polytechnic.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.astra.polytechnic.model.Dashboard;
import com.astra.polytechnic.model.Koleksi;

import java.util.List;

public class KoleksiDao {
    private static final String TAG = "KoleksiDao";
    private MutableLiveData<List<Koleksi>> koleksi = new MutableLiveData<>();
    private MutableLiveData<List<Koleksi>> koleksi2 = new MutableLiveData<>();
    private MutableLiveData<List<Koleksi>> search = new MutableLiveData<>();
    private MutableLiveData<Dashboard> dataDashboard = new MutableLiveData<>();

    public LiveData<List<Koleksi>> getListKoleksi(){
        return koleksi;
    }
    public LiveData<List<Koleksi>> getListNewestCollection(){
        return koleksi2;
    }
    public LiveData<List<Koleksi>> getBukuByNama(){
        return search;
    }
    public LiveData<Dashboard> getDataDashboard() {
        return dataDashboard;
    }

    public void setListKoleksi(List<Koleksi> koleksi){
        this.koleksi.setValue(koleksi);
    }
    public void setBukuByNama(List<Koleksi> koleksi){
        this.search.setValue(koleksi);
    }
    public void setListNewestCollection(List<Koleksi> koleksi){
        this.koleksi2.setValue(koleksi);
    }
    public void setDataDashboard(Dashboard dataDashboard) {
        this.dataDashboard.setValue(dataDashboard);
    }

    public MutableLiveData<List<Koleksi>> getSearch() {
        return search;
    }

    public void setSearch(MutableLiveData<List<Koleksi>> search) {
        this.search = search;
    }
}
