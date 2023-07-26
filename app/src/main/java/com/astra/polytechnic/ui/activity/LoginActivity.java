package com.astra.polytechnic.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.UserViewModel;
import com.astra.polytechnic.ViewModel.msprodiViewModel;
import com.astra.polytechnic.helper.ValidationHelper;
import com.astra.polytechnic.model.LoginModel;
import com.astra.polytechnic.model.msprodi;
import com.astra.polytechnic.model.response.LoginResponse;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private ImageView mbtnSignIn;
    private ImageView mbtnSignUp;
    private ImageView mbtnGuest;
    private EditText mNim;
    private EditText mPassword;
    //private msMemberRepo mMsMemberRepo;
    private UserViewModel mUserViewModel;
    private LoginModel mLoginModel;
    List<msprodi> mMsprodis;
    List<String> prodiNames = new ArrayList<>();
    List<String> prodiIds = new ArrayList<>();
    private msprodiViewModel mMsprodiViewModel;

    String id_role;
    public boolean validate(View v) {
        boolean emailValidation = ValidationHelper.requiredTextInputValidation(mNim);
        boolean passwordValidation = ValidationHelper.requiredTextInputValidation(mPassword);

        return emailValidation && passwordValidation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref;
        mbtnSignIn = findViewById(R.id.btnSignIn);
        mbtnSignUp = findViewById(R.id.btnSignUp);
        mbtnGuest = findViewById(R.id.btnGuest);
        mNim =(EditText)findViewById(R.id.input_username_login);
        mPassword=(EditText) findViewById(R.id.input_password_login);
        pref = getSharedPreferences("nomor",MODE_PRIVATE);
        if(pref.contains("nomor")&& pref.contains("password")){
            if(pref.contains("id_role")){
                id_role = pref.getString("id_role","");
                CheckRole();
            }
        }else{
            mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            mMsprodiViewModel=new ViewModelProvider(this).get(msprodiViewModel.class);

            mbtnSignIn.setOnClickListener(view -> {
                String nim=mNim.getText().toString();
                String password=mPassword.getText().toString();

                mUserViewModel.login(nim,password).observe(this, new Observer<LoginResponse>() {
                    @Override
                    public void onChanged(LoginResponse loginResponse) {
                        if(loginResponse != null){
                            if (loginResponse.getStatus() == 200) {
                                // Set Shared Preference
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("email", loginResponse.getUser().getEmail());
                                editor.putString("nomor",loginResponse.getUser().getNomor());
                                editor.putString("nama",loginResponse.getUser().getNama());
                                editor.putString("id_role",loginResponse.getUser().getId_role());
                                editor.putString("deskripsi",loginResponse.getUser().getDeskripsi());
                                editor.putString("password", loginResponse.getUser().getPassword());
                                editor.putString("no_hp", loginResponse.getUser().getHp());
                                editor.apply();

                                id_role = loginResponse.getUser().getId_role();
                                CheckRole();
                            } else{
                                Toast.makeText(LoginActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });
            mbtnSignUp.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            });
            mbtnGuest.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, LoanDetailActivity.class);
                startActivity(intent);
            });
        }
    }

    public void CheckRole(){
        Log.d(TAG, "CheckRole: " + id_role);
        if (id_role.equals("ROL06")) {
            Intent intent = new Intent(LoginActivity.this, DashboardMemberActivity.class);
            startActivity(intent);
            finish();
        } else if (id_role.equals("ROL01")) {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                //startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                break;
            case R.id.btnGuest:
//                startActivity(new Intent(LoginActivity.this,LoanDetailActivity.class));
                break;
        }
    }
}