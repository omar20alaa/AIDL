package app.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var service: IMyAidlInterface? = null
    private var bound: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.d("MainActivity", "Service connected")
            this@MainActivity.service = IMyAidlInterface.Stub.asInterface(service)
            bound = true

            val result = this@MainActivity.service?.add(1, 2)
            Log.d("MainActivity", "Result of add in onServiceConnected: $result")
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d("MainActivity", "Service disconnected")
            bound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, MyService::class.java).also { intent ->
            val success = bindService(intent, connection, Context.BIND_AUTO_CREATE)
            Log.d("MainActivity", "Service bind success: $success")
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(connection)
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}