package com.a29stech.a2first

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.a29stech.a2first.App.Companion.NOTIFICATION_CH_ID

class BgService: Service() {

    private var isRunning = false

    private lateinit var receiver: MyBroadcastReceiver

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun sendResult() {
        sendBroadcast(
            Intent("com.a29stech.a2frist.SERVICE_STATUS")
            .putExtra("isRunning", isRunning))
    }

    private fun createNotification( ) {

        var customNotificationBar =  RemoteViews( packageName, R.layout.notification )

        var notification = NotificationCompat.Builder( this, NOTIFICATION_CH_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setCustomContentView( customNotificationBar )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .build()

        startForeground(1, notification)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var action = intent?.getStringExtra( "SERVICE_NAME" ) ?: "ERROR"

        if ( action == "LOG_IN_EFFECT_START" ) {
            createNotification()
            receiver = MyBroadcastReceiver()
            var intentFilter = IntentFilter( Intent.ACTION_USER_PRESENT )
            intentFilter.addAction("com.a29stech.a2first.CHANGE_VOICE")
            registerReceiver( receiver, intentFilter )
            isRunning = true
            sendResult()

        } else sendResult()

        if ( !isRunning ) stopSelf()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::receiver.isInitialized) unregisterReceiver( receiver )
    }
}