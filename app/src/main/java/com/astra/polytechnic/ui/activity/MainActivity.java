package com.astra.polytechnic.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.astra.polytechnic.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ImageButton mbtnGetStarted;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        if (isOpen())
        {
            Log.d(TAG, "onCreate: " + "if");
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else
        {
            Log.d(TAG, "onCreate: " + "else");
            SharedPreferences.Editor editor = getSharedPreferences("startup",MODE_PRIVATE).edit();
            editor.putBoolean("startup", true);
            editor.commit();
        }

        mbtnGetStarted = findViewById(R.id.btnGetStarted);
        mbtnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean isOpen() {
        SharedPreferences sharedPreferences = getSharedPreferences("startup",MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("startup",false);
        Log.d(TAG, "isOpen: " + result);
        return result;
    }
}