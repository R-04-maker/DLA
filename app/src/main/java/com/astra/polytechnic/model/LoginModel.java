package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nomor")
    @Expose
    private String nomor;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("id_role")
    @Expose
    private String id_role;

    @SerializedName("id_prodi")
    @Expose
    private String id_prodi;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    @SerializedName("hp")
    @Expose
    private String hp;

    public LoginModel(String email, String nomor, String nama, String id_role, String id_prodi, String password, String deskripsi, String hp) {
        this.email = email;
        this.nomor = nomor;
        this.nama = nama;
        this.id_role = id_role;
        this.id_prodi = id_prodi;
        this.password = password;
        this.deskripsi = deskripsi;
        this.hp = hp;
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

    public String getId_role() {
        return id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }

    public String getId_prodi() {
        return id_prodi;
    }

    public void setId_prodi(String id_prodi) {
        this.id_prodi = id_prodi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }
}
