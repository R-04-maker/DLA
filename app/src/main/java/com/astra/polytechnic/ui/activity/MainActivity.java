package com.astra.polytechnic.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.astra.polytechnic.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;

    private ImageButton mbtnGetStarted;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        if (isOpen())
        {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else
        {
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
                if (!isNotificationPermissionGranted()) {
                    // Jika belum diberikan, minta izin notifikasi
                    requestNotificationPermission();
                }
            }
        });
    }
    private boolean isOpen() {
        SharedPreferences sharedPreferences = getSharedPreferences("startup",MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("startup",false);
        Log.d(TAG, "isOpen: " + result);
        return result;
    }
    private boolean isNotificationPermissionGranted() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        return notificationManagerCompat.areNotificationsEnabled();
    }
    private void requestNotificationPermission() {
        try {
            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            // Jika perangkat tidak mendukung intent untuk membuka pengaturan notifikasi, tampilkan pesan
            Toast.makeText(this, "Unable to open notification settings", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            // Setelah pengguna mengatur izin notifikasi, cek izin lagi
            if (isNotificationPermissionGranted()) {
                // Jika izin diberikan, lanjutkan ke Login
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                // Jika izin tidak diberikan, Anda bisa menampilkan pesan atau tindakan lain
                Toast.makeText(this, "Notification permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}