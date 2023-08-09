package com.astra.polytechnic.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ui.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    final String channelId = "notification_channel";
    final String channelName = "com.astra.polytechnic";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if(message.getNotification() != null){
            generatedNotification(message.getNotification().getTitle(),message.getNotification().getBody());
        }
    }

    public RemoteViews getRemoteView(String title, String message){
        RemoteViews remoteViews = new RemoteViews("com.astra.polytechnic", R.layout.notification);
        remoteViews.setTextViewText(R.id.title,title);
        remoteViews.setTextViewText(R.id.description,message);
        return remoteViews;
    }

    void generatedNotification(String title, String message){
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        long[] longArray = new long[]{1000,1000,1000,1000};

        PendingIntent pI = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channelId)
                .setSmallIcon(R.drawable.avatar_person)
                .setAutoCancel(true)
                .setVibrate(longArray)
                .setOnlyAlertOnce(true)
                .setContentIntent(pI);

        builder = builder.setContent(getRemoteView(title,message));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0,builder.build());
    }

}
