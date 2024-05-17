package app.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    private val binder = object : IMyAidlInterface.Stub() {
        override fun add(x: Int, y: Int): Int {
            Log.d("MyService", "add method called with: $x, $y")
            return x + y
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d("MyService", "Service bound")
        return binder
    }
}