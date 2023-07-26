package com.astra.polytechnic.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.KeranjangViewModel;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.astra.polytechnic.helper.DLAHelper;
import com.astra.polytechnic.model.BookingDetail;
import com.astra.polytechnic.model.Keranjang;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.ui.fragment.LoanMemberFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class KeranjangActivity extends AppCompatActivity {

    private EditText mNim,mProdi,mNama,mNoHp;
    private List<Keranjang> mKeranjangList;

    private EditText mDate;
    private static final String REQUEST_DATE = "DialogDate";
    private KeranjangActivity.KeranjangAdapter mKeranjangAdapter = new KeranjangActivity.KeranjangAdapter(Collections.emptyList());
    RecyclerView cartBuku;
    KeranjangViewModel mKeranjangViewModel;
    ImageButton btnBooking;
    SharedPreferences pref;
    Keranjang mKeranjang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_book_loan);
        mKeranjangViewModel = new ViewModelProvider(this).get(KeranjangViewModel.class);

        pref = getSharedPreferences("nomor", MODE_PRIVATE);

        btnBooking = findViewById(R.id.btnBooking);
        mNim = findViewById(R.id.ediTextNIM);
        mNama = findViewById(R.id.ediTextName);
        mProdi = findViewById(R.id.ediTextProdi);
        mNoHp = findViewById(R.id.ediTextPhoneNumber);
        mDate = findViewById(R.id.btnDate);

        mNim.setText(pref.getString("nomor", ""));
        mNama.setText(pref.getString("nama", ""));
        mProdi.setText(pref.getString("deskripsi", ""));
        mNoHp.setText(pref.getString("no_hp", ""));
        mDate.setText("Pilih Tanggal");
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog();
            }
        });
        String email= pref.getString("email","");
        cartBuku = findViewById(R.id.recyclerView_cart);
        cartBuku.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mKeranjangViewModel.getKeranjangbyemail(email).observe(KeranjangActivity.this, this::updateNewestBook);

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void updateNewestBook(List<Keranjang> keranjangs){
        Log.d("TAG", "updateNewestBook: "+ keranjangs);
        mKeranjangList = DLAHelper.getKeranjang(keranjangs);
        mKeranjangAdapter = new KeranjangActivity.KeranjangAdapter(mKeranjangList);
        cartBuku.setAdapter(mKeranjangAdapter);
    }
    private class KeranjangAdapter extends RecyclerView.Adapter<KeranjangActivity.KeranjangAdapter.KoleksiHolder> {
        private List<Keranjang> mKoleksis;
        ImageView mImageView;

        public KeranjangAdapter(List<Keranjang> koleksis) {
            mKoleksis = koleksis;
        }

        @NonNull
        @Override
        public KeranjangActivity.KeranjangAdapter.KoleksiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(KeranjangActivity.this);

            return new KeranjangActivity.KeranjangAdapter.KoleksiHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull KeranjangActivity.KeranjangAdapter.KoleksiHolder holder, int position) {
            Keranjang koleksi = mKoleksis.get(position);
            holder.onBindViewHolder(koleksi);
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.fade_in);
            holder.itemView.startAnimation(animation);
        }

        @Override
        public int getItemCount() {
            return mKoleksis.size();
        }

        private class KoleksiHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private ImageView mBookImage, mDeleteImage;
            private TextView mBookTitle;
            private Keranjang mKoleksi;
            private TextView deskripsi;
            private String mEditableTitle;
            ImageView imageView;

            public KoleksiHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_detail_keranjang, parent, false));

                mBookImage = itemView.findViewById(R.id.cover_book_newest);
                mBookTitle = itemView.findViewById(R.id.title_book_newest);
                deskripsi = itemView.findViewById(R.id.title_book_kategori);
                mDeleteImage= itemView.findViewById(R.id.btnDelete);

                mDeleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mKeranjangViewModel.deleteKeranjang(mKoleksi.getIdKoleksi().getIdKoleksi());
                        Toast.makeText(KeranjangActivity.this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    }
                });

                itemView.setOnClickListener(this);
            }


            private void onBindViewHolder(Keranjang koleksi) {

                mEditableTitle = koleksi.getIdKoleksi().getNama();
                if (mEditableTitle.length() > 25) {
                    mBookTitle.setText(mEditableTitle.substring(0, 25));
                    deskripsi.setText(koleksi.getIdKoleksi().getDeskripsi());

                } else {
                    mBookTitle.setText(koleksi.getIdKoleksi().getNama());
                    deskripsi.setText(koleksi.getIdKoleksi().getDeskripsi());
                }
                mKoleksi = koleksi;

            }

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(KeranjangActivity.this, BookDetailMemberActivity.class);
//                intent.putExtra("id_koleksi", mKoleksi.getIdKoleksi());
//                startActivity(intent);

            }
        }
    }


    private void DateDialog(){
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar newdate= Calendar.getInstance();
                newdate.set(i,i1,i2);
                mDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(newdate.getTime()));
            }
        },calendar.get((Calendar.YEAR)),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
