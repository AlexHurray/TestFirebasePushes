package com.mks.testfirebasepushes;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mks.testfirebasepushes.event.SetImageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    public static final String ELARI_TOKEN = "ElariToken";
    private static final String KEY_URL = "KEY_URL";

    public static void start(@NonNull Context context, String url) {
        Intent startIntent = new Intent(context, MainActivity.class);
        startIntent.putExtra(KEY_URL, url);
        context.startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(ELARI_TOKEN, "getInstanceId failed", task.getException());

                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        if (BuildConfig.DEBUG) {
                            Log.d(ELARI_TOKEN, "token: " + token);
                        }

                        FirebaseMessaging.getInstance().subscribeToTopic("group_main");
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImageChanged(@NonNull SetImageEvent event) {
        String url = event.getUrl();
        if (url != null && !url.isEmpty()) {
            if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ){
                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );

            }
            else if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB )
                getWindow().getDecorView().setSystemUiVisibility( View.STATUS_BAR_HIDDEN );
            else{}
            RequestOptions imageOption = new RequestOptions()
                    .placeholder(R.drawable.placeholder_image)
                    .fallback(R.drawable.placeholder_image)
                    .centerInside();
            Glide.with(this).applyDefaultRequestOptions(imageOption).load(url).into((ImageView) findViewById(R.id.image_view));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
