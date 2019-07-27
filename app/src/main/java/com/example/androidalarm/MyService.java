package com.example.androidalarm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.Calendar;

//service class is trigger when phone is reboot and we copy same alarm trigger event here lik ein mainactivity class

public class MyService extends IntentService {
    public MyService() {
        super("MyService");

    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //getting alarm data from database and register after device is bootup
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 101, alarmIntent, 0);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 44);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

    }
}
