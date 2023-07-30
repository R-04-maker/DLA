package com.astra.polytechnic.ui.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.KeranjangViewModel;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.astra.polytechnic.model.*;
import com.astra.polytechnic.repository.KeranjangRepository;
import com.squareup.picasso.Picasso;

public class BookDetailMemberActivity extends AppCompatActivity {
    private static final String TAG = "BookDetailActivity";
    private String mIdKoleksi;
    private ImageView mCoverDetail, mBackButton,mApprove;
    private TextView mBookTitle, mBookAuthor, mBookPublisher, mBookPubYear, mBookDesc, mBookCategory, mBookKlasifikasi, mBookRak, mStatusPinjam;
    private KoleksiViewModel mKoleksiViewModel;
    private KeranjangRepository mKeranjangRepository;
    KeranjangViewModel mKeranjangViewModel;
    msuser mUser;
    Koleksi mKoleksi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKeranjangViewModel = new ViewModelProvider(this).get(KeranjangViewModel.class);
        setContentView(R.layout.activity_book_detail_member);
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
        mApprove= findViewById(R.id.btnApprove);

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
        mApprove.setOnClickListener(v -> {
            int ids=0;
            SharedPreferences sharedPreferences = getSharedPreferences("nomor",MODE_PRIVATE);
            String email = sharedPreferences.getString("email","");
            System.out.println("email :"+email);
            msuser user = new msuser();
            user.setEmail(email);
            Koleksi koleksi = new Koleksi();
            koleksi.setIdKoleksi(mIdKoleksi);
            Keranjang keranjang= new Keranjang(ids,koleksi,user);
            mKeranjangViewModel.addKeranjang(keranjang);
            finish();

            Toast.makeText(this, "Data Berhasil Dimasukan Ke keranjang", Toast.LENGTH_SHORT).show();
        });

    }
}
