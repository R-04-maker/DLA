package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Banner {
    @SerializedName("id_banner")
    @Expose
    private String id_banner;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("berkas")
    @Expose
    private String berkas;

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

    public Banner() {
    }

    public Banner(String id_banner, String nama, String berkas, int status, String creaby, String creadate, String modiby, String modidate) {
        this.id_banner = id_banner;
        this.nama = nama;
        this.berkas = berkas;
        this.status = status;
        this.creaby = creaby;
        this.creadate = creadate;
        this.modiby = modiby;
        this.modidate = modidate;
    }

    public String getId_banner() {
        return id_banner;
    }

    public void setId_banner(String id_banner) {
        this.id_banner = id_banner;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBerkas() {
        return berkas;
    }

    public void setBerkas(String berkas) {
        this.berkas = berkas;
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
        return "Banner{" +
                "id_banner='" + id_banner + '\'' +
                ", nama='" + nama + '\'' +
                ", berkas='" + berkas + '\'' +
                ", status=" + status +
                ", creaby='" + creaby + '\'' +
                ", creadate='" + creadate + '\'' +
                ", modiby='" + modiby + '\'' +
                ", modidate='" + modidate + '\'' +
                '}';
    }
}
