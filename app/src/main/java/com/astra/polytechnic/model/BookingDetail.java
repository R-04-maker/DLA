package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.astra.polytechnic.model.*;
public class BookingDetail {

    @SerializedName("id_transactiondetail")
    @Expose
    private int idTransactionDetail;

    @SerializedName("id_transaction")
    @Expose
    private Booking idTransaction;

    @SerializedName("id_koleksi")
    @Expose
    private Koleksi idKoleksi;

    @SerializedName("tanggalpinjam")
    @Expose
    private String tanggalPinjam;

    @SerializedName("tanggalkembali")
    @Expose
    private String tanggalKembali;

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

    public BookingDetail() {
    }

    public BookingDetail(int idTransactionDetail, Booking idTransaction, Koleksi idKoleksi, String tanggalPinjam, String tanggalKembali, int status, String creaby, String creadate, String modiby, String modidate) {
        this.idTransactionDetail = idTransactionDetail;
        this.idTransaction = idTransaction;
        this.idKoleksi = idKoleksi;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
        this.status = status;
        this.creaby = creaby;
        this.creadate = creadate;
        this.modiby = modiby;
        this.modidate = modidate;
    }

    public int getIdTransactionDetail() {
        return idTransactionDetail;
    }

    public void setIdTransactionDetail(int idTransactionDetail) {
        this.idTransactionDetail = idTransactionDetail;
    }

    public Booking getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Booking idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Koleksi getIdKoleksi() {
        return idKoleksi;
    }

    public void setIdKoleksi(Koleksi idKoleksi) {
        this.idKoleksi = idKoleksi;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public String getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(String tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
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
}
