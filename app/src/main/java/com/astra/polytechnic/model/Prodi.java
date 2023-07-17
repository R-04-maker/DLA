package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prodi {
    @SerializedName("id_prodi")
    @Expose
    private String id_prodi;

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

    public Prodi() {
    }

    public Prodi(String id_prodi, String deskripsi, String status, String creaby, String creadate, String modiby, String modidate) {
        this.id_prodi = id_prodi;
        this.deskripsi = deskripsi;
        this.status = status;
        this.creaby = creaby;
        this.creadate = creadate;
        this.modiby = modiby;
        this.modidate = modidate;
    }

    public String getId_prodi() {
        return id_prodi;
    }

    public void setId_prodi(String id_prodi) {
        this.id_prodi = id_prodi;
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
        return "Prodi{" +
                "id_prodi='" + id_prodi + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", status='" + status + '\'' +
                ", creaby='" + creaby + '\'' +
                ", creadate='" + creadate + '\'' +
                ", modiby='" + modiby + '\'' +
                ", modidate='" + modidate + '\'' +
                '}';
    }
}
