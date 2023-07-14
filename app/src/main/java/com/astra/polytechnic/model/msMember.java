package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class msMember {
    @SerializedName("mbr_nim")
    @Expose
    private String nim;

    @SerializedName("mbr_username")
    @Expose
    private String username;
    @SerializedName("mbr_notel")
    @Expose
    private String notelp;
    @SerializedName("mbr_jk")
    @Expose
    private String jkelamin;
    @SerializedName("mbr_password")
    @Expose
    private String password;
    @SerializedName("mbr_status")
    @Expose
    private String status;
    @SerializedName("mbr_created_date")
    @Expose
    private String created_date;
    @SerializedName("mbr_modf_by")
    @Expose
    private String modf_by;
    @SerializedName("mbr_modif_date")
    @Expose
    private String modif_date;

    public msMember(String nim, String username, String notelp, String jkelamin, String password, String status, String created_date, String modf_by, String modif_date) {
        this.nim = nim;
        this.username = username;
        this.notelp = notelp;
        this.jkelamin = jkelamin;
        this.password = password;
        this.status = status;
        this.created_date = created_date;
        this.modf_by = modf_by;
        this.modif_date = modif_date;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getJkelamin() {
        return jkelamin;
    }

    public void setJkelamin(String jkelamin) {
        this.jkelamin = jkelamin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModf_by() {
        return modf_by;
    }

    public void setModf_by(String modf_by) {
        this.modf_by = modf_by;
    }

    public String getModif_date() {
        return modif_date;
    }

    public void setModif_date(String modif_date) {
        this.modif_date = modif_date;
    }
}
