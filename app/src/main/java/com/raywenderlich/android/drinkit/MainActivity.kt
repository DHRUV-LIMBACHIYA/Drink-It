package com.raywenderlich.android.drinkit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

// TODO: import libraries

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

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

        // TODO: check in bundle extras for notification data
    }


    override fun onStart() {
        super.onStart()
        //TODO: Register the receiver for notifications
    }

    override fun onStop() {
        super.onStop()
        // TODO: Unregister the receiver for notifications
    }
    // TODO: Add a method for receiving notifications

    // TODO: Add a function to check for Google Play Services

    // TODO: Create a message receiver constant

    companion object {
        private const val TAG = "MainActivity"
    }
}