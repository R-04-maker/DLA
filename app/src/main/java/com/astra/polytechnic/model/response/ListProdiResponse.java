package com.astra.polytechnic.model.response;

import com.astra.polytechnic.model.msprodi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListProdiResponse {
    @SerializedName("result")
    @Expose
    String result;
    @SerializedName("data")
    @Expose
    private List<msprodi> data;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private int status;

    public ListProdiResponse( String result,List<msprodi> data, String message, int status) {
        this.result = result;
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<msprodi> getData() {
        return data;
    }

    public void setData(List<msprodi> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ListKolekasasdsiResponse{" +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
