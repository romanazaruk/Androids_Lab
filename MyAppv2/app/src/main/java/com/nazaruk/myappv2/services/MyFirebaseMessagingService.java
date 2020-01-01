package com.nazaruk.myappv2.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nazaruk.myappv2.Constants;
import com.nazaruk.myappv2.DetailsActivity;
import com.nazaruk.myappv2.R;

import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().get("movieId") != null) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(Constants.MOVIE_ID.getValue(), Objects.requireNonNull(remoteMessage.getData().get("movieId")));
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

            sendNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(),
                    Objects.requireNonNull(remoteMessage.getNotification()).getBody(), pendingIntent);
        }
    }

    private void sendNotification(String title, String contentText, PendingIntent pendingIntent) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.ic_notifications_24dp))
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setContentText(contentText)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
