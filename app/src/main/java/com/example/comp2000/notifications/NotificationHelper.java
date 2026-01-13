package com.example.comp2000.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.comp2000.R;

public class NotificationHelper {

    private static final String ALERT_CHANNEL_ID = "my_app_alerts_channel";
    private static final String ALERT_CHANNEL_TITLE = "My App Alerts";
    private static final String ALERT_CHANNEL_DESCRIPTION = "Notifications about updates inside the app";

    private static void ensureChannelExists(Context appContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(
                    ALERT_CHANNEL_ID,
                    ALERT_CHANNEL_TITLE,
                    importance
            );
            channel.setDescription(ALERT_CHANNEL_DESCRIPTION);

            NotificationManager mgr = appContext.getSystemService(NotificationManager.class);
            if (mgr != null) {
                mgr.createNotificationChannel(channel);
            }
        }
    }

    public static void pushAlert(Context appContext, String titleText, String bodyText, String categoryTag) {

        SharedPreferences prefs = appContext.getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        boolean allowed = true;

        if ("orders".equals(categoryTag)) {
            allowed = prefs.getBoolean("alerts_orders", true);
        } else if ("products".equals(categoryTag)) {
            allowed = prefs.getBoolean("alerts_products", true);
        }

        if (!allowed) return;

        ensureChannelExists(appContext);

        NotificationCompat.Builder notif = new NotificationCompat.Builder(appContext, ALERT_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_notify)
                .setAutoCancel(true)
                .setContentTitle(titleText)
                .setContentText(bodyText)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat nm = NotificationManagerCompat.from(appContext);

        if (ActivityCompat.checkSelfPermission(appContext, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        int id = (int) System.currentTimeMillis();
        nm.notify(id, notif.build());
    }
}
