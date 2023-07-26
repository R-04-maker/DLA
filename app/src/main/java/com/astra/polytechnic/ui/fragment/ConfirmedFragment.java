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
import com.astra.polytechnic.helper.DateConverter;
import com.astra.polytechnic.ui.activity.LoanDetailActivity;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class ConfirmedFragment extends Fragment {
    private static final String TAG = "ConfirmedFragment";
    private RecyclerView mRvConfirmedLoan;
    private View mEmptyData;
    private ManagedLoanViewModel mManagedLoanVM;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private List<Object[]> mBookingList;
    private BookingAdapter mBookingAdapter = new BookingAdapter(Collections.emptyList());
    public static ConfirmedFragment newInstance() {
        return new ConfirmedFragment();
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
        View view = inflater.inflate(R.layout.fragment_confirmed, container, false);
        mRvConfirmedLoan = view.findViewById(R.id.rv_confirmed_booking);
        mRvConfirmedLoan.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvConfirmedLoan.setAdapter(mBookingAdapter);
        mEmptyData = view.findViewById(R.id.layout_empty_data_con);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh();
    }
    private void refresh(){
        mManagedLoanVM.getConfirmedBooking().observe(getViewLifecycleOwner(), this::updateUI);
    }
    private void updateUI(List<Object[]> bookingList){
        Log.d(TAG, "updateUI: " + bookingList);
        mBookingList = DLAHelper.getUnconBookList(bookingList);
        mBookingAdapter = new ConfirmedFragment.BookingAdapter(mBookingList);
        mRvConfirmedLoan.setAdapter(mBookingAdapter);

        mEmptyData.setVisibility(mBookingList.size() == 0 ? View.VISIBLE : View.GONE);
        mRvConfirmedLoan.setVisibility(mBookingList.size() == 0 ? View.GONE : View.VISIBLE);
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
                mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.card_diterima));
                itemView.setOnClickListener(this);
            }
            public void bind(Object[] booking){
                mBooking = booking;
                mEditableTitle = booking[10] != null ? booking[10].toString() : "";

                if (mEditableTitle.length() > 15) {
                    mName.setText(mEditableTitle.substring(0, 15));
                } else {
                    mName.setText(mEditableTitle);
                }

                mNIM.setText(booking[11] != null ? booking[11].toString() : "");
                String bookDate = booking[5] != null ? DateConverter.fromDbDateTimeTo(DATE_FORMAT, booking[5].toString()) : "";
                mDate.setText(bookDate);
                mStatus.setText(booking[3] != null ? booking[3].toString() : "");
                mBookID.setText(booking[2] != null ? booking[2].toString() : "");
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