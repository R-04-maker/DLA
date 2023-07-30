package com.astra.polytechnic.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.model.Dashboard;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.repository.KoleksiRepository;

import java.util.List;

public class KoleksiViewModel extends ViewModel {
    private static final String TAG = "KoleksiViewModel";
    private KoleksiRepository mKoleksiRepository;

    public KoleksiViewModel(){
        mKoleksiRepository = KoleksiRepository.get();
    }
    public LiveData<List<Koleksi>> getNewest(){
        return mKoleksiRepository.getKoleksiNewest();
    }
    public LiveData<List<Koleksi>> getNewestReleased(){
        return mKoleksiRepository.getNewestReleased();
    }
    public LiveData<List<Object[]>> getDataDashboard(){
        return mKoleksiRepository.getDataDashboard();
    }
    public LiveData<List<Object[]>> getDetailBook(int id){
        return mKoleksiRepository.getDetailBook(id);
    }
    public LiveData<List<Object[]>> getDetailAtribut(int id){
        return mKoleksiRepository.getDetailAtribut(id);
    }
    public LiveData<List<Object[]>> getKlasifikasiDetail(int id){
        return mKoleksiRepository.getKlasifikasiDetail(id);
    }
    public LiveData<List<Koleksi>> getBukuByNama(){
        return mKoleksiRepository.getBukuByNama();
    }

    // nihh harus pake mutablelivedata biar dia bisa sambil dibaca datanya secara realtime sama activity
    public MutableLiveData<List<Koleksi>> getBukuByNamaMt(){
        return mKoleksiRepository.getBukuByNamaMt();
    }
}
