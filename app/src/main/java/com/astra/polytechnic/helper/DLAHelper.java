package com.astra.polytechnic.helper;

import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.model.Keranjang;
import com.astra.polytechnic.model.Koleksi;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DLAHelper {
    public static String getString(String text){
        return text != null ? text : "";
    }

    public static List<Koleksi> getNewestBook(List<Koleksi> koleksis){
        return koleksis == null ? Collections.emptyList() : koleksis.stream().limit(10).collect(Collectors.toList());
    }
    public static List<Keranjang> getKeranjang(List<Keranjang> keranjangs){
        return keranjangs == null ? Collections.emptyList() : keranjangs.stream().limit(10).collect(Collectors.toList());
    }
    public static List<Koleksi> getPopularBooks(List<Koleksi> koleksis){
        return koleksis == null ? Collections.emptyList() : koleksis.stream().limit(10).collect(Collectors.toList());
    }
    public static List<Object[]> getUnconBookList(List<Object[]> bookingList){
        return bookingList == null ? Collections.emptyList() : bookingList.stream().collect(Collectors.toList());
    }
    public static List<Object[]> getAllHistory(List<Object[]> historylist){
        return historylist == null ? Collections.emptyList() : historylist.stream().collect(Collectors.toList());
    }
    public static List<Object[]> getBookDetailList(List<Object[]> booklistdetail){
        return booklistdetail == null ? Collections.emptyList() : booklistdetail.stream().collect(Collectors.toList());
    }

}
