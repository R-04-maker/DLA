package com.astra.polytechnic.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.ManagedLoanViewModel;
import com.astra.polytechnic.helper.DLAHelper;
import com.astra.polytechnic.ui.activity.HistoryMemberActivity;
import com.astra.polytechnic.ui.activity.LoanDetailActivity;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BorrowedFragment extends Fragment {
    private static final String TAG = "BorrowedFragment";
    private RecyclerView mRvFinishedLoan;
    private View mEmptyData;
    private ManagedLoanViewModel mManagedLoanVM;
    Date date,date1,dateAfter14Days,datenow;
    String formattedDate,resultDateStr,formattedDate1,Denda;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private List<Object[]> mBookingList;
    private BookingAdapter mBookingAdapter = new BookingAdapter(Collections.emptyList());
    public static BorrowedFragment newInstance() {
        return new BorrowedFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManagedLoanVM = new ViewModelProvider(this).get(ManagedLoanViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_borrowed, container, false);
        mRvFinishedLoan = view.findViewById(R.id.rv_finished_booking);
        mRvFinishedLoan.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvFinishedLoan.setAdapter(mBookingAdapter);
        mEmptyData = view.findViewById(R.id.layout_empty_data_fin);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh();
    }
    private void refresh(){
        mManagedLoanVM.getBorrowedBooking().observe(getViewLifecycleOwner(), this::updateUI);
    }
    private void updateUI(List<Object[]> bookingList){
        mBookingList = DLAHelper.getUnconBookList(bookingList);
        if (mBookingList != null) {
            mBookingAdapter = new BookingAdapter(mBookingList);
            mRvFinishedLoan.setAdapter(mBookingAdapter);

            mEmptyData.setVisibility(mBookingList.size() == 0 ? View.VISIBLE : View.GONE);
            mRvFinishedLoan.setVisibility(mBookingList.size() == 0 ? View.GONE : View.VISIBLE);
        }else {
            mEmptyData.setVisibility(View.VISIBLE);
            mRvFinishedLoan.setVisibility(View.GONE);
        }
    }
    private class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder>{
        private List<Object[]> mBookingList;
        public BookingAdapter(List<Object[]> bookings){
            mBookingList = bookings;
        }

        @NonNull
        @Override
        public BookingAdapter.BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BookingAdapter.BookingHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(BookingAdapter.BookingHolder holder, int position) {
            Object[] booking = mBookingList.get(position);
            holder.bind(booking);
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
        }

        @Override
        public int getItemCount() {
            return mBookingList.size();
        }

        private class BookingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private TextView mName, mNIM, mDate, mBookID, mStatus;
            private Object[] mBooking;
            private String mEditableTitle;
            private MaterialCardView mMaterialCardView;
            public BookingHolder(LayoutInflater inflater,ViewGroup parent){
                super(inflater.inflate(R.layout.item_manage_loan, parent, false));

                mName = itemView.findViewById(R.id.cv_name);
                mNIM = itemView.findViewById(R.id.cv_nim);
                mDate = itemView.findViewById(R.id.cv_booking_date);
                mBookID = itemView.findViewById(R.id.book_id_loan);
                mStatus = itemView.findViewById(R.id.loan_status);
                mMaterialCardView = itemView.findViewById(R.id.materialCardView);
                mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.card_dipinjam));
                itemView.setOnClickListener(this);
            }
            public void bind(Object[] booking){
                mBooking = booking;

                if (booking != null){
                    mEmptyData.setVisibility(View.GONE);
                    mRvFinishedLoan.setVisibility(View.VISIBLE);
                    mEditableTitle = booking[10] != null ? booking[10].toString() : "";

                    if (mEditableTitle.length() > 15) {
                        mName.setText(mEditableTitle.substring(0, 15));
                    } else {
                        mName.setText(mEditableTitle);
                    }
                    DateTimeFormatter inputFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                    DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    mNIM.setText(booking[11] != null ? booking[11].toString() : "");
                    String tglambil = booking[12] != null ? booking[12].toString() : "";
                    String tglkembali =booking[13] != null ? booking[13].toString() : "";
                    OffsetDateTime offsettglambil = OffsetDateTime.parse((String) tglambil, inputFormat);
                    OffsetDateTime offsettglambil1 = OffsetDateTime.parse((String) tglkembali, inputFormat);
                    Instant instant = offsettglambil.toInstant();
                    Instant instant1 = offsettglambil1.toInstant();
                    date = Date.from(instant);
                    date1 = Date.from(instant1);
                    datenow=Date.from(Instant.now());

                    formattedDate = offsettglambil.format(outputFormat);
                    formattedDate1 = offsettglambil1.format(outputFormat);
                    mBookID.setText(booking[2] != null ? booking[2].toString() : "");

                    System.out.println("Parsed Tanggal Ambil: " + date);
                    System.out.println("Parsed Tanggal Kembali: " + date1);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DAY_OF_MONTH, 14);
                    dateAfter14Days = calendar.getTime();
                    resultDateStr = DATE_FORMAT.format(dateAfter14Days);
                    System.out.println("Date 14 days after the input date: " + resultDateStr);
                    // Menghitung selisih antara dateAfter21Days dan date dalam milidetik
                    long diffInMillis = dateAfter14Days.getTime() - date1.getTime();

                    // Mengubah selisih milidetik ke dalam satuan hari
                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);

                    if (booking[3].toString().equals("Dipinjam")) {
                        if (dateAfter14Days.after(datenow)) {
                            mStatus.setText(booking[3].toString());
                            mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.card_dipinjam));
                            mDate.setText(formattedDate);
                        }
                        else {
                            mStatus.setText("Denda");
                            mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.card_ditolak));
                            mDate.setText(formattedDate);
                        }
                    }


                }else {
                    mEmptyData.setVisibility(View.VISIBLE);
                    mRvFinishedLoan.setVisibility(View.GONE);
                }


            }

            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), LoanDetailActivity.class);
                intent.putExtra("id_booking", mBooking[2].toString());
                startActivity(intent);

            }
        }
    }
    @Override
    public void onResume() {
        Log.d(TAG, "onResume: Called");
        super.onResume();
        // Calling Data to Refresh Calling API
        refresh();
    }
    @Override
    public void onPause() {
        Log.d(TAG, "onPause: Called");
        super.onPause();
    }
}