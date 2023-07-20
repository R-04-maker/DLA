package com.astra.polytechnic.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responses {
    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("result")
    @Expose
    private String result;

    public Responses() {
    }

    public Responses(String data, int status, String result) {
        this.data = data;
        this.status = status;
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data='" + data + '\'' +
                ", status='" + status + '\'' +
                ", result=" + result +
                '}';
    }
}
