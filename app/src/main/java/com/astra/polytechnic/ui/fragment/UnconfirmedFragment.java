package com.astra.polytechnic.ui.fragment;

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

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UnconfirmedFragment extends Fragment {
    private static final String TAG = "UnconfirmedFragment";
    private RecyclerView mRvUnconfirmedLoan;
    private ManagedLoanViewModel mManagedLoanVM;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private List<Booking> mBookingList;
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mManagedLoanVM.getUnconfirmedBooking().observe(getViewLifecycleOwner(), this::updateUI);
    }
    private void updateUI(List<Booking> bookingList){
        Log.d(TAG, "updateUI: " + bookingList);
        mBookingList = DLAHelper.getUnconBookList(bookingList);
        mBookingAdapter = new BookingAdapter(mBookingList);
        mRvUnconfirmedLoan.setAdapter(mBookingAdapter);
    }
    private class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder>{
        private List<Booking> mBookingList;
        public BookingAdapter(List<Booking> bookings){
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
            Booking booking = mBookingList.get(position);
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
            private Booking mBooking;
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
            public void bind(Booking booking){
                mBooking = booking;
                mEditableTitle = booking.getNama();
                if(mEditableTitle.length() > 15 ){
                    mName.setText(mEditableTitle.substring(0,15));
                }else {
                    mName.setText(booking.getNama());
                }
                mNIM.setText(booking.getNomor());
                String bookDate = DateConverter.fromDbDateTimeTo(DATE_FORMAT,booking.getCreadate());
                mDate.setText(bookDate.toString());
                mStatus.setText(booking.getStatus());
                mBookID.setText(booking.getIdbooking());
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), mBooking.getIdbooking(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + mBooking.getIdbooking());
            }
        }
    }
}