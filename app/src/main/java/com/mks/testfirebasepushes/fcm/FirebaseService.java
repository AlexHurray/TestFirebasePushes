package com.mks.testfirebasepushes.fcm;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mks.testfirebasepushes.MainActivity;
import com.mks.testfirebasepushes.event.SetImageEvent;

import org.greenrobot.eventbus.EventBus;

public class FirebaseService extends FirebaseMessagingService {
    private final static String TAG = "FirebaseService";
    public static final String TYPE_FOTO = "foto";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() == null) {
            String url = remoteMessage.getData().get("URL");
            String type = remoteMessage.getData().get("type");

            if (type != null && type.equalsIgnoreCase(TYPE_FOTO)) {
                //Glide.with(get).applyDefaultRequestOptions(imageOption).load(match.getTeam1Img()).into(player1Image);
                EventBus.getDefault().post(new SetImageEvent(url));
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        } else {
            super.onMessageReceived(remoteMessage);
        }
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onNewToken(String refreshedToken) {
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}

