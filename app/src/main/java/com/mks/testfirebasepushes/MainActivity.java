package com.mks.testfirebasepushes;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    public static final String ELARI_TOKEN = "ElariToken";

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
                    }
                });
    }
}
