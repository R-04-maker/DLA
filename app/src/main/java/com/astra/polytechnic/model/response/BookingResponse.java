package com.astra.polytechnic.model.response;

import com.astra.polytechnic.model.Booking;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingResponse {
    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Booking> data;

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

    public List<Booking> getData() {
        return data;
    }

    public void setData(List<Booking> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BookingResponse{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
