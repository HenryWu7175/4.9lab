package edu.temple.a49lab

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountdownService : Service() {

    //this is the service scope that will be used to launch coroutines
    private val serviceScope = CoroutineScope(Dispatchers.Default)

    //not binding anything to the service in this app
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    //starts the service and starts the countdown
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            //unpacks the intent and gets the countdown number
        val countdownNumber = it.getIntExtra(COUNTDOWN_SERVICE_ACTION, 0)
            if (countdownNumber > 0) {
                //if the countdown number is valid, starts the countdown
                startCountdown(countdownNumber, startId)
            } else {
                //if the countdown number is invalid, logs a warning and stops the service
                Log.w("CountdownService", "Invalid countdown value received")
                stopSelf(startId)
            }
        }
        //returns START_NOT_STICKY to indicate that the service should not be restarted if it is killed
        return START_NOT_STICKY
    }

    private fun startCountdown(countdownValue: Int, startId: Int) {
        //starts a coroutine to perform the countdown
        serviceScope.launch {
            //loops through the countdown value and logs the current value
            for (i in countdownValue downTo 1) {
                //logs since we dont have a text view
                Log.d("CountdownService", "Countdown: $i")
                delay(1000)
            }
            Log.d("CountdownService", "Countdown finished")
            stopSelf(startId)
        }
    }
        override fun onDestroy() {
            super.onDestroy()
            serviceScope.cancel()
            Log.d("CountdownService", "Service destroyed")
        }


}