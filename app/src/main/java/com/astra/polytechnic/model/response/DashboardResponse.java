package com.astra.polytechnic.model.response;

import com.astra.polytechnic.model.Dashboard;
import com.astra.polytechnic.model.Koleksi;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardResponse {

    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Dashboard data;

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

    public Dashboard getData() {
        return data;
    }

    public void setData(Dashboard data) {
        this.data = data;
    }

}
