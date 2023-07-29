package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {
    @SerializedName("id_transaction")
    @Expose
    private String idTransaksi;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("bookingonline")
    @Expose
    private int idbooking;

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

    public Booking() {
    }

    public Booking(String idTransaksi, String email, int idbooking, String status, String creaby, String creadate, String modiby, String modidate, String nama, String nomor) {
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
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdbooking() {
        return idbooking;
    }

    public void setIdbooking(int idbooking) {
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
}
