package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class Kategori {
    @SerializedName("id_kategori")
    @Expose
    private String id_kategori;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    @SerializedName("status")
    @Expose
    private int status;

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

    public Kategori() {
    }

    public Kategori(String id_kategori, String nama, String deskripsi, int status, String creaby, String creadate, String modiby, String modidate) {
        this.id_kategori = id_kategori;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.status = status;
        this.creaby = creaby;
        this.creadate = creadate;
        this.modiby = modiby;
        this.modidate = modidate;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
        return "Kategori{" +
                "id_kategori='" + id_kategori + '\'' +
                ", nama='" + nama + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", status=" + status +
                ", creaby='" + creaby + '\'' +
                ", creadate=" + creadate +
                ", modiby='" + modiby + '\'' +
                ", modidate=" + modidate +
                '}';
    }
}
