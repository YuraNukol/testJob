package com.example.testjob;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btbPlus;
    ImageButton btnMinus;
    ImageButton btbNotification;

    TextView numbFragment;

    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    int numberF = 1;

    private NotificationManager nm;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btbPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btbNotification = findViewById(R.id.btnNotification);
        numbFragment = findViewById(R.id.numbFragment);

        btbPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btbNotification.setOnClickListener(this);



        nm =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(
                "1",
                "Notify",
                "Example News Channel");


               fragment = new Fragment_main();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
      //  fragmentTransaction.add(R.id.fr_place,fragment);
        fragmentTransaction.commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void createNotificationChannel(String id, String name,
                                             String description) {

        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel =
                new NotificationChannel(id, name, importance);

        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(
                new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        nm.createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {


       /* fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();*/
        fragmentTransaction = getSupportFragmentManager().beginTransaction();


        switch (view.getId()){
            case R.id.btnPlus:

                fragmentTransaction.remove(fragment);
                fragmentTransaction.add(R.id.fr_place,fragment);

                numberF++;
                numbFragment.setText(""+(numberF));
                break;

            case R.id.btnMinus:

                fragmentTransaction.remove(fragment);

                numberF--;
                numbFragment.setText(""+(numberF));
                break;
            case R.id.btnNotification:

                int notificationId = 101;

                Intent resultIntent = new Intent(this, MainActivity.class);

                PendingIntent pendingIntent =
                        PendingIntent.getActivity(
                                this,
                                0,
                                resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                String CHANNEL_ID = "1";

                Notification notification = new Notification.Builder(MainActivity.this, CHANNEL_ID)
                        .setContentTitle("You create a notification")
                        .setContentText("Notification"+numberF)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setChannelId(CHANNEL_ID)
                        .setContentIntent(pendingIntent)
                        .build();

                nm.notify(notificationId, notification);

                break;
        }

        fragmentTransaction.commit();

        if (numberF>1)btnMinus.setVisibility(View.VISIBLE);
        else btnMinus.setVisibility(View.INVISIBLE);

    }
}
