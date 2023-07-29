package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class msuser {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("nomor")
    @Expose
    private String nomor;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("instansi")
    @Expose
    private String instansi;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("hp")
    @Expose
    private String hp;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("id_role")
    @Expose
    private msrole id_role;

    @SerializedName("id_prodi")
    @Expose
    private msprodi id_prodi;

    @SerializedName("status")
    @Expose
    private Integer status;

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

    public msuser() {
    }

    public msuser(String email, String nomor, String nama, String instansi, String alamat, String hp, String password, msrole id_role, msprodi id_prodi, Integer status, String creaby, String creadate, String modiby, String modidate) {
        this.email = email;
        this.nomor = nomor;
        this.nama = nama;
        this.instansi = instansi;
        this.alamat = alamat;
        this.hp = hp;
        this.password = password;
        this.id_role = id_role;
        this.id_prodi = id_prodi;
        this.status = status;
        this.creaby = creaby;
        this.creadate = creadate;
        this.modiby = modiby;
        this.modidate = modidate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public msrole getId_role() {
        return id_role;
    }

    public void setId_role(msrole id_role) {
        this.id_role = id_role;
    }

    public msprodi getId_prodi() {
        return id_prodi;
    }

    public void setId_prodi(msprodi id_prodi) {
        this.id_prodi = id_prodi;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
