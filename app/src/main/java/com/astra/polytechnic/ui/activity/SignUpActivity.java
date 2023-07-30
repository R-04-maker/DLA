package com.astra.polytechnic.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.astra.polytechnic.R;
import com.astra.polytechnic.helper.ValidationHelper;
import com.astra.polytechnic.model.*;
import com.astra.polytechnic.model.response.AddResponse;
import com.astra.polytechnic.model.response.ListProdiResponse;
import com.astra.polytechnic.repository.*;
import com.astra.polytechnic.ViewModel.*;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText mEmail, mNIM, mNama, mnotelp, mpassword, mReenterPass;
    private ImageView mSignUp;
    ProgressDialog loading;
    private msuser mMsuser;
    private msuserRepo mMsuserRepo;

    private UserViewModel mUserViewModel;
    private TextInputLayout mProdiLayout, mNameLayout, mNIMLayout, mEmailLayout, mTeleponLayout, mPasswordLayout, mRetypePassLayout;
    private AutoCompleteTextView mAutoCompleteTextView;
    private ArrayAdapter<String> mArrayAdapter;
    String prodi;
    List<msprodi> mMsprodis;
    Map<String, String> programMap = new HashMap<>();
    List<String> prodiNames = new ArrayList<>();
    List<String> prodiIds = new ArrayList<>();
    private msprodiViewModel mMsprodiViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponent();
    }

    private void initComponent(){
        mProdiLayout = findViewById(R.id.dropdown_menu);
        mEmailLayout = findViewById(R.id.txtLayoutEmail);
        mProdiLayout = findViewById(R.id.dropdown_menu);
        mNameLayout  = findViewById(R.id.txtLayoutNama);
        mNIMLayout = findViewById(R.id.txtLayoutNIM);
        mTeleponLayout = findViewById(R.id.txtLayoutNotel);
        mPasswordLayout = findViewById(R.id.txtLayoutPassword);
        mRetypePassLayout = findViewById(R.id.txtLayoutRePassword);
        mAutoCompleteTextView = findViewById(R.id.actv_dd_prodi);
        mEmail = findViewById(R.id.input_email_signup);
        mNIM = findViewById(R.id.input_nomor_signup);
        mNama = findViewById(R.id.input_nama_signup);
        mnotelp = findViewById(R.id.input_mobile_phone_signup);
        mpassword = findViewById(R.id.input_password_signup);
        mReenterPass = findViewById(R.id.input_retype_password_signup);
        mSignUp = findViewById(R.id.regist);
        mMsprodis = new ArrayList<>();

        mMsprodiViewModel = new ViewModelProvider(this).get(msprodiViewModel.class);
        mMsprodiViewModel.getAllProdi().observe(this, new Observer<List<msprodi>>() {
            @Override
            public void onChanged(List<msprodi> msprodis) {
                mMsprodis=msprodis;
                for (msprodi msprodi:mMsprodis){
                    prodiNames.add(msprodi.getDeskripsi());
                    prodiIds.add(msprodi.getId_prodi());
                }
            }
        });

        mMsuserRepo = msuserRepo.get();
        mArrayAdapter = new ArrayAdapter<>(SignUpActivity.this, R.layout.list_dd_prodi, prodiNames);
        mAutoCompleteTextView.setAdapter(mArrayAdapter);
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = editable.toString().trim();
                if (TextUtils.isEmpty(email)) {
                    // Jika email kosong, tampilkan pesan error
                    mEmailLayout.setError("Email harus diisi");
                } else if (!isValidEmail(email)) {
                    // Jika email tidak valid, tampilkan pesan error
                    mEmailLayout.setError("Email tidak valid");
                } else {
                    // Jika email valid, hilangkan pesan error
                    mEmailLayout.setError(null);
                }
            }
        });
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView <?> parent, View view,int i, long l){
                if (parent != null) {
                    //get prodi id from prodi name
                    prodi = prodiIds.get(i);
                }
            }
        });
        //set prodi

        Date  dateTimeNow = new Date();

        SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        mSignUp.setOnClickListener(view -> {
            String email = mEmail.getText().toString();
            String nomor = mNIM.getText().toString();
            String nama = mNama.getText().toString();
            String instansi = "POLITEKNIK ASTRA";
            String alamat = "";
            String hp = mnotelp.getText().toString();
            String password = mpassword.getText().toString();
            String id_role = "ROL06";
            String id_prodi = prodi;
            Integer status = 1;
            String creaby = mNama.getText().toString();
            String creadate = formatter.format(dateTimeNow);
            String modiby = "";
            String modidate = formatter.format(dateTimeNow);

            UserViewModel userViewModel=new UserViewModel();
            msuser user=new msuser(email,nomor,nama,instansi,alamat,hp,password
                    ,id_role,id_prodi,status,creaby,creadate,modiby,modidate);

            if (validate(view)) {
                ProgressDialog progressDialog = ProgressDialog.show(this, "Sign Up", "Signing Up...");
                userViewModel.register(user).observe(this, new Observer<AddResponse>() {
                    @Override
                    public void onChanged(AddResponse registerResponse) {
                        if (registerResponse != null) {
                            if (registerResponse.getStatus() == 200) {
                                Toast.makeText(SignUpActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Register Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean validate(View view) {
        boolean emailValidation = ValidationHelper.requiredTextInputValidation(mEmailLayout);
        boolean nimValidation = ValidationHelper.requiredTextInputValidation(mNIMLayout);
        boolean nameValidation = ValidationHelper.requiredTextInputValidation(mNameLayout);
        boolean phoneValidation = ValidationHelper.requiredTextInputValidation(mTeleponLayout);
        boolean prodiValidation = ValidationHelper.requiredTextInputValidation(mProdiLayout);
        boolean passwordValidation = ValidationHelper.requiredTextInputValidation(mPasswordLayout);
        boolean repasswordValidation = ValidationHelper.requiredTextInputValidation(mRetypePassLayout);
        boolean confirmationValidation = ValidationHelper.confirmationValidation(mPasswordLayout, mRetypePassLayout);

        return nameValidation && emailValidation && passwordValidation && confirmationValidation;
    }
}

