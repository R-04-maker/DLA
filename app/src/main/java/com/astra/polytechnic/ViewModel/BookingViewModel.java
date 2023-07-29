package com.astra.polytechnic.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.model.BookingDetail;
import com.astra.polytechnic.model.Keranjang;
import com.astra.polytechnic.model.response.AddResponse;
import com.astra.polytechnic.repository.BookingRepository;
import com.astra.polytechnic.repository.KeranjangRepository;

import java.util.List;

public class BookingViewModel extends ViewModel {
    private static final String TAG = "BookingViewModel";
    private BookingRepository mBookingRepository;

    public BookingViewModel() {
        mBookingRepository = BookingRepository.get();
    }

    public LiveData<List<Booking>> getBooking() {
        return mBookingRepository.getBooking();
    }

    public LiveData<AddResponse> saveBooking(Booking booking) {
        return mBookingRepository.SaveBooking(booking);
    }

    public LiveData<AddResponse> saveBookingDetail(BookingDetail bookingDetail) {
        return mBookingRepository.SaveBookingDetail(bookingDetail);
    }



}
