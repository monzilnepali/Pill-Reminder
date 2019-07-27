package com.example.androidalarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import com.example.androidalarm.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

public class MyReceiver extends BroadcastReceiver {
    //this class is fired when system notify that event occur
    //notification variable
    private static String DEFAULT_CHANNEL_ID = "default_channel";
    private static String DEFAULT_CHANNEL_NAME = "Default";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        //System.out.println("intent action0  "+intent.getAction());
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {

            Intent serviceIntent = new Intent(context, MyService.class);
            context.startService(serviceIntent);
        } else {


            new Handler().post(new Runnable() {
                @Override
                public void run() {
                   launchApp(context,intent);
                }
            });








        }

    }

    private void launchApp(Context context, Intent intent) {
        //start new activity
        String id =intent.getStringExtra("id");
        System.out.println("id of alert intent  "+id);

        Intent alertIntent=new Intent(context,activity_alert.class);
        alertIntent.putExtra("id",Integer.parseInt(id));
        alertIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(alertIntent);

        System.out.println("opening notification of alarm");
        //1.Get reference to Notification Manager
        NotificationManager   mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel(mNotificationManager);

        //2.Build Notification with NotificationCompat.Builder
        Notification notification = new NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setContentTitle("Medicine Remainder")   //Set the title of Notification
                .setContentText("Take paracetamol ")    //Set the text for notification
                .setSmallIcon(R.drawable.capsule).build();

        //Send the notification.
        mNotificationManager.notify(1, notification);

    }


    /*
     * Create NotificationChannel as required from Android 8.0 (Oreo)
     * */
    public static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Create channel only if it is not already created
            if (notificationManager.getNotificationChannel(DEFAULT_CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(new NotificationChannel(
                        DEFAULT_CHANNEL_ID, DEFAULT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ));
            }
        }
    }
}
