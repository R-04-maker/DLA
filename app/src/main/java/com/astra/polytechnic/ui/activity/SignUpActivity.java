package com.astra.polytechnic.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.astra.polytechnic.R;
import com.astra.polytechnic.model.*;
import com.astra.polytechnic.repository.msMemberRepo;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {
    private EditText musername;
    private EditText mNIM;
    private EditText mnotelp;
    private RadioButton mmale;
    private RadioButton mfemale;
    private ImageView mSignUp;
    private String mgender;
    private EditText mpassword;
    private EditText mReenterPass;
    ProgressDialog loading;
    private msMemberRepo mMsMemberRepo;
    private msMember member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponent();
    }

    private void initComponent(){
        mMsMemberRepo=msMemberRepo.get();

        musername = (EditText) findViewById(R.id.input_fullname_signup);
        mNIM=(EditText) findViewById(R.id.input_email_signup);
        mnotelp=(EditText) findViewById(R.id.input_mobile_phone_signup);
        mpassword=(EditText) findViewById(R.id.input_password_signup);
        mReenterPass=(EditText) findViewById(R.id.input_retype_password_signup);
        mSignUp= (ImageView) findViewById(R.id.regist);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            String Status="Aktif";
            String modf_by="ss";

            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            @Override
            public void onClick(View view) {
//                member= new msMember(mNIM.getText().toString(),musername.getText().toString(),mnotelp.getText().toString()
//                            ,mgender,mpassword.getText().toString(),Status,dateFormat.format(date),modf_by,dateFormat.format(date));
//                    mMsMemberRepo.addUser(member);
                String pass=mpassword.getText().toString();
                member= new msMember(mNIM.getText().toString(),musername.getText().toString(),mnotelp.getText().toString()
                        ,mgender,pass,Status,dateFormat.format(date),modf_by,dateFormat.format(date));
                mMsMemberRepo.addUser(member);
//                if (mpassword.getText().toString()==mReenterPass.getText().toString()){
//
//                } else if (mpassword.getText().toString().isEmpty()|mReenterPass.getText().toString().isEmpty()) {
//                    Toast.makeText(SignUpActivity.this, "PASSWORD EMPTY", Toast.LENGTH_SHORT).show();
//                } else if (mReenterPass.getText().toString().isEmpty()) {
//                    Toast.makeText(SignUpActivity.this, "REPASSWORD EMPTY", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }
}