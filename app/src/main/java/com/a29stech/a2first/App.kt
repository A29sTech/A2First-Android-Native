package com.a29stech.a2first

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App : Application() {

    companion object {
        const val NOTIFICATION_CH_ID = "My_Notification_Id"
    }


    override fun onCreate() {
        super.onCreate()
        // Codes.
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){

            var nChannel = NotificationChannel(
                NOTIFICATION_CH_ID,
                "Example Notification",
                NotificationManager.IMPORTANCE_HIGH)
            nChannel.description = "My Application"

            getSystemService( NotificationManager::class.java )
                .createNotificationChannel( nChannel )

        }


    }

}