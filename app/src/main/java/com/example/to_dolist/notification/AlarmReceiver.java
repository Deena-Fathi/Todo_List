package com.example.to_dolist.notification;

import android.Manifest;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.to_dolist.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Send a notification to the channel to notify the user that their to-do is due.
        Notification notification = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.ic_checklist)
                .setContentTitle(context.getString(R.string.todo_notification_title))
                .setContentText(context.getString(R.string.todo_notification_description))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, notification);
    }
}
