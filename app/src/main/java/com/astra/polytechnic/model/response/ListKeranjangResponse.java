package com.astra.polytechnic.model.response;

import com.astra.polytechnic.model.Keranjang;
import com.astra.polytechnic.model.Koleksi;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListKeranjangResponse {
    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Keranjang> data;

    public ListKeranjangResponse(int result, String message, List<Keranjang> data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

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

    public List<Keranjang> getData() {
        return data;
    }

    public void setData(List<Keranjang> data) {
        this.data = data;
    }
}
