package com.astra.polytechnic.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddResponse {
    @SerializedName("result")
    @Expose
    String result;

    @SerializedName("status")
    @Expose
    private int status;

    public AddResponse(String result, int status) {
        this.result = result;
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AddResponse{" +
                ", status=" + status +
                '}';
    }
}
