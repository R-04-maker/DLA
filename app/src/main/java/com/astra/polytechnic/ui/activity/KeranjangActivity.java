package com.astra.polytechnic.ui.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.BookingViewModel;
import com.astra.polytechnic.ViewModel.KeranjangViewModel;
import com.astra.polytechnic.ViewModel.KoleksiViewModel;
import com.astra.polytechnic.helper.DLAHelper;
import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.model.BookingDetail;
import com.astra.polytechnic.model.*;
import com.astra.polytechnic.model.Koleksi;
import com.astra.polytechnic.repository.BookingRepository;
import com.astra.polytechnic.ui.fragment.HomeMemberFragment;
import com.astra.polytechnic.model.Keranjang;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private RecyclerView cartBuku;
    private KeranjangViewModel mKeranjangViewModel;
    private BookingViewModel mBookingViewModel;
    private ImageButton btnBooking;
    SharedPreferences pref;
    private List<Booking> mBookings;
    private Keranjang mKeranjang;
    private int idBooking, newIdBooking;
    private BookingRepository mBookingRepository;
    private ArrayList mList=new ArrayList<>();
    String resultDateStr;
    int num;
    int MaksPinjam;
    int MaksTempo;
    String datepick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_book_loan);
        mKeranjangViewModel = new ViewModelProvider(this).get(KeranjangViewModel.class);
        mBookingViewModel = new ViewModelProvider(this).get(BookingViewModel.class);

        pref = getSharedPreferences("nomor", MODE_PRIVATE);

        btnBooking = findViewById(R.id.btnBooking);
        mNim = findViewById(R.id.ediTextNIM);
        mNama = findViewById(R.id.ediTextName);
        mProdi = findViewById(R.id.ediTextProdi);
        mNoHp = findViewById(R.id.ediTextPhoneNumber);
        mDate = findViewById(R.id.btnDate);
//        mDate.setText("Tanggal Pengembalian Buku");

        mNim.setFocusable(false);
        mNim.setClickable(false);

        mProdi.setFocusable(false);
        mProdi.setClickable(false);

        mNama.setFocusable(false);
        mNama.setClickable(false);

        mNim.setText(pref.getString("nomor", ""));
        mNama.setText(pref.getString("nama", ""));
        mProdi.setText(pref.getString("deskripsi", ""));
        mNoHp.setText(pref.getString("no_hp", ""));

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
                String id_koleksi = "";
                if (mDate.getText().toString().equals("")) {
                    Toast.makeText(KeranjangActivity.this, "Tanggal Ambil Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (MaksPinjam < mKeranjangList.size()) {
                        Log.d("validasi makspinjam", "onClick: " + mKeranjangList.size());
                        Toast.makeText(KeranjangActivity.this, "Maksimal Peminjaman Buku adalah " + MaksPinjam, Toast.LENGTH_SHORT).show();
                    } else {
                        if (mKeranjangList.size() == 0) {
                            Toast.makeText(KeranjangActivity.this, "Keranjang Kosong", Toast.LENGTH_SHORT).show();
                        } else {
                            // ngecek dulu statuspinjam dari setiap buku
                            for (Keranjang data : mKeranjangList){
                                if(data.getIdKoleksi().getStatuspinjam() == 0){
                                    id_koleksi = String.valueOf(data.getIdKoleksi().getIdKoleksi());
                                    break;
                                }else {
                                    id_koleksi = "";
                                }
                            }
                            // End for
                            if(id_koleksi.equals("")){
                                // Get Last BookingID ("Bookingonline) di trbooking
                                mBookingViewModel.getBooking().observe(KeranjangActivity.this, bookings ->{
                                    int increament;
                                    if(bookings.size() > 0){
                                        Booking obj = bookings.get(0);
                                        idBooking = Integer.parseInt(obj.getIdbooking());
                                        int tglId = Integer.parseInt(obj.getIdbooking().substring(0, 6));
                                        increament = Integer.parseInt(obj.getIdbooking().substring(6));
                                    }else {
                                        increament = 0;
                                    }
                                    newIdBooking = getDateNow(increament);

                                    Booking booking = new Booking();
                                    booking.setIdTransaksi(0);
                                    booking.setEmail(email);
                                    booking.setIdbooking(String.valueOf(newIdBooking));
                                    booking.setStatus("Pengajuan");
                                    booking.setCreaby(email);
                                    booking.setCreadate("");
                                    booking.setModiby(email);
                                    booking.setModidate("");
                                    booking.setGambar("");
                                    booking.setGambar_sesudah("");
                                    mBookingViewModel.saveBooking(booking).observe(KeranjangActivity.this, result -> {
                                        System.out.println(result.getStatus());
                                        int lastID = result.getStatus();
                                        // cek status koleksi
                                        for(Keranjang keranjang : mKeranjangList){
                                            Koleksi koleksi = new Koleksi();
                                            koleksi.setIdKoleksi(keranjang.getIdKoleksi().getIdKoleksi());
                                            Booking booking1 = new Booking();
                                            booking1.setIdTransaksi(lastID);
                                            BookingDetail bookingDetail = new BookingDetail();
                                            bookingDetail.setIdTransactionDetail(0);
                                            bookingDetail.setIdTransaction(booking1);
                                            bookingDetail.setIdKoleksi(koleksi);
                                            bookingDetail.setTanggalPinjam(mDate.getText().toString());
                                            bookingDetail.setTanggalKembali(calculate(mDate.getText().toString()));
                                            bookingDetail.setStatus(1);
                                            bookingDetail.setCreaby(email);
                                            bookingDetail.setCreadate("");
                                            bookingDetail.setModiby("");
                                            bookingDetail.setModidate("");
                                            mBookingViewModel.saveBookingDetail(bookingDetail);
                                        }
                                    });
                                });
                                Toast.makeText(KeranjangActivity.this, "Berhasil Booking", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(KeranjangActivity.this, DashboardMemberActivity.class);
                                intent.putExtra("MaksTempo", MaksTempo);
                                startActivity(intent);
                            }else {
                                Toast.makeText(KeranjangActivity.this, "Maaf, Id Buku " + id_koleksi + " Tidak dapat dipinjam", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }
    private String calculate(String datepick){
        Date lcl = new Date();
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date inputDate = sdf.parse(datepick);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inputDate);
            calendar.add(Calendar.DAY_OF_MONTH, MaksTempo);
            Date dateAfter14Days = calendar.getTime();
            resultDateStr = sdf.format(dateAfter14Days);
            System.out.println("Date 14 days after the input date: " + resultDateStr);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the parsing or calculation exception if needed
        }
        return resultDateStr;
    }
    private static int getDateNow(int num){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Months are zero-based in Calendar
        int year = calendar.get(Calendar.YEAR) % 100; // Get last two digits of the year

        // Format the values to always have 2 digits (e.g., 01, 02, ..., 10, 11, ...)
        DecimalFormat df = new DecimalFormat("00");
        String formattedDay = df.format(day);
        String formattedMonth = df.format(month);
        String formattedYear = df.format(year);

        // Concatenate the formatted values to get the final result
        String result = formattedDay + formattedMonth + formattedYear;
        String formattedNumberAfterDate = String.format("%03d", num+1);
        System.out.println("Hasil: " + result+formattedNumberAfterDate);
        result = result+formattedNumberAfterDate;
        return Integer.valueOf(result);
    }

    private void updateNewestBook(List<Keranjang> keranjangs){
        Log.d("TAG", "updateNewestBook: "+ keranjangs);
        //perulangan untuk mengambil data dari list ke model keranjang
        for (Keranjang keranjang : keranjangs) {
            MaksPinjam = keranjang.getEmail().getId_role().getMaksbuku();
            MaksTempo  = keranjang.getEmail().getId_role().getMakstempo();
            System.out.println("Maks Pinjam : " + MaksPinjam);
        }

        mKeranjangList = DLAHelper.getKeranjang(keranjangs);
        mKeranjangAdapter = new KeranjangActivity.KeranjangAdapter(mKeranjangList);
        cartBuku.setAdapter(mKeranjangAdapter);
    }
    private class KeranjangAdapter extends RecyclerView.Adapter<KeranjangActivity.KeranjangAdapter.KoleksiHolder> {
        private List<Keranjang> mKoleksis;
        ImageView mImageView;
        private SparseBooleanArray selectedItems = new SparseBooleanArray();

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

            // checkbox
            boolean isSelected = selectedItems.get(position, false  );
            holder.mCheckBox.setChecked(isSelected);
            holder.itemView.setOnClickListener(v -> {
                holder.mCheckBox.setChecked(!isSelected);
                if (holder.mCheckBox.isChecked()) {
                    selectedItems.put(position, true);
                } else {
                    selectedItems.delete(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mKoleksis.size();
        }
        public void removeItem(int position) {
            mKoleksis.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mKoleksis.size());
            mKeranjangList = mKoleksis;
        }

        private class KoleksiHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private ImageView mBookImage, mDeleteImage;
            private TextView mBookTitle, pengarang, mIdKoleksi;
            private Keranjang mKeranjang;
            private String mEditableTitle;
            ImageView imageView;
            private CheckBox mCheckBox;

            public KoleksiHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_detail_keranjang, parent, false));

                mBookImage = itemView.findViewById(R.id.cover_book_newest);
                mBookTitle = itemView.findViewById(R.id.title_book_newest);
                pengarang = itemView.findViewById(R.id.title_book_kategori);
                mDeleteImage = itemView.findViewById(R.id.btnDelete);
                mIdKoleksi = itemView.findViewById(R.id.id_koleksi);
                mCheckBox = itemView.findViewById(R.id.checkbox_keranjang);

                mDeleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mKeranjangViewModel.deleteKeranjang(mKeranjang.getId_keranjang());
                        Toast.makeText(KeranjangActivity.this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        mKeranjangAdapter.removeItem(getAdapterPosition());
                    }
                });

                itemView.setOnClickListener(this);
            }


            private void onBindViewHolder(Keranjang koleksi) {
                mKeranjang = koleksi;
                mEditableTitle = koleksi.getIdKoleksi().getNama();
                mBookTitle.setText(koleksi.getIdKoleksi().getNama());
                pengarang.setText(koleksi.getIdKoleksi().getPengarang());
                mIdKoleksi.setText(koleksi.getIdKoleksi().getIdKoleksi());
                if(!koleksi.getIdKoleksi().getGambar().equals("KOSONG") && !koleksi.getIdKoleksi().getGambar().equals("IMG_NoImage.jpg")){
                    Glide.with(KeranjangActivity.this)
                            .load(koleksi.getIdKoleksi().getGambar())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.no_cover_book)
                                    .error(R.drawable.no_cover_book)
                            )
                            .into(mBookImage);
                }else {
                    mBookImage.setImageResource(R.drawable.no_cover_book);
                }
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
                Calendar newdate = Calendar.getInstance();
                newdate.set(i,i1,i2);
                datepick = new SimpleDateFormat("dd/MM/yyyy").format(newdate.getTime());
                mDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(newdate.getTime()));
                Toast.makeText(KeranjangActivity.this, "Tanggal Pinjam : "+datepick, Toast.LENGTH_SHORT).show();
            }
        },calendar.get((Calendar.YEAR)),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        // Set minimum date
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Mengubah warna tombol "OK" menjadi hijau
        datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((DatePickerDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setTextColor(getResources().getColor(R.color.tab_layout_on)); // Ganti dengan warna yang diinginkan
            }
        });

        datePickerDialog.show();
    }
}
