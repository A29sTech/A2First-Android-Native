package com.a29stech.a2first

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import java.lang.Exception

class MyBroadcastReceiver: BroadcastReceiver() {

    private var isMaleVoice = false


    override fun onReceive(context: Context?, intent: Intent?) {

        try {
            var intentActionType = intent?.action ?: "ERROR"
            if ( intentActionType == "com.a29stech.a2first.CHANGE_VOICE" ) {
                isMaleVoice = !isMaleVoice
                if ( isMaleVoice ) {
                    Toast.makeText(context, "Male", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Female", Toast.LENGTH_SHORT).show()
                }
            } else {

                Toast.makeText(context, "Access Granted", Toast.LENGTH_SHORT).show()
                if ( isMaleVoice ) {
                    MediaPlayer.create(context, R.raw.male_voice).start()
                } else MediaPlayer.create(context, R.raw.access_granted).start()
            }
        } catch (e:Exception) {
            Toast.makeText(context, "Some Problem Occur", Toast.LENGTH_SHORT).show()
        }

    }

}