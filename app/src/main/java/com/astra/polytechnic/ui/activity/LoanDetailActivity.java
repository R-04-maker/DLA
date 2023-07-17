package com.astra.polytechnic.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.ManagedLoanViewModel;
import com.astra.polytechnic.helper.DLAHelper;
import com.astra.polytechnic.helper.DateConverter;
import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.ui.fragment.UnconfirmedFragment;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class LoanDetailActivity extends AppCompatActivity {
    private static final String TAG = "LoanDetailActivity";
    private String mIdBooking,mAmountBooks;
    private TextView mStatus,mBookingId,mBookingDate,mBookingReturn,mNIM,mName,mProdi,mHp,mAmount;
    private RecyclerView mRVDetailBooks;
    private ManagedLoanViewModel mManagedLoanViewModel;
    private List<Object[]> mDetailBooksList;
    private BooksDetailAdapter mBooksDetailAdapter = new BooksDetailAdapter(Collections.emptyList());
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_detail);
        mIdBooking = getIntent().getStringExtra("id_booking");
        Log.d(TAG, "onCreate: " + mIdBooking);
        // Initialize Component
        mStatus = findViewById(R.id.loan_status);
        mBookingId = findViewById(R.id.booking_code_req_detail);
        mBookingDate = findViewById(R.id.booking_date);
        mBookingReturn = findViewById(R.id.booking_return_date);
        mNIM = findViewById(R.id.nim);
        mName = findViewById(R.id.name_profile);
        mProdi = findViewById(R.id.prodi);
        mHp = findViewById(R.id.noHp);
//        mAmount = findViewById(R.id.amount);

        mRVDetailBooks = findViewById(R.id.rv_detail_loan);
        mRVDetailBooks.setLayoutManager(new LinearLayoutManager(this));
        mRVDetailBooks.setAdapter(mBooksDetailAdapter);

        mManagedLoanViewModel = new ViewModelProvider(this).get(ManagedLoanViewModel.class);

        mManagedLoanViewModel.getDetailBooking(Integer.parseInt(mIdBooking)).observe(this,objects -> {
            Object[] obj = objects.get(0);
            Log.d(TAG, "getDetailBooking : " + obj[0].toString() + "Index : " + obj.length);
            mBookingId.setText(obj[1].toString());
            mStatus.setText(obj[2].toString());
            mBookingDate.setText(DateConverter.fromDbDateTimeTo(DATE_FORMAT, obj[3].toString()).toString());
            mBookingReturn.setText(DateConverter.fromDbDateTimeTo(DATE_FORMAT,obj[4].toString()).toString());
            mNIM.setText(obj[5].toString());
            mName.setText(obj[6].toString());
            mProdi.setText(obj[8].toString());
            mHp.setText(obj[9].toString());
        });

        mManagedLoanViewModel.getBookDetailBooking().observe(this,this::UpdateDetailBooks);

    }
    private void UpdateDetailBooks(List<Object[]> listdata){
        Log.d(TAG, "UpdateDetailBooks: " + listdata);
        mDetailBooksList = DLAHelper.getBookDetailList(listdata);
        mBooksDetailAdapter = new BooksDetailAdapter(mDetailBooksList);
        mRVDetailBooks.setAdapter(mBooksDetailAdapter);
//        Log.d(TAG, "UpdateDetailBooks: getAmount " + mAmountBooks);
//        mAmount.setText(mAmountBooks);
    }
    private class BooksDetailAdapter extends RecyclerView.Adapter<BooksDetailAdapter.BooksDetailHolder>{
        private List<Object[]> mBooksList;
        public BooksDetailAdapter(List<Object[]> booksList){
            mBooksList = booksList;
        }

        @NonNull
        @Override
        public BooksDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(LoanDetailActivity.this);
            return new BooksDetailHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(BooksDetailHolder holder, int position) {
            Object[] obj = mBooksList.get(position);
            holder.bind(obj);
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
        }

        @Override
        public int getItemCount() {
            mAmountBooks = String.valueOf(mBooksList.size());
            return mBooksList.size();
        }

        private class BooksDetailHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private TextView mTitle,mKategori;
            private ImageView mCoverBook;
            private Object[] mObjects;
            private String mEditableTitle;
            public BooksDetailHolder(LayoutInflater inflater,ViewGroup parent){
                super(inflater.inflate(R.layout.item_loan_detail, parent, false));

                mTitle = itemView.findViewById(R.id.title_book_newest);
                mKategori = itemView.findViewById(R.id.title_book_kategori);
                mCoverBook = itemView.findViewById(R.id.cover_book_newest);
//                itemView.setOnClickListener(this);
            }
            public void bind(Object[] objects){
                mObjects = objects;
                Log.d(TAG, "bind: " + objects[2].toString());
                mEditableTitle = objects[2].toString();
                if(mEditableTitle.length() > 15 ){
                    mTitle.setText(mEditableTitle.substring(0,15));
                }else {
                    mTitle.setText(objects[2].toString());
                }
                mKategori.setText(objects[4].toString());
                if(!objects[3].toString().equals("KOSONG") && !objects[3].toString().equals("IMG_NoImage.jpg")){
                    Picasso.get()
                            .load(objects[3].toString())
                            .placeholder(R.drawable.no_cover_book)
                            .error(R.drawable.no_cover_book)
                            .into(mCoverBook);
                }else {
                    mCoverBook.setImageResource(R.drawable.no_cover_book);
                }
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mObjects[2].toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + mObjects[2].toString());
            }
        }
    }
}