package com.raywenderlich.android.drinkit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailabilityLight
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_retrieve_token.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }
                val token = task.result // Token
                val message = getString(R.string.token_prefix, token)
                Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
            }
        }

        if(checkGooglePlayServices()){
            Log.i(TAG, "onCreate: Ready to receive notifications")
        }else {
            Log.w(TAG, "Device doesn't have google play services")
        }

        broadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
              text_view_notification.text = intent?.extras?.getString("message") // Set notification message into Notification TextView.
            }
        }

    }


    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, IntentFilter("MyData")) // Register LocalBroadCastManager
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver) // Unregister LocalBroadcastManager.
    }

    // TODO: Add a method for receiving notifications



    // A function to check for Google Play Services
    private fun checkGooglePlayServices(): Boolean{
        val status = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(this) // Check google play service availability.
        return if(status != ConnectionResult.SUCCESS){
            Log.e(TAG, "checkGooglePlayServices: Google Play Service is not available")
            false
        }else {
            Log.i(TAG, "checkGooglePlayServices: Google Play Service is available")
            true
        }
    }

    // TODO: Create a message receiver constant

    companion object {
        private const val TAG = "MainActivity"
    }
}