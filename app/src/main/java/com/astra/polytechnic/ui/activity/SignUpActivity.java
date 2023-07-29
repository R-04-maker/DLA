package com.astra.polytechnic.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
    private EditText memail;
    private EditText mNIM;
    private EditText mNama;
    private EditText mnotelp;
    private ImageView mSignUp;
    private EditText mpassword;
    private EditText mReenterPass;
    ProgressDialog loading;
    private msuser mMsuser;
    private msuserRepo mMsuserRepo;

    private UserViewModel mUserViewModel;
    private TextInputLayout mTextInputLayout;
    private AutoCompleteTextView mAutoCompleteTextView;
    private ArrayAdapter<String> mArrayAdapter;

    //Map<String,String> map=new HashMap<>();
    //String[] items={"P4","TPM","MI","TO","MK","TAB","TPHP","TKBG"};
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
        mTextInputLayout=findViewById(R.id.dropdown_menu);
        mAutoCompleteTextView=findViewById(R.id.actv_dd_prodi);
//        map.put("PR001","P4");
//        map.put("PR002","TPM");
//        map.put("PR003","MI");
//        map.put("PR004","TO");
//        map.put("PR005","MK");
//        map.put("PR006","TAB");
//        map.put("PR007","TPHP");
//        map.put("PR008","TKBG");
        mMsprodis=new ArrayList<>();
        mMsprodiViewModel=new ViewModelProvider(this).get(msprodiViewModel.class);
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
        mMsuserRepo=msuserRepo.get();

        memail = (EditText) findViewById(R.id.input_email_signup);
        mNIM=(EditText) findViewById(R.id.input_nomor_signup);
        mNama=(EditText)findViewById(R.id.input_nama_signup);
        mnotelp=(EditText) findViewById(R.id.input_mobile_phone_signup);
        mpassword=(EditText) findViewById(R.id.input_password_signup);
        mReenterPass=(EditText) findViewById(R.id.input_retype_password_signup);
        mSignUp= (ImageView) findViewById(R.id.regist);

        mArrayAdapter = new ArrayAdapter<>(SignUpActivity.this, R.layout.list_dd_prodi, prodiNames);
        mAutoCompleteTextView.setAdapter(mArrayAdapter);
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView <?> parent, View view,int i, long l){
                if (parent != null) {
                    //get prodi id from prodi name
                    prodi = prodiIds.get(i);
                    Toast.makeText(SignUpActivity.this, prodi, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //set prodi


        Date  dateTimeNow = new Date();

        SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        mSignUp.setOnClickListener(view -> {
            System.out.println(prodi);
            String email=memail.getText().toString();
            String nomor=mNIM.getText().toString();
            String nama=mNama.getText().toString();
            String instansi="POLITEKNIK ASTRA";
            String alamat="";
            String hp=mnotelp.getText().toString();
            String password=mpassword.getText().toString();
            String id_role="ROL06";
            String id_prodi=prodi;
            Integer status=1;
            String creaby=mNama.getText().toString();
            String creadate= formatter.format(dateTimeNow);
            String modiby="";
            String modidate= formatter.format(dateTimeNow);
            msprodi msprodi=new msprodi();
            msprodi.setId_prodi(id_prodi);
            msrole role =new msrole();
            role.setId_role(id_role);
            UserViewModel userViewModel=new UserViewModel();
            msuser user=new msuser(email,nomor,nama,instansi,alamat,hp,password
                    ,role,msprodi,status,creaby,creadate,modiby,modidate);

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

    public boolean validate(View view) {
        boolean nameValidation = ValidationHelper.requiredTextInputValidation(mNama);
        boolean emailValidation = ValidationHelper.requiredTextInputValidation(memail);
        boolean passwordValidation = ValidationHelper.requiredTextInputValidation(mpassword);
        boolean confirmationValidation = ValidationHelper.confirmationValidation(mpassword, mReenterPass);

        return nameValidation && emailValidation && passwordValidation && confirmationValidation;
    }
}

