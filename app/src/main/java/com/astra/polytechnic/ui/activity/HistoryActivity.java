package com.astra.polytechnic.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.ManagedLoanViewModel;
import com.astra.polytechnic.helper.DLAHelper;
import com.astra.polytechnic.helper.DateConverter;
import com.astra.polytechnic.ui.fragment.ConfirmedFragment;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = "HistoryActivity";
    private RecyclerView mRVallBooking;
    private ManagedLoanViewModel mManagedLoanViewModel;
    private List<Object[]> mDataList;
    private View mEmptyData;
    private ImageView mBackButton;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private HistoryAdapter mHistoryAdapter = new HistoryAdapter(Collections.emptyList());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mRVallBooking = findViewById(R.id.rv_booking_history);

        mBackButton = findViewById(R.id.back_button);

        mEmptyData = findViewById(R.id.layout_empty_data_hist);

        mRVallBooking.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));

        mManagedLoanViewModel = new ViewModelProvider(this).get(ManagedLoanViewModel.class);

        mManagedLoanViewModel.getAllHistory().observe(this,this::UpdateData);

    }

    private void UpdateData(List<Object[]> objectList){
        Log.d(TAG, "UpdateData: " + objectList);
        mDataList = DLAHelper.getAllHistory(objectList);
        mHistoryAdapter = new HistoryAdapter(mDataList);
        mRVallBooking.setAdapter(mHistoryAdapter);

        mEmptyData.setVisibility(mDataList.size() == 0 ? View.VISIBLE : View.GONE);
        mRVallBooking.setVisibility(mDataList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder>{
        private List<Object[]> mHistoryList;
        public HistoryAdapter(List<Object[]> historyList){
            mHistoryList = historyList;
        }

        @NonNull
        @Override
        public HistoryAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(HistoryActivity.this);
            return new HistoryAdapter.HistoryHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(HistoryAdapter.HistoryHolder holder, int position) {
            Object[] historydata = mHistoryList.get(position);
            holder.bind(historydata);
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
        }

        @Override
        public int getItemCount() {
            return mHistoryList.size();
        }

        private class HistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private TextView mName, mNIM, mDate, mBookID, mStatus;
            private Object[] mBooking;
            private String mEditableTitle;
            private MaterialCardView mMaterialCardView;
            public HistoryHolder(LayoutInflater inflater,ViewGroup parent){
                super(inflater.inflate(R.layout.item_manage_loan, parent, false));

                mName = itemView.findViewById(R.id.cv_name);
                mNIM = itemView.findViewById(R.id.cv_nim);
                mDate = itemView.findViewById(R.id.cv_booking_date);
                mBookID = itemView.findViewById(R.id.book_id_loan);
                mStatus = itemView.findViewById(R.id.loan_status);
                mMaterialCardView = itemView.findViewById(R.id.materialCardView);
                mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(HistoryActivity.this, R.color.card_selesai));
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
                Intent intent  = new Intent(HistoryActivity.this, LoanDetailActivity.class);
                intent.putExtra("id_booking", mBooking[2].toString());
                startActivity(intent);

            }
        }
    }
    public void onBackButtonClicked(View view) {
        // Kembali ke activity sebelumnya
        finish();
    }
}