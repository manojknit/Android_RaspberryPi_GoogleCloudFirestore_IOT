package com.cloudjibe.android_googlecloudfirestore;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//https://github.com/firebase/quickstart-android/tree/1b310e0e0ea2279d7e1363edb7e4923d0fc55369/messaging/app/src/main/java/com/google/firebase/quickstart/fcm
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public String TAG = "MyFirebaseMessagingService" ;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        if (remoteMessage.getNotification() != null) {
            //Toast.makeText(getApplicationContext(), "This is my Toast Notification!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("Val", remoteMessage.getNotification().getTitle());
            startActivity(i);
        }
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        Log.d(TAG, "Short lived task is done.");
        Toast.makeText(getApplicationContext(), "This is my Toast Notification!", Toast.LENGTH_LONG).show();
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }
    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}

