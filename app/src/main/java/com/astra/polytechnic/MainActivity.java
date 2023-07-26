package com.astra.polytechnic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.astra.polytechnic.ui.activity.DashboardActivity;
import com.astra.polytechnic.ui.activity.DashboardMemberActivity;
import com.astra.polytechnic.ui.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton mbtnGetStarted;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        mbtnGetStarted = findViewById(R.id.btnGetStarted);
        pref= getSharedPreferences("nomor",MODE_PRIVATE);
        mbtnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=pref.getString("nomor","");
                String password=pref.getString("password","");

                if (username.equals("") && password.equals("")){
                    Toast.makeText(MainActivity.this, "Silahkan Login Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }else{
                    String id_role = pref.getString("id_role", "");
                    if (id_role.equals("ROL06")) {
                        Intent intent = new Intent(MainActivity.this, DashboardMemberActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (id_role.equals("ROL01")) {
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}