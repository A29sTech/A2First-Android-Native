package com.a29stech.a2first
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    class ServiceStatusReceiver( reRun:MainActivity ) : BroadcastReceiver() {

        var reRunner: MainActivity = reRun

        override fun onReceive(context: Context?, intent: Intent?) {
            var isRunning = intent?.getBooleanExtra( "isRunning", false ) ?: false
            reRunner.updateServiceStatus( isRunning )
        }
    }
    var serviceStatusReceiver = ServiceStatusReceiver( this )

    private var isRunning = false


    fun checkPermission() {
        if ( ContextCompat
                .checkSelfPermission(this ,
                    android.Manifest.permission.FOREGROUND_SERVICE
                ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, Array<String>(5){ android.Manifest.permission.FOREGROUND_SERVICE }, 1 )
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        registerReceiver(serviceStatusReceiver, IntentFilter( "com.a29stech.a2frist.SERVICE_STATUS" ))
        startService( Intent(this, BgService::class.java) )

    }

    fun updateServiceStatus( serviceStatus: Boolean ) {
        isRunning = serviceStatus
        updateView()
    }

    fun toggleService(v : View) {

        var intentOfService = Intent( this, BgService::class.java );

        if ( !isRunning ) {
            intentOfService.putExtra("SERVICE_NAME","LOG_IN_EFFECT_START")
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
                startForegroundService( intentOfService )
            } else {
                startService( intentOfService )
            }
            btnTglService.isEnabled = false
        } else {
            stopService(intentOfService)
            isRunning = false
            updateView()
        }
    }

    private fun updateView(){
        btnTglService.isEnabled = true
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

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver( serviceStatusReceiver )
    }

}
