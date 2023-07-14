package com.astra.polytechnic.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.astra.polytechnic.R;
import com.astra.polytechnic.helper.ValidationHelper;
import com.astra.polytechnic.model.*;
import com.astra.polytechnic.repository.*;
import com.astra.polytechnic.ViewModel.*;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

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
    String[] items={"P4","TPM","MI","TO","MK","TAB","TPHP","TKBG"};
    String prodi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponent();
    }

    private void initComponent(){

//        map.put("PR001","P4");
//        map.put("PR002","TPM");
//        map.put("PR003","MI");
//        map.put("PR004","TO");
//        map.put("PR005","MK");
//        map.put("PR006","TAB");
//        map.put("PR007","TPHP");
//        map.put("PR008","TKBG");

        mMsuserRepo=msuserRepo.get();

        memail = (EditText) findViewById(R.id.input_email_signup);
        mNIM=(EditText) findViewById(R.id.input_nomor_signup);
        mNama=(EditText)findViewById(R.id.input_nama_signup);
        mnotelp=(EditText) findViewById(R.id.input_mobile_phone_signup);
        mpassword=(EditText) findViewById(R.id.input_password_signup);
        mReenterPass=(EditText) findViewById(R.id.input_retype_password_signup);
        mSignUp= (ImageView) findViewById(R.id.regist);

        mTextInputLayout=findViewById(R.id.dropdown_menu);
        mAutoCompleteTextView=findViewById(R.id.actv_dd_prodi);
        mArrayAdapter=new ArrayAdapter<>(SignUpActivity.this,R.layout.list_dd_prodi,items);
        mAutoCompleteTextView.setAdapter(mArrayAdapter);
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                prodi=parent.getItemAtPosition(i).toString();
                Toast.makeText(SignUpActivity.this, prodi, Toast.LENGTH_SHORT).show();
            }
        });

        mSignUp.setOnClickListener(view -> {
            setProdi();
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
            Date creadate= Calendar.getInstance().getTime();
            String modiby="";
            Date modidate= Calendar.getInstance().getTime();

            UserViewModel userViewModel=new UserViewModel();
            msuser user=new msuser(email,nomor,nama,instansi,alamat,hp,password
                    ,id_role,id_prodi,status,creaby,creadate,modiby,modidate);

            if (validate(view)) {
                //ProgressDialog progressDialog = ProgressDialog.show(this, "Sign Up", "Signing Up...");
                userViewModel.register(user);
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
    public void setProdi(){
        if (prodi=="P4"){
            prodi="PRO01";
        } else if (prodi=="TPM") {
            prodi="PRO02";
        } else if (prodi=="MI") {
            prodi="PRO03";
        }else if (prodi=="TO") {
            prodi="PRO04";
        }else if (prodi=="MK") {
            prodi="PRO05";
        }else if (prodi=="TAB") {
            prodi="PRO06";
        }else if (prodi=="TPHP") {
            prodi="PRO07";
        }else if (prodi=="TKBG") {
            prodi="PRO08";
        }
    }
}

