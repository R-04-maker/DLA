package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {
    @SerializedName("id_transaction")
    @Expose
    private int idTransaksi;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("bookingonline")
    @Expose
    private String idbooking;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("creaby")
    @Expose
    private String creaby;

    @SerializedName("creadate")
    @Expose
    private String creadate;

    @SerializedName("modiby")
    @Expose
    private String modiby;

    @SerializedName("modidate")
    @Expose
    private String modidate;

    // Nama get dari table msUser as a String only
    @SerializedName("nama")
    @Expose
    private String nama;

    // NIM atau NPK
    @SerializedName("nomor")
    @Expose
    private String nomor;

    @SerializedName("gambar")
    @Expose
    private String gambar;

    @SerializedName("gambar_sesudah")
    @Expose
    private String gambar_sesudah;

    public Booking() {
    }

    public Booking(int idTransaksi, String email, String idbooking, String status, String creaby, String creadate, String modiby, String modidate, String nama, String nomor, String gambar, String gambar_sesudah) {
        this.idTransaksi = idTransaksi;
        this.email = email;
        this.idbooking = idbooking;
        this.status = status;
        this.creaby = creaby;
        this.creadate = creadate;
        this.modiby = modiby;
        this.modidate = modidate;
        this.nama = nama;
        this.nomor = nomor;
        this.gambar = gambar;
        this.gambar_sesudah = gambar_sesudah;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdbooking() {
        return idbooking;
    }

    public void setIdbooking(String idbooking) {
        this.idbooking = idbooking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreaby() {
        return creaby;
    }

    public void setCreaby(String creaby) {
        this.creaby = creaby;
    }

    public String getCreadate() {
        return creadate;
    }

    public void setCreadate(String creadate) {
        this.creadate = creadate;
    }

    public String getModiby() {
        return modiby;
    }

    public void setModiby(String modiby) {
        this.modiby = modiby;
    }

    public String getModidate() {
        return modidate;
    }

    public void setModidate(String modidate) {
        this.modidate = modidate;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getGambar_sesudah() {
        return gambar_sesudah;
    }

    public void setGambar_sesudah(String gambar_sesudah) {
        this.gambar_sesudah = gambar_sesudah;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "idTransaksi=" + idTransaksi +
                ", email='" + email + '\'' +
                ", idbooking='" + idbooking + '\'' +
                ", status='" + status + '\'' +
                ", creaby='" + creaby + '\'' +
                ", creadate='" + creadate + '\'' +
                ", modiby='" + modiby + '\'' +
                ", modidate='" + modidate + '\'' +
                ", nama='" + nama + '\'' +
                ", nomor='" + nomor + '\'' +
                ", gambar='" + gambar + '\'' +
                ", gambar_sesudah='" + gambar_sesudah + '\'' +
                '}';
    }
}
