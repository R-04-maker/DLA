package com.astra.polytechnic.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class ObjectResponse {
    @SerializedName("data")
    @Expose
    private List<Object[]> data;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("listdata")
    @Expose
    private List<Object[]> listdata;

    public ObjectResponse(List<Object[]> data, String message, int result, List<Object[]> listdata) {
        this.data = data;
        this.message = message;
        this.result = result;
        this.listdata = listdata;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int status) {
        this.result = status;
    }

    public List<Object[]> getListdata() {
        return listdata;
    }

    public void setListdata(List<Object[]> listdata) {
        this.listdata = listdata;
    }

    @Override
    public String toString() {
        return "ObjectResponse{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", listdata=" + listdata +
                '}';
    }
}
