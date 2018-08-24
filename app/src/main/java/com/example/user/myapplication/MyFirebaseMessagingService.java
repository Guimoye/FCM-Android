package com.example.user.myapplication;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.StringTokenizer;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "FirebaseMessagingService";

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.e(TAG, "From: " + from);
        if (remoteMessage.getNotification() != null) {
            mostrarNoticacion(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }


        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());

            if (true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //     scheduleJob();
            } else {
                // Handle message within 10 seconds
                //    handleNow();
            }

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void mostrarNoticacion(String titulo, String cuerpo) {

        StringTokenizer tokens=new StringTokenizer(cuerpo, "▄");
        int nDatos=tokens.countTokens();
        String[] datos=new String[nDatos];
        int i=0;
        while(tokens.hasMoreTokens()){
            String str=tokens.nextToken();
            datos[i]=str;
            i++;
        }

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("url",datos[1]);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

       // Uri sounUri = new RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);




        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(titulo)
                .setContentText(datos[0])
                .setAutoCancel(true)
              //  .setSound(sounUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,noBuilder.build());
    }
}
