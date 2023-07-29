package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class msrole {
    @SerializedName("id_role")
    @Expose
    private String id_role;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("maksbuku")
    @Expose
    private int maksbuku;
    @SerializedName("makstempo")
    @Expose
    private int makstempo;

    public msrole() {
    }
    public msrole(String id_role, int status, int maksbuku, int makstempo) {
        this.id_role = id_role;
        this.status = status;
        this.maksbuku = maksbuku;
        this.makstempo = makstempo;
    }

    public String getId_role() {
        return id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMaksbuku() {
        return maksbuku;
    }

    public void setMaksbuku(int maksbuku) {
        this.maksbuku = maksbuku;
    }

    public int getMakstempo() {
        return makstempo;
    }

    public void setMakstempo(int makstempo) {
        this.makstempo = makstempo;
    }
}
