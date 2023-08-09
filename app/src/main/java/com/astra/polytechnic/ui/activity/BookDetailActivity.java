package com.astra.polytechnic.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.KeranjangViewModel;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.astra.polytechnic.ViewModel.ManagedLoanViewModel;
import com.astra.polytechnic.model.Keranjang;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.model.msuser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    private static final String TAG = "BookDetailActivity";
    private String mIdKoleksi;
    private CardView mCardViewStatus;
    private Button mAddtoCart;
    private ImageView mCoverDetail, mBackButton;
    private TextView mBookTitle, mBookAuthor, mBookPublisher, mBookPubYear, mBookDesc, mBookCategory, mBookKlasifikasi, mBookRak, mStatusPinjam;
    private KoleksiViewModel mKoleksiViewModel;
    SharedPreferences pref;
    private KeranjangViewModel mKeranjangViewModel;
    ManagedLoanViewModel mManagedLoanViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        mIdKoleksi = getIntent().getStringExtra("id_koleksi");

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
        mAddtoCart = findViewById(R.id.btn_addtoCart);
        mCardViewStatus = findViewById(R.id.card_status_detail);

        mKoleksiViewModel = new ViewModelProvider(this).get(KoleksiViewModel.class);
        mKeranjangViewModel = new ViewModelProvider(this).get(KeranjangViewModel.class);
        mManagedLoanViewModel = new ViewModelProvider(this).get(ManagedLoanViewModel.class);

        int id = Integer.parseInt(mIdKoleksi);

        mKoleksiViewModel.getDetailBook(id).observe(this,objects -> {
            String status;
            Object[] obj = objects.get(0);
            mBookTitle.setText(obj[1].toString());
            mBookDesc.setText(obj[2].toString());
            mBookPubYear.setText(obj[14].toString());
            mBookCategory.setText(obj[9].toString());
            mBookRak.setText(obj[10].toString());
            Log.d(TAG, "onCreate: " + obj[8].toString());
            if (obj[8].toString().equals("1.0")) {
                status = "Tersedia";
                mStatusPinjam.setText(status);
                mCardViewStatus.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_dipinjam));
            }else {
                status = "Tidak Tersedia";
                mStatusPinjam.setText(status);
                mCardViewStatus.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_ditolak));
            }
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
            mBookAuthor.setText(obj[2].toString());
            mBookPublisher.setText(obj[2].toString());
        });

        mKoleksiViewModel.getKlasifikasiDetail(id).observe(this,objects -> {
            Object[] obj = objects.get(0);
            String klasifikasi = obj[2].toString();
            if(objects.size() != 1){
                for(int i = 2; i < objects.size(); i++){
                    Object[] obj1 = objects.get(i - 1);
                    // Melakukan penambahan jika objek tidak null
                    klasifikasi += ", " + obj1[2].toString();
                }
                mBookKlasifikasi.setText(klasifikasi);
            }else {
                mBookKlasifikasi.setText(klasifikasi);
            }
        });

        pref = BookDetailActivity.this.getSharedPreferences("nomor", BookDetailActivity.MODE_PRIVATE);
        String namaSp = pref.getString("id_role", null);
        if (namaSp != null) {
            if(namaSp.equals("ROL06")){
                mAddtoCart.setVisibility(View.VISIBLE);
            }else {
                mAddtoCart.setVisibility(View.GONE);
            }
        }

        mAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ids=0;
                String email = pref.getString("email","");
                String idBuku = mIdKoleksi;
                System.out.println("email :" + email);
                // Cek keranjang untuk validasi ketika data udah ada di keranjang
                mKeranjangViewModel.cekKeranjang(email,idBuku).observe(BookDetailActivity.this,obj -> {
                    if (obj.getStatus() == 500) {
                        msuser user = new msuser();
                        user.setEmail(email);
                        Koleksi koleksi = new Koleksi();
                        koleksi.setIdKoleksi(mIdKoleksi);
                        Keranjang keranjang = new Keranjang(ids,koleksi,user);
                        mKeranjangViewModel.addKeranjang(keranjang);
                        Toast.makeText(BookDetailActivity.this, "Berhasil ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(BookDetailActivity.this, obj.getResult(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void onBackBtnClicked(View view){
        finish();
    }
}