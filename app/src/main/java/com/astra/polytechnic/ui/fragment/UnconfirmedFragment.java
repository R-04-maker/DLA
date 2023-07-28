package com.astra.polytechnic.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.Element;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.astra.polytechnic.ViewModel.ManagedLoanViewModel;
import com.astra.polytechnic.helper.DLAHelper;
import com.astra.polytechnic.helper.DateConverter;
import com.astra.polytechnic.helper.DateType;
import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.ui.activity.LoanDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UnconfirmedFragment extends Fragment {
    private static final String TAG = "UnconfirmedFragment";
    private RecyclerView mRvUnconfirmedLoan;
    private View mEmptyData;
    private ManagedLoanViewModel mManagedLoanVM;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private List<Object[]> mBookingList;
    private BookingAdapter mBookingAdapter = new BookingAdapter(Collections.emptyList());
    public static UnconfirmedFragment newInstance() {
        return new UnconfirmedFragment();
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
        View view = inflater.inflate(R.layout.fragment_unconfirmed, container, false);
        mRvUnconfirmedLoan = view.findViewById(R.id.rv_unconfirmed_booking);
        mRvUnconfirmedLoan.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvUnconfirmedLoan.setAdapter(mBookingAdapter);
        mEmptyData = view.findViewById(R.id.layout_empty_data);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUnconfirmedData();
    }

    private void getUnconfirmedData(){
        mManagedLoanVM.getUnconfirmedBooking().observe(getViewLifecycleOwner(), this::updateUI);
    }

    private void updateUI(List<Object[]> bookingList){
        Log.d(TAG, "updateUI: " + bookingList.size());
        mBookingList = DLAHelper.getUnconBookList(bookingList);
        if (mBookingList != null) {
            mBookingAdapter = new BookingAdapter(mBookingList);
            mRvUnconfirmedLoan.setAdapter(mBookingAdapter);
            mBookingAdapter.notifyDataSetChanged();
            mEmptyData.setVisibility(mBookingList.size() == 0 ? View.VISIBLE : View.GONE);
            mRvUnconfirmedLoan.setVisibility(mBookingList.size() == 0 ? View.GONE : View.VISIBLE);
        }else {
            mEmptyData.setVisibility(View.VISIBLE);
            mRvUnconfirmedLoan.setVisibility(View.GONE);
        }

    }
    private class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder>{
        private List<Object[]> mBookingList;
        public BookingAdapter(List<Object[]> bookings){
            mBookingList = bookings;
        }

        @NonNull
        @Override
        public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BookingHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(BookingHolder holder, int position) {
            Object[] booking = mBookingList.get(position);
            holder.bind(booking);
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android .R.anim.fade_in);
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
            public BookingHolder(LayoutInflater inflater,ViewGroup parent){
                super(inflater.inflate(R.layout.item_manage_loan, parent, false));

                mName = itemView.findViewById(R.id.cv_name);
                mNIM = itemView.findViewById(R.id.cv_nim);
                mDate = itemView.findViewById(R.id.cv_booking_date);
                mBookID = itemView.findViewById(R.id.book_id_loan);
                mStatus = itemView.findViewById(R.id.loan_status);
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
//                Toast.makeText(getContext(), mBooking.getIdbooking(), Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(getActivity(), LoanDetailActivity.class);
                intent.putExtra("id_booking", mBooking[2].toString());
                intent.putExtra("status", mBooking[3].toString());
                startActivity(intent);

            }
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: Called");
        super.onResume();
        // Calling Data to Refresh Calling API
        getUnconfirmedData();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: Called");
        super.onPause();
    }
}