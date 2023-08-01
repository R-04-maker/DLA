package com.astra.polytechnic.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "LoginActivity";
    private ImageView mbtnSignIn, mbtnSignUp, mbtnGuest;
    private TextInputEditText mPassword,mNim;
    private TextInputLayout mPasswordLayout, mUsernameLayout;
    private UserViewModel mUserViewModel;
    String id_role,pass;
    String email;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref;
        mbtnSignIn = findViewById(R.id.btnSignIn);
        mbtnSignUp = findViewById(R.id.btnSignUp);
        mbtnGuest = findViewById(R.id.btnGuest);
        mNim = findViewById(R.id.input_username_login);
        mPassword = findViewById(R.id.input_password_login);
        mPasswordLayout = findViewById(R.id.txtLayoutPassword);
        mUsernameLayout = findViewById(R.id.txtLayoutUsername);
        mPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = mPassword.getText().toString();
                if(pass.equals("")){
                    Toast.makeText(LoginActivity.this,"Enter Password", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,pass, Toast.LENGTH_SHORT).show();
                }
            }
        });

        pref = getSharedPreferences("nomor",MODE_PRIVATE);
        Log.d(TAG, "nomor: " + pref.getString("nomor", ""));
        Log.d(TAG, "Role ID : " + pref.getString("id_role", ""));
        Log.d(TAG, "Role Name : " + pref.getString("deskripsi", ""));
        // Check preferences
        if(pref.contains("nomor")&& pref.contains("id_role")){
            id_role = pref.getString("id_role","");
            CheckRole();

        }else{
            mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            mbtnSignIn.setOnClickListener(view -> {
                String nim = mNim.getText().toString();
                String password = mPassword.getText().toString();

                if(validate(view)){
                    ProgressDialog progressDialog = ProgressDialog.show(this, "Sign In", "Signing in...");
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
                                    Log.d(TAG, "No hp : " + loginResponse.getUser().getHp());

                                    id_role = loginResponse.getUser().getId_role();
                                    email = loginResponse.getUser().getEmail();
                                    CheckRole();
                                    saveFireBaseToken();
                                } else{
                                    Toast.makeText(LoginActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
            });
            mbtnSignUp.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            });
            mbtnGuest.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, DashboardMemberActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
    public boolean validate(View v) {
        boolean emailValidation = ValidationHelper.requiredTextInputValidation(mUsernameLayout);
        boolean passwordValidation = ValidationHelper.requiredTextInputValidation(mPasswordLayout);
        Log.d(TAG, "validate: " + emailValidation);
        return passwordValidation && emailValidation;
    }
    public void CheckRole(){
        Log.d(TAG, "CheckRole: " + id_role);
        if (id_role.equals("ROL06")) {
            // ROL06 is Mahasiswa/Member
            Intent intent = new Intent(LoginActivity.this, DashboardMemberActivity.class);
            startActivity(intent);
            finish();
        } else if (id_role.equals("ROL01")) {
            // ROL01 is Admin
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void saveFireBaseToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        // Handle error
                        return;
                    }
                    // Get the token
                    String token = task.getResult();
                    Log.d("firebase",token);
                    mUserViewModel.saveFBtoken(token, email);
                    // Use the token as needed (e.g., send it to your server)
                });
    }
}