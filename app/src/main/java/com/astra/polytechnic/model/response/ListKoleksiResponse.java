package com.astra.polytechnic.model.response;

import com.astra.polytechnic.model.Koleksi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListKoleksiResponse {

    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Koleksi> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Koleksi> getData() {
        return data;
    }

    public void setData(List<Koleksi> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ListKoleksiResponse{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
