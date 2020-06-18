package com.dpridoy.foregroundservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.dpridoy.foregroundservice.App.CHANNEL_ID1;

public class BleService extends Service {

    MediaPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input=intent.getStringExtra("Extra");

        Intent notificationIntent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID1)
                .setContentTitle("Music Play")
                .setContentText(input)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);

        play();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void play(){
        Uri notif= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        player=MediaPlayer.create(this,notif);
        if(player.isPlaying()){
            player.stop();
        }else {
            player.setLooping(true);
            player.start();
        }
    }
}
