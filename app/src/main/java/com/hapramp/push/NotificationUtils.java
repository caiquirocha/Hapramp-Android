package com.hapramp.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.hapramp.R;
import com.hapramp.activity.NotificationsActivity;
import com.hapramp.models.response.NotificationResponse;

/**
 * Created by Ankit on 12/27/2017.
 */

// Issues: https://docs.telerik.com/platform/knowledge-base/troubleshooting/troubleshooting-cannot-receive-push-notifications-on-android-when-the-app-is-closed
public class NotificationUtils {

    public static void showNotification(Context mContext, NotificationResponse.Notification notificationObject){

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final int icon = R.mipmap.ic_launcher;
        Intent notificationIntent = new Intent(mContext, NotificationsActivity.class);

        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);

        Notification notification;
        notification = mBuilder
                .setSmallIcon(icon)
                .setTicker(notificationObject.content)
                .setWhen(0)
                .setAutoCancel(true)
                .setSound(uri)
                .setColor(mContext.getResources().getColor(R.color.colorAccent))
                .setContentTitle("Hapramp")
                .setContentText(notificationObject.content)
                .setContentIntent(resultPendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationObject.content))
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, notification);
    }
}
