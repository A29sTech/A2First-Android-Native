package com.a29stech.a2first

import android.app.Notification
import android.app.NotificationManager
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isRunning = getSharedPreferences("LOGIN_EFFECT", MODE_PRIVATE)
            .getBoolean("isRunning", false)
        updateView()

    }

    fun toogleService(v : View) {

        var editState = getSharedPreferences("LOGIN_EFFECT", MODE_PRIVATE).edit()
        var intentOfService = Intent( this, BgService::class.java );

        if ( !isRunning ) {
            intentOfService.putExtra("SERVICE_NAME","LOG_IN_EFFECT_START")
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
                startForegroundService( intentOfService )
            } else {
                startService( intentOfService )
            }
            editState.putBoolean("isRunning", true).apply()
            isRunning = true
        } else {
            intentOfService.putExtra("SERVICE_NAME","LOG_IN_EFFECT_STOP")
            stopService(intentOfService)
            editState.putBoolean("isRunning", false).apply()
            isRunning = false
        }
        updateView()
    }

    private fun updateView(){
        if ( isRunning ) {
            tvServiceStatus.text = "Login Effect is Running..."
            btnTglService.text = "Stop"
        } else {
            tvServiceStatus.text = "Login Effect is Disabled"
            btnTglService.text = "Start"
        }
    }


    fun changeVoice(v: View) {
        sendBroadcast( Intent("com.a29stech.a2first.CHANGE_VOICE"))
    }

}
