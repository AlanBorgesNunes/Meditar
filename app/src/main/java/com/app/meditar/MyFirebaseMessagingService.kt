package com.app.meditar

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val channelId = "notification_channel"
const val channelName = "com.muita.megasorte"
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService: FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
       if (remoteMessage.notification != null){
        generationNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)

       }
    }

    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.muita.megasorte", R.layout.item_notificacao)
        remoteView.setTextViewText(R.id.title,title )
        remoteView.setTextViewText(R.id.message,message )
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.logoo)

        return remoteView
    }

    fun generationNotification(title: String, message: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pedingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext,
        channelId)
            .setSmallIcon(R.drawable.logoo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pedingIntent)

        builder = builder.setContent(getRemoteView(title, message))


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

       notificationManager.notify(0, builder.build())

    }

}