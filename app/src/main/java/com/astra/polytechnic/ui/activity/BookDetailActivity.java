package com.astra.polytechnic.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    private static final String TAG = "BookDetailActivity";
    private String mIdKoleksi;
    private ImageView mCoverDetail, mBackButton;
    private TextView mBookTitle, mBookAuthor, mBookPublisher, mBookPubYear, mBookDesc, mBookCategory, mBookKlasifikasi, mBookRak, mStatusPinjam;
    private KoleksiViewModel mKoleksiViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        mIdKoleksi = getIntent().getStringExtra("id_koleksi");
        Log.d(TAG, "onCreate: " + mIdKoleksi);
        // Initialize Component
        mBackButton = findViewById(R.id.back_button);
        mCoverDetail = findViewById(R.id.book_cover_detail);
        mBookTitle = findViewById(R.id.book_title_detail);
        mBookAuthor = findViewById(R.id.book_author_detail);
        mBookPublisher = findViewById(R.id.book_publisher_detail);
        mBookPubYear = findViewById(R.id.book_publish_year_detail);
        mBookDesc = findViewById(R.id.book_description);
        mBookCategory = findViewById(R.id.book_category);
        mBookKlasifikasi = findViewById(R.id.book_klasifikasi);
        mBookRak = findViewById(R.id.book_rak);
        mStatusPinjam = findViewById(R.id.book_available);

        mKoleksiViewModel = new ViewModelProvider(this).get(KoleksiViewModel.class);

        int id = Integer.parseInt(mIdKoleksi);

        mKoleksiViewModel.getDetailBook(id).observe(this,objects -> {
            String status;
            Object[] obj = objects.get(0);
            Log.d(TAG, "getDetailBook: " + obj[0].toString());
            mBookTitle.setText(obj[1].toString());
            mBookDesc.setText(obj[2].toString());
            mBookPubYear.setText(obj[14].toString());
            mBookCategory.setText(obj[9].toString());
            mBookRak.setText(obj[10].toString());
            if (obj[8].toString().equals("1.0")) {
                status = "Tersedia";
            }else {
                status = "Tidak Tersedia";
            }
            mStatusPinjam.setText(status);
            if(!obj[5].equals("KOSONG") && !obj[5].equals("IMG_NoImage.jpg")){
                Picasso.get()
                        .load(obj[5].toString())
                        .placeholder(R.drawable.no_cover_book)
                        .error(R.drawable.no_cover_book)
                        .into(mCoverDetail);
            }else {
                mCoverDetail.setImageResource(R.drawable.no_cover_book);
            }
        });

        mKoleksiViewModel.getDetailAtribut(id).observe(this,objects -> {
            Object[] obj = objects.get(0);
            Object[] obj1 = objects.get(1);
            Log.d(TAG, "getDetailAtribut: " + obj[0].toString());
            mBookAuthor.setText(obj[2].toString());
            mBookPublisher.setText(obj[2].toString());
        });

        mKoleksiViewModel.getKlasifikasiDetail(id).observe(this,objects -> {
            Object[] obj = objects.get(0);
            Log.d(TAG, "getKlasifikasiDetail: " + objects.size());
            String klasifikasi = obj[2].toString();
            if(objects.size() != 1){
                for(int i = 2; i < objects.size(); i++){
                    Object[] obj1 = objects.get(i - 1);
                    // Melakukan penambahan jika objek tidak null
                    klasifikasi += ", " + obj1[2].toString();
                }
                mBookKlasifikasi.setText(klasifikasi);
                Log.d(TAG, "getKlasifikasiDetail: " + objects.size());
            }else {
                Log.d(TAG, "getKlasifikasiDetail: " + objects.size());
                mBookKlasifikasi.setText(klasifikasi);
            }
        });
    }
}