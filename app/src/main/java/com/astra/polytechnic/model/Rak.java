package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rak {
    @SerializedName("id_rak")
    @Expose
    private String id_rak;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

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

    public Rak() {
    }

    public Rak(String id_rak, String nama, String deskripsi, String status, String creaby, String creadate, String modiby, String modidate) {
        this.id_rak = id_rak;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.status = status;
        this.creaby = creaby;
        this.creadate = creadate;
        this.modiby = modiby;
        this.modidate = modidate;
    }

    public String getId_rak() {
        return id_rak;
    }

    public void setId_rak(String id_rak) {
        this.id_rak = id_rak;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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

    @Override
    public String toString() {
        return "Rak{" +
                "id_rak='" + id_rak + '\'' +
                ", nama='" + nama + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", status='" + status + '\'' +
                ", creaby='" + creaby + '\'' +
                ", creadate='" + creadate + '\'' +
                ", modiby='" + modiby + '\'' +
                ", modidate='" + modidate + '\'' +
                '}';
    }
}
