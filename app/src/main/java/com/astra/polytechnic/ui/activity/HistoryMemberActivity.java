package com.astra.polytechnic.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.ManagedLoanViewModel;
import com.astra.polytechnic.helper.DLAHelper;
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

public class HistoryMemberActivity extends AppCompatActivity {

    private static final String TAG = "HistoryActivity";
    private RecyclerView mRVallBooking;
    private ManagedLoanViewModel mManagedLoanViewModel;
    private List<Object[]> mDataList;
    private View mEmptyData;
    private ImageView mBackButton;
    String email, id_role;
    Date date,date1,dateAfter14Days,datenow;
    String formattedDate,resultDateStr,formattedDate1,Denda;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    private HistoryMemberActivity.HistoryAdapter mHistoryAdapter = new HistoryMemberActivity.HistoryAdapter(Collections.emptyList());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mRVallBooking = findViewById(R.id.rv_booking_history);

        mBackButton = findViewById(R.id.back_button);

        mEmptyData = findViewById(R.id.layout_empty_data_hist);

        mRVallBooking.setLayoutManager(new LinearLayoutManager(HistoryMemberActivity.this));

        mManagedLoanViewModel = new ViewModelProvider(this).get(ManagedLoanViewModel.class);

        SharedPreferences pref= getSharedPreferences("nomor", MODE_PRIVATE);

        email = pref.getString("email", null);
        id_role = pref.getString("id_role", null);
        mManagedLoanViewModel.getHistoryMember(email).observe(this,this::UpdateData);

    }

    private void UpdateData(List<Object[]> objectList){
        Log.d(TAG, "UpdateData: " + objectList);
        mDataList = DLAHelper.getAllHistoryMember(objectList);

        mHistoryAdapter = new HistoryMemberActivity.HistoryAdapter(mDataList);
        mRVallBooking.setAdapter(mHistoryAdapter);

        mEmptyData.setVisibility(mDataList.size() == 0 ? View.VISIBLE : View.GONE);
        mRVallBooking.setVisibility(mDataList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryMemberActivity.HistoryAdapter.HistoryHolder>{
        private List<Object[]> mHistoryList;
        public HistoryAdapter(List<Object[]> historyList){
            mHistoryList = historyList;
        }

        @NonNull
        @Override
        public HistoryMemberActivity.HistoryAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(HistoryMemberActivity.this);
            return new HistoryMemberActivity.HistoryAdapter.HistoryHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(HistoryMemberActivity.HistoryAdapter.HistoryHolder holder, int position) {
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
                DateTimeFormatter inputFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                mNIM.setText(booking[11] != null ? booking[11].toString() : "");
                String tglambil = booking[12].toString();
                String tglkembali =booking[13].toString();
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

                if (dateAfter14Days.after(datenow)) {
                    if (booking[3].toString().equals("Ditolak")) {
                        mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(HistoryMemberActivity.this, R.color.card_ditolak));
                    }  else if (booking[3].toString().equals("Pengajuan")) {
                        mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(HistoryMemberActivity.this, R.color.card_pengajuan));
                    }

                    if (booking[3].toString().equals("Dipinjam")) {
                        mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(HistoryMemberActivity.this, R.color.card_dipinjam));
                    } else if(booking[3].toString().equals("Diterima")){
                        mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(HistoryMemberActivity.this, R.color.card_diterima));
                    }
                    if (booking[3].toString().equals("Selesai")) {
                        mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(HistoryMemberActivity.this, R.color.card_selesai));
                    } else {

                    }
                    mStatus.setText(booking[3] != null ? booking[3].toString() : "");
                    mDate.setText(formattedDate);
                }
                else {
                    System.out.println("Denda");
                    mMaterialCardView.setCardBackgroundColor(ContextCompat.getColor(HistoryMemberActivity.this, R.color.card_ditolak));
                    mStatus.setText("Denda");
                    mDate.setText(formattedDate);
                }
            }

            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(HistoryMemberActivity.this, LoanDetailActivity.class);
                intent.putExtra("id_booking", mBooking[2].toString());
                startActivity(intent);
            }
        }
    }
    public void onBackButtonClicked(View view) {
        finish();
    }
}
