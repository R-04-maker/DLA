package com.astra.polytechnic.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.astra.polytechnic.model.*;

import com.astra.polytechnic.R;
import com.astra.polytechnic.repository.msMemberRepo;
import com.astra.polytechnic.ui.fragment.BookDetailFragment;
import com.astra.polytechnic.ui.fragment.HomeFragment;
import com.astra.polytechnic.ui.fragment.HomeMemberFragment;
import com.astra.polytechnic.ui.fragment.RequestDetailFragment;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mbtnSignIn,mbtnSignUp,mbtnGuest;
    private EditText mNim,mPassword;
    private msMemberRepo mMsMemberRepo;
    private msMember mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mbtnSignIn = findViewById(R.id.btnSignIn);
        mbtnSignUp = findViewById(R.id.btnSignUp);
        mbtnGuest = findViewById(R.id.btnGuest);
        mNim =(EditText)findViewById(R.id.input_username_login);
        mPassword=(EditText) findViewById(R.id.input_password_login);

        mbtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(mNim.getText().toString())){
                    Toast.makeText(LoginActivity.this,"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }

                // If email is empty, return
                if (TextUtils.isEmpty(mPassword.getText().toString())){
                    Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }


                mMsMemberRepo = msMemberRepo.get();
                String username=mNim.getText().toString();
                String password=mPassword.getText().toString();
                mMsMemberRepo.Login(username,password);
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        mbtnSignUp.setOnClickListener(this);
        mbtnGuest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                break;
            case R.id.btnGuest:
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(false)
                        .replace(R.id.fragment_try, RequestDetailFragment.class, null)
                        .commit();
//                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                break;
        }
    }

    private void checkLogin(Context context){

    }
}