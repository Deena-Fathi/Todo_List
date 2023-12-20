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

/**
 * BroadcastReceiver responsible for handling alarm events and triggering notifications
 * to remind users of pending to-do tasks.
 *
 * - context            The application context.
 * - intent             The Intent object containing alarm information.
 * - notification       The notification to be displayed to the user.
 * - notificationManager The NotificationManagerCompat for managing notifications.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Called when the BroadcastReceiver receives an Intent broadcast.
     * Sends a notification to remind the user of a pending to-do task.
     *
     * @param context The application context.
     * @param intent  The Intent object containing alarm information.
     */
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
        // Check for notification permissions before displaying the notification.
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, notification);
    }
}
