package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dashboard {
    @SerializedName("book_count")
    @Expose
    private String bookCount;
    @SerializedName("visitor_count")
    @Expose
    private String visitorCount;
    @SerializedName("history_count")
    @Expose
    private String historyCount;

    public Dashboard() {
    }

    public Dashboard(String bookCount, String visitorCount, String historyCount) {
        this.bookCount = bookCount;
        this.visitorCount = visitorCount;
        this.historyCount = historyCount;
    }

    public String getBookCount() {
        return bookCount;
    }

    public void setBookCount(String bookCount) {
        this.bookCount = bookCount;
    }

    public String getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(String visitorCount) {
        this.visitorCount = visitorCount;
    }

    public String getHistoryCount() {
        return historyCount;
    }

    public void setHistoryCount(String historyCount) {
        this.historyCount = historyCount;
    }

    @Override
    public String toString() {
        return "Dashboard{" +
                "bookCount='" + bookCount + '\'' +
                ", visitorCount='" + visitorCount + '\'' +
                ", historyCount='" + historyCount + '\'' +
                '}';
    }
}
