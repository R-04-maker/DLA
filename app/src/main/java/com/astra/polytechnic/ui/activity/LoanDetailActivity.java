package com.astra.polytechnic.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.ManagedLoanViewModel;
import com.astra.polytechnic.helper.DLAHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import android.util.Base64;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LoanDetailActivity extends AppCompatActivity {
    private static final String TAG = "LoanDetailActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSIONS_REQUEST_CAMERA = 2;
    private String mIdBooking,mStatusExtra;
    private Button mBtnTolak, mBtnTerima, mUpdateGambar, mBtnCamera, mBtnCancel;
    private MaterialCardView mMaterialCardView;
    private TextView mStatus,mBookingId,mBookingDate,mBookingReturn,mNIM,mName,mProdi,mHp,mTry,mDendaTitle,mDenda;
    private CardView mBtnModalFotoSebelum,mBtnModalFotoSetelah;
    private RecyclerView mRVDetailBooks;
    private ManagedLoanViewModel mManagedLoanViewModel;
    private List<Object[]> mDetailBooksList;
    private Bitmap imageBitmap;
    private ImageView imageView,mBackButton, mImgFotoSebelum, mImgFotoSesudah;
    String email, id_role;
    String formattedDate,resultDateStr,formattedDate1,formatedcreadate;
    Date dateAfter14Days,date1,date,datenow;
    private BooksDetailAdapter mBooksDetailAdapter = new BooksDetailAdapter(Collections.emptyList());
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private String mFotoSebelum, mFotoSesudah;
    int colorDiterima, colorDitolak, colorPengajuan, colorDipinjam, colorSelesai, colorBatal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_detail);
        mIdBooking = getIntent().getStringExtra("id_booking");

        // Initialize Component

        colorDiterima = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_diterima);
        colorDitolak = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_ditolak);
        colorPengajuan = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_pengajuan);
        colorDipinjam = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_dipinjam);
        colorSelesai = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_selesai);
        colorBatal = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_batal);

        mStatus = findViewById(R.id.loan_status);
        mBookingId = findViewById(R.id.booking_code_req_detail);
        mBookingDate = findViewById(R.id.booking_date);
        mBookingReturn = findViewById(R.id.booking_return_date);
        mNIM = findViewById(R.id.nim);
        mName = findViewById(R.id.name_profile);
        mProdi = findViewById(R.id.prodi);
        mHp = findViewById(R.id.noHp);
        mDendaTitle = findViewById(R.id.denda_title);
        mDenda = findViewById(R.id.denda);
        mMaterialCardView = findViewById(R.id.materialCardView);

        mImgFotoSebelum = findViewById(R.id.foto_sebelum);
        mImgFotoSesudah = findViewById(R.id.foto_sesudah);

        mBtnTerima = findViewById(R.id.btn_terima);
        mBtnTolak = findViewById(R.id.btn_tolak);
        mBtnCamera = findViewById(R.id.btn_camera);
        mBtnCancel = findViewById(R.id.btn_cancel_booking);
        mBtnCamera.setVisibility(View.GONE);

        mBackButton = findViewById(R.id.back_fragment_to_activity);

        mRVDetailBooks = findViewById(R.id.rv_detail_loan);
        mRVDetailBooks.setLayoutManager(new LinearLayoutManager(this));
        mRVDetailBooks.setAdapter(mBooksDetailAdapter);

        imageView = findViewById(R.id.image_result);
        mUpdateGambar = findViewById(R.id.update_gambar);
        mTry = findViewById(R.id.try_textview);
        mTry.setVisibility(View.GONE);

        mBtnModalFotoSebelum = findViewById(R.id.card_view_1);
        mBtnModalFotoSetelah = findViewById(R.id.card_view_2);

        mManagedLoanViewModel = new ViewModelProvider(this).get(ManagedLoanViewModel.class);
        SharedPreferences pref = getSharedPreferences("nomor", MODE_PRIVATE);
        String id_role = pref.getString("id_role", null);
        mManagedLoanViewModel.getDetailBooking(Integer.parseInt(mIdBooking)).observe(this, objects -> {
            Object[] obj = objects.get(0);
            DateTimeFormatter inputFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            mNIM.setText(obj[11] != null ? obj[11].toString() : "");
            String tglkembali =obj[3].toString();
            String tglambil = obj[4].toString();
            String creadate = obj[5].toString();
            OffsetDateTime offsettglambil = OffsetDateTime.parse((String) tglambil, inputFormat);
            OffsetDateTime offsettglambil1 = OffsetDateTime.parse((String) tglkembali, inputFormat);
            OffsetDateTime offsetcreadate = OffsetDateTime.parse((String) creadate, inputFormat);
            Instant instant = offsettglambil.toInstant();
            Instant instant1 = offsettglambil1.toInstant();
            Instant instant2 = offsetcreadate.toInstant();
            date = Date.from(instant);
            date1 = Date.from(instant1);
            datenow=Date.from(Instant.now());

            formattedDate = offsettglambil.format(outputFormat);
            formattedDate1 = offsettglambil1.format(outputFormat);
            formatedcreadate = offsetcreadate.format(outputFormat);

/*            System.out.println("Parsed Tanggal Ambil: " + date1);
            System.out.println("Parsed Tanggal Kembali: " + date);
            System.out.println("Parsed Tanggal Sekarang: " + datenow);
            System.out.println("Parsed Tanggal Buat: " + formatedcreadate);*/
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.DAY_OF_MONTH, 14);
             dateAfter14Days = calendar.getTime();
            resultDateStr = DATE_FORMAT.format(dateAfter14Days);
//            System.out.println("Date 14 days after the input date: " + resultDateStr);
            long diffInMillis =  dateAfter14Days.getTime() - datenow.getTime();

            // Mengubah selisih milidetik ke dalam satuan hari
            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
//            System.out.println("Selisih hari: " + diffInDays);
            mBookingId.setText(obj[1].toString());
            mBookingDate.setText(formatedcreadate);
            mBookingReturn.setText(formattedDate);
            mNIM.setText(obj[6].toString());
            mName.setText(obj[7].toString());
            mProdi.setText(obj[9].toString());
            mHp.setText(obj[10].toString());

            mFotoSebelum = obj[11].toString();
            mFotoSesudah = obj[12].toString();

            if(!mFotoSebelum.equals("")){
                Picasso.get()
                        .load(mFotoSebelum)
                        .placeholder(R.drawable.noimage)
                        .error(R.drawable.noimage)
                        .into(mImgFotoSebelum);
            }else {
                mImgFotoSebelum.setImageResource(R.drawable.noimage);
            }
            if(!mFotoSesudah.equals("")){
                Picasso.get()
                        .load(mFotoSesudah)
                        .placeholder(R.drawable.noimage)
                        .error(R.drawable.noimage)
                        .into(mImgFotoSesudah);
            }else {
                mImgFotoSesudah.setImageResource(R.drawable.noimage);
            }

            // Set cardView background for status and Button Action with status param
            mStatusExtra = obj[2].toString();
                if (mStatusExtra.equals("Pengajuan")) {
                    mMaterialCardView.setCardBackgroundColor(colorPengajuan);
                    mStatus.setText(obj[2].toString());
                    mDendaTitle.setVisibility(View.GONE);
                    mDenda.setVisibility(View.GONE);
                    if (id_role.equals("ROL06")){
                        mBtnTerima.setVisibility(View.GONE);
                        mBtnTolak.setVisibility(View.GONE);
                        mBtnCamera.setVisibility(View.GONE);
                        mUpdateGambar.setVisibility(View.GONE);
                        mBtnCancel.setVisibility(View.VISIBLE);
                    }else {
                        mBtnTolak.setVisibility(View.VISIBLE);
                        mBtnTerima.setVisibility(View.VISIBLE);
                        mBtnCamera.setVisibility(View.GONE);
                        mUpdateGambar.setVisibility(View.GONE);
                    }

                } else if (mStatusExtra.equals("Diterima")) {
                    if (dateAfter14Days.after(datenow)) {
                        mStatus.setText(obj[2].toString());
                        mMaterialCardView.setCardBackgroundColor(colorDiterima);
                        mDenda.setVisibility(View.GONE);
                        mDendaTitle.setVisibility(View.GONE);
                    }else {
                        mStatus.setText("Denda");
                        mDenda.setText(diffInDays+"(Hari)*5000 ="+"Rp. "+diffInDays*5000);
                        mMaterialCardView.setCardBackgroundColor(colorDitolak);
                    }
                    if (id_role.equals("ROL06")){
                        mBtnTerima.setVisibility(View.GONE);
                        mBtnTolak.setVisibility(View.GONE);
                        mBtnCamera.setVisibility(View.GONE);
                        mUpdateGambar.setVisibility(View.GONE);
                        mBtnCancel.setVisibility(View.GONE);
                    }else {
                        mBtnTolak.setVisibility(View.GONE);
                        mBtnTerima.setVisibility(View.GONE);
                        mBtnCamera.setVisibility(View.VISIBLE);
                        mUpdateGambar.setVisibility(View.VISIBLE);
                    }

                } else if (mStatusExtra.equals("Ditolak") && id_role.equals("ROL01")) {
                    mMaterialCardView.setCardBackgroundColor(colorDitolak);
                    mStatus.setText(obj[2].toString());

                } else if (mStatusExtra.equals("Dipinjam")) {
                    mBtnTolak.setVisibility(View.GONE);
                    if (dateAfter14Days.after(datenow)) {
                        mStatus.setText(obj[2].toString());
                        mMaterialCardView.setCardBackgroundColor(colorDipinjam);
                        mDenda.setVisibility(View.GONE);
                        mDendaTitle.setVisibility(View.GONE);
                    }else {
                        mStatus.setText("Denda");
                        mDenda.setText(diffInDays+"(Hari)*5000 ="+"Rp. "+diffInDays*5000);
                        mMaterialCardView.setCardBackgroundColor(colorDitolak);
                    }
                    if (id_role.equals("ROL06")){
                        mBtnTerima.setVisibility(View.GONE);
                        mBtnTolak.setVisibility(View.GONE);
                        mBtnCamera.setVisibility(View.GONE);
                        mUpdateGambar.setVisibility(View.GONE);
                        mBtnCancel.setVisibility(View.GONE);
                    }else {
                        mBtnTolak.setVisibility(View.GONE);
                        mBtnTerima.setVisibility(View.GONE);
                        mBtnCamera.setVisibility(View.VISIBLE);
                        mUpdateGambar.setVisibility(View.VISIBLE);
                    }
                } else if (mStatusExtra.equals("Selesai")) {
                    mBtnTolak.setVisibility(View.GONE);
                        if (dateAfter14Days.after(datenow)) {
                            mStatus.setText(obj[2].toString());
                            mMaterialCardView.setCardBackgroundColor(colorSelesai);
                            mBtnTerima.setVisibility(View.GONE);
                            mBtnTolak.setVisibility(View.GONE);
                            mBtnCamera.setVisibility(View.GONE);
                            mUpdateGambar.setVisibility(View.GONE);
                            mDenda.setVisibility(View.GONE);
                            mDendaTitle.setVisibility(View.GONE);
                        }else {
                            mStatus.setText("Denda");
                            mDenda.setText(diffInDays+"(Hari)*5000 ="+"Rp. "+diffInDays*5000);
                            mMaterialCardView.setCardBackgroundColor(colorDitolak);
                            mBtnTerima.setVisibility(View.GONE);
                            mBtnTolak.setVisibility(View.GONE);
                            mBtnCamera.setVisibility(View.GONE);
                            mUpdateGambar.setVisibility(View.GONE);
                        }
                        mBtnTerima.setVisibility(View.GONE);
                        mBtnTolak.setVisibility(View.GONE);
                        mBtnCamera.setVisibility(View.GONE);
                        mUpdateGambar.setVisibility(View.GONE);
                        mBtnCancel.setVisibility(View.GONE);
                } else if (mStatusExtra.equals("Batal")) {
                    mStatus.setText("Batal");
                    mMaterialCardView.setCardBackgroundColor(colorBatal);
                    mBtnTerima.setVisibility(View.GONE);
                    mBtnTolak.setVisibility(View.GONE);
                    mBtnCamera.setVisibility(View.GONE);
                    mUpdateGambar.setVisibility(View.GONE);
                    mBtnCancel.setVisibility(View.GONE);
                }
        });
        mManagedLoanViewModel.getBookDetailBooking().observe(this, this::UpdateDetailBooks);
        if (id_role.equals("ROL06")) {
            mBtnTerima.setVisibility(View.GONE);
            mBtnTolak.setVisibility(View.GONE);
            mBtnCamera.setVisibility(View.GONE);
            mUpdateGambar.setVisibility(View.GONE);
        } else {
            mBtnTolak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoanDetailActivity.this);
                    builder.setTitle(R.string.konfirmasi_title);
                    builder.setMessage(R.string.konfirmasi_tolak);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ProgressDialog progressDialog = ProgressDialog.show(LoanDetailActivity.this, "", "Loading ...", true);
                            mManagedLoanViewModel.updateDetailBooking(Integer.parseInt(mIdBooking), "Ditolak").observe(LoanDetailActivity.this, data -> {
                                progressDialog.dismiss();
                                UpdateData(data);
                            });
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    // Show Confirmation Dialog
                    AlertDialog dialog = builder.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                            positiveButton.setTextColor(Color.BLACK);
                            negativeButton.setTextColor(Color.BLACK);
                        }
                    });
                    dialog.show();
                }
            });
            mBtnTerima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoanDetailActivity.this);
                    builder.setTitle(R.string.konfirmasi_title);
                    builder.setMessage(R.string.konfirmasi_terima);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ProgressDialog progressDialog = ProgressDialog.show(LoanDetailActivity.this, "", "Loading ...", true);
                            mManagedLoanViewModel.updateDetailBooking(Integer.parseInt(mIdBooking), "Diterima").observe(LoanDetailActivity.this, data -> {
                                progressDialog.dismiss();
                                UpdateData(data);
                            });
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    // Show Confirmation Dialog
                    AlertDialog dialog = builder.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                            positiveButton.setTextColor(Color.BLACK);
                            negativeButton.setTextColor(Color.BLACK);
                        }
                    });
                    dialog.show();
                }
            });
            mBtnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: ");
                    // Memeriksa izin kamera sebelum memulai implisit intent camera
                    if (checkCameraPermission()) {
                        dispatchTakePictureIntent();
                    } else {
                        requestCameraPermission();
                    }
                }
            });
            mUpdateGambar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status1 = "Dipinjam";
                    String status2 = "Selesai";
                    Log.d(TAG, "onClic1k: " + mStatusExtra);
                    if (imageBitmap != null) {
                        Log.d(TAG, "onClick2: " + imageBitmap);
                        if (mStatusExtra.equals("Diterima")) {
                            Log.d(TAG, "onClick3: " + imageBitmap);
                            // save image to coloumn gambar with status = "Dipinjam"
                            uploadImageToServer(imageBitmap, status1);
                        } else if (mStatusExtra.equals("Dipinjam")) {
                            // save image to coloumn gambar_sesudah with status = "Selesai"
                            uploadImageToServer(imageBitmap, status2);
                        }
                    } else {
                        Toast.makeText(LoanDetailActivity.this, "No image captured!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoanDetailActivity.this);
                builder.setTitle(R.string.konfirmasi_title);
                builder.setMessage(R.string.konfirmasi_cancel);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProgressDialog progressDialog = ProgressDialog.show(LoanDetailActivity.this, "", "Loading ...", true);
                        mManagedLoanViewModel.updateDetailBooking(Integer.parseInt(mIdBooking), "Batal").observe(LoanDetailActivity.this, data -> {
                            progressDialog.dismiss();
                            UpdateData(data);
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                // Show Confirmation Dialog
                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        positiveButton.setTextColor(Color.BLACK);
                        negativeButton.setTextColor(Color.BLACK);
                    }
                });
                dialog.show();
            }
        });
        mBtnModalFotoSebelum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        LoanDetailActivity.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.bottom_image_modal, findViewById(R.id.botomSheetContainer));
                ImageView mFotoModal = bottomSheetView.findViewById(R.id.fotoPeminjaman);
                // Masukin resource foto pakai picasso
                if (!mFotoSebelum.equals("")) {
                    Glide.with(bottomSheetView)
                            .load(mFotoSebelum)
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.noimage)
                                    .error(R.drawable.noimage)
                            )
                            .into(mFotoModal);
                } else {
                    mFotoModal.setImageResource(R.drawable.noimage);
                }
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        mBtnModalFotoSetelah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        LoanDetailActivity.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.bottom_image_modal, findViewById(R.id.botomSheetContainer)
                        );
                // Masukin resource foto pakai picasso
                ImageView mFotoModal = bottomSheetView.findViewById(R.id.fotoPeminjaman);
                if (!mFotoSesudah.equals("")) {
                    Glide.with(bottomSheetView)
                            .load(mFotoSesudah)
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.noimage)
                                    .error(R.drawable.noimage)
                            )
                            .into(mFotoModal);
                } else {
                    mFotoModal.setImageResource(R.drawable.noimage);
                }
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }
    private boolean checkCameraPermission() {
        // Periksa izin kamera saat runtime (hanya diperlukan untuk Android 6.0 dan di atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            return cameraPermission == PackageManager.PERMISSION_GRANTED;
        } else {
            // Jika versi Android kurang dari 6.0, izin kamera dianggap sudah diberikan.
            return true;
        }
    }
    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin kamera telah diberikan, lanjutkan dengan intent kamera
                dispatchTakePictureIntent();
            } else {
                // Izin kamera ditolak, beri tahu pengguna atau berikan feedback sesuai kebutuhan
                Toast.makeText(this, "Izin kamera diperlukan untuk mengambil gambar.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            if (extras != null) {
                imageBitmap = (Bitmap) extras.get("data");
                if(mFotoSebelum.equals("")){
                    mImgFotoSebelum.setImageBitmap(imageBitmap);
                }else if(mFotoSesudah.equals("")){
                    mImgFotoSesudah.setImageBitmap(imageBitmap);
                }
//                imageView.setImageBitmap(imageBitmap);
                Toast.makeText(LoanDetailActivity.this, "Foto berhasil diambil", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void uploadImageToServer(Bitmap bitmap, String status){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        // Konversi byte array menjadi MultipartBody.Part
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        // Debug start
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        mTry.setText(encodedImage);
        // Debug end
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), imageBytes);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("encodedImage", mIdBooking + "_" + mStatusExtra + ".png", requestFile);
        Log.d(TAG, "uploadImageToServer: " + multipartBody + status + mIdBooking);
        ProgressDialog progressDialog = ProgressDialog.show(LoanDetailActivity.this, "", "Loading ...", true);
        mManagedLoanViewModel.updateGambar(multipartBody,status,Integer.parseInt(mIdBooking)).observe(LoanDetailActivity.this, data ->{
            // Action after upload Image
            UpdateData(data);
            progressDialog.dismiss();
        });
    }

    private void dispatchTakePictureIntent() {
        // Fungsi pemanggilan implisit intent camera
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void UpdateDetailBooks(List<Object[]> listdata){
        mDetailBooksList = DLAHelper.getBookDetailList(listdata);
        mBooksDetailAdapter = new BooksDetailAdapter(mDetailBooksList);
        mRVDetailBooks.setAdapter(mBooksDetailAdapter);
    }
    private void UpdateData(String data){
        mStatus.setText(data);
        if(data.equals("Pengajuan")){
            if (dateAfter14Days.after(date1)) {
                mStatus.setText(data);
                mMaterialCardView.setCardBackgroundColor(colorPengajuan);
            }else {
                mStatus.setText("Denda");
                mMaterialCardView.setCardBackgroundColor(colorDitolak);
            }
        }else if(data.equals("Diterima")){
            if (dateAfter14Days.after(date1)) {
                mStatus.setText(data);
                mMaterialCardView.setCardBackgroundColor(colorDiterima);
            }else {
                mStatus.setText("Denda");
                mMaterialCardView.setCardBackgroundColor(colorDitolak);
            }
        }else if(data.equals("Ditolak")){
            mMaterialCardView.setCardBackgroundColor(colorDitolak);
        }else if(data.equals("Dipinjam")){
            if (dateAfter14Days.after(date1)) {
                mStatus.setText(data);
                mMaterialCardView.setCardBackgroundColor(colorDipinjam);
            }else {
                mStatus.setText("Denda");
                mMaterialCardView.setCardBackgroundColor(colorDitolak);
            }
        }else if(data.equals("Selesai")){
            if (dateAfter14Days.after(date1)) {
                mStatus.setText(data);
                mMaterialCardView.setCardBackgroundColor(colorSelesai);
            }else {
                mStatus.setText("Denda");
                mMaterialCardView.setCardBackgroundColor(colorDitolak);
            }
        }else if(data.equals("Batal")){
            mMaterialCardView.setCardBackgroundColor(colorBatal);
        }
        mBtnTerima.setVisibility(View.GONE);
        mBtnTolak.setVisibility(View.GONE);
        mBtnCamera.setVisibility(View.GONE);
        mUpdateGambar.setVisibility(View.GONE);
        mBtnCancel.setVisibility(View.GONE);
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
            return mBooksList.size();
        }

        private class BooksDetailHolder extends RecyclerView.ViewHolder{
            private TextView mTitle, mKategori, mBookId;
            private ImageView mCoverBook;
            private Object[] mObjects;
            public BooksDetailHolder(LayoutInflater inflater,ViewGroup parent){
                super(inflater.inflate(R.layout.item_loan_detail, parent, false));

                mTitle = itemView.findViewById(R.id.title_book_newest);
                mKategori = itemView.findViewById(R.id.title_book_kategori);
                mBookId = itemView.findViewById(R.id.book_id_item_loan);
                mCoverBook = itemView.findViewById(R.id.cover_book_newest);
//                itemView.setOnClickListener(this);
            }
            public void bind(Object[] objects){
                mObjects = objects;
                mBookId.setText(objects[1].toString());
                mTitle.setText(objects[2].toString());
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
        }
    }
    public void onImageViewClicked(View view) {
        // Kembali ke activity sebelumnya
        finish();
    }
}