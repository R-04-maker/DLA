package com.astra.polytechnic.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.astra.polytechnic.helper.DateConverter;
import com.astra.polytechnic.model.Booking;
import com.astra.polytechnic.ui.fragment.UnconfirmedFragment;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
//import java.util.Base64;
import android.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LoanDetailActivity extends AppCompatActivity {
    private static final String TAG = "LoanDetailActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSIONS_REQUEST_CAMERA = 2;
    private String mIdBooking,mStatusExtra;
    private Button mBtnTolak, mBtnTerima, mBtnCamera, mUpdateGambar;
    private MaterialCardView mMaterialCardView;
    private TextView mStatus,mBookingId,mBookingDate,mBookingReturn,mNIM,mName,mProdi,mHp,mTry;
    private RecyclerView mRVDetailBooks;
    private ManagedLoanViewModel mManagedLoanViewModel;
    private List<Object[]> mDetailBooksList;
    private Bitmap imageBitmap;
    private ImageView imageView;

    private BooksDetailAdapter mBooksDetailAdapter = new BooksDetailAdapter(Collections.emptyList());
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_detail);
        mIdBooking = getIntent().getStringExtra("id_booking");
        // Initialize Component
        mStatus = findViewById(R.id.loan_status);
        mBookingId = findViewById(R.id.booking_code_req_detail);
        mBookingDate = findViewById(R.id.booking_date);
        mBookingReturn = findViewById(R.id.booking_return_date);
        mNIM = findViewById(R.id.nim);
        mName = findViewById(R.id.name_profile);
        mProdi = findViewById(R.id.prodi);
        mHp = findViewById(R.id.noHp);
        mMaterialCardView = findViewById(R.id.materialCardView);

        mBtnTerima = findViewById(R.id.btn_terima);
        mBtnTolak = findViewById(R.id.btn_tolak);
        mBtnCamera = findViewById(R.id.btn_camera);

        mRVDetailBooks = findViewById(R.id.rv_detail_loan);
        mRVDetailBooks.setLayoutManager(new LinearLayoutManager(this));
        mRVDetailBooks.setAdapter(mBooksDetailAdapter);

        imageView = findViewById(R.id.image_result);
        mUpdateGambar = findViewById(R.id.update_gambar);
        mTry = findViewById(R.id.try_textview);

        mManagedLoanViewModel = new ViewModelProvider(this).get(ManagedLoanViewModel.class);

        mManagedLoanViewModel.getDetailBooking(Integer.parseInt(mIdBooking)).observe(this,objects -> {
            Object[] obj = objects.get(0);
            mBookingId.setText(obj[1].toString());
            mStatus.setText(obj[2].toString());
            mBookingDate.setText(DateConverter.fromDbDateTimeTo(DATE_FORMAT, obj[3].toString()).toString());
            mBookingReturn.setText(DateConverter.fromDbDateTimeTo(DATE_FORMAT,obj[4].toString()).toString());
            mNIM.setText(obj[5].toString());
            mName.setText(obj[6].toString());
            mProdi.setText(obj[8].toString());
            mHp.setText(obj[9].toString());

            // Set cardView background for status and Button Action with status param
            int colorDiterima = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_diterima);
            int colorDitolak = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_ditolak);
            int colorPengajuan = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_pengajuan);
            int colorDipinjam = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_dipinjam);
            int colorSelesai = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_selesai);
            mStatus.setText(obj[2].toString());
            mStatusExtra = obj[2].toString();
            if(mStatusExtra.equals("Pengajuan")){
                mMaterialCardView.setCardBackgroundColor(colorPengajuan);
                mBtnTerima.setVisibility(View.VISIBLE);
                mBtnTolak.setVisibility(View.VISIBLE);
                mBtnCamera.setVisibility(View.GONE);
            }else if(mStatusExtra.equals("Diterima")){
                mMaterialCardView.setCardBackgroundColor(colorDiterima);
                mBtnTolak.setVisibility(View.GONE);
                mBtnTerima.setVisibility(View.GONE);
                mBtnCamera.setVisibility(View.VISIBLE);
            }else if(mStatusExtra.equals("Ditolak")){
                mMaterialCardView.setCardBackgroundColor(colorDitolak);
            }else if(mStatusExtra.equals("Dipinjam")){
                mMaterialCardView.setCardBackgroundColor(colorDipinjam);
                mBtnTolak.setVisibility(View.GONE);
                mBtnTerima.setVisibility(View.GONE);
                mBtnCamera.setVisibility(View.VISIBLE);
            }else if(mStatusExtra.equals("Selesai")){
                mMaterialCardView.setCardBackgroundColor(colorSelesai);
                mBtnTolak.setVisibility(View.GONE);
                mBtnTerima.setVisibility(View.GONE);
            }
        });

        mManagedLoanViewModel.getBookDetailBooking().observe(this,this::UpdateDetailBooks);

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
                        mManagedLoanViewModel.updateDetailBooking(Integer.parseInt(mIdBooking), "Diterima").observe(LoanDetailActivity.this,data -> {
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
/*        PackageManager packageManager = getActivity().getPackageManager();
        // Check apakah terdapat activity yg dapat menangani camera
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(packageManager.resolveActivity(captureImage,PackageManager.MATCH_DEFAULT_ONLY) == null){
            mBtnCamera.setEnabled(false);
        }*/
        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dispatchTakePictureIntent();
                // Membuat Intent untuk mengambil gambar menggunakan kamera
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                    // Membuat file tempat gambar akan disimpan
//                    File photoFile = null;
//                    try {
//                        photoFile = createImageFile();
//                    } catch (IOException ex) {
//                        // Menghandle exception jika terjadi error saat membuat file
//                        ex.printStackTrace();
//                    }
//                    // Melanjutkan hanya jika file tempat gambar berhasil dibuat
//                    if (photoFile != null) {
//                        Uri photoURI = FileProvider.getUriForFile(LoanDetailActivity.this,
//                                "com.example.android.fileprovider",
//                                photoFile);
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                    }
//                }
            }
        });
        mUpdateGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageBitmap != null){
                    uploadImageToServer(imageBitmap);
                }else {
                    Toast.makeText(LoanDetailActivity.this, "No image captured!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            if (extras != null) {
                imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }
    private void uploadImageToServer(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        mTry.setText(encodedImage);

        // Konversi byte array menjadi MultipartBody.Part
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("encodedImage", "image.jpg", requestFile);
        mManagedLoanViewModel.updateGambar(multipartBody).observe(LoanDetailActivity.this, data ->{
            // Action after upload Image
        });
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void UpdateDetailBooks(List<Object[]> listdata){
        Log.d(TAG, "UpdateDetailBooks: " + listdata);
        mDetailBooksList = DLAHelper.getBookDetailList(listdata);
        mBooksDetailAdapter = new BooksDetailAdapter(mDetailBooksList);
        mRVDetailBooks.setAdapter(mBooksDetailAdapter);
    }
    private void UpdateData(String data){
        Log.d(TAG, "UpdateData: " + data);
        int colorDiterima = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_diterima);
        int colorDitolak = ContextCompat.getColor(LoanDetailActivity.this, R.color.card_ditolak);
        mStatus.setText(data);
        if(data.equals("Diterima")){
            mMaterialCardView.setCardBackgroundColor(colorDiterima);
        }else {
            mMaterialCardView.setCardBackgroundColor(colorDitolak);
        }
        mBtnTerima.setVisibility(View.GONE);
        mBtnTolak.setVisibility(View.GONE);
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
        }
    }
    private File createImageFile() throws IOException {
        // Membuat nama file berdasarkan timestamp unik
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // Mendapatkan direktori penyimpanan gambar yang sesuai
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // Membuat file gambar tempat gambar akan disimpan
        File imageFile = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        // Mengembalikan file gambar yang berhasil dibuat
        return imageFile;
    }
}