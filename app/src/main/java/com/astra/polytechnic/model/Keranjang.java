package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keranjang {

    @SerializedName("id_keranjang")
    @Expose
    private int id_keranjang;

    @SerializedName("id_koleksi")
    @Expose
    private Koleksi idKoleksi;

    @SerializedName("email")
    @Expose
    private msuser email;

    public Keranjang() {
    }

    public Keranjang(int id_keranjang, Koleksi idKoleksi, msuser email) {
        this.id_keranjang = id_keranjang;
        this.idKoleksi = idKoleksi;
        this.email = email;
    }

    public int getId_keranjang() {
        return id_keranjang;
    }

    public void setId_keranjang(int id_keranjang) {
        this.id_keranjang = id_keranjang;
    }

    public Koleksi getIdKoleksi() {
        return idKoleksi;
    }

    public void setIdKoleksi(Koleksi idKoleksi) {
        this.idKoleksi = idKoleksi;
    }

    public msuser getEmail() {
        return email;
    }

    public void setEmail(msuser email) {
        this.email = email;
    }
}
