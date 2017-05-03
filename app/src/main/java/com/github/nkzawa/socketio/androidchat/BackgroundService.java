package com.github.nkzawa.socketio.androidchat;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Drew on 4/30/2017.
 */

public class BackgroundService extends Service{


    //SharedPreferences prefs;
    private static BackgroundService bs;

    private DBController database_controller;
    //SharedPreferences.Editor editor;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    // happens when service starts
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        bs = this;
        // we need this for extracting username when 'emitting' steps to the server.
        //prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        // We need to initialize an intent filter that will recognize 'ACTION_TIME_TICK'
        IntentFilter filter = new IntentFilter(
                Intent.ACTION_TIME_TICK);

        // We need to register our local broadcast receiver
        registerReceiver(receiver, filter);

        Log.d("background_service", "BackgroundService Started!");

        // Lets initiate the database controller
        database_controller = new DBController(getApplicationContext(), getApplication());
        database_controller.OpenDB();

        // START_STICKY -- ? what does it mean? Research it.
        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.d("background_service", "BackgroundService Stopped!");

        //inside onDestroy you need to 'unregister' the broadcast receiver
        unregisterReceiver(receiver);
        database_controller.CloseDB();
        database_controller = null;

        super.onDestroy();
    }

    public static BackgroundService getbs()
    {
        return bs;
    }

    public DBController getdbc()
    {
        return database_controller;
    }






    // BroadcastRecevier receiver
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // extracing the string that the action is bringin in
            String action = intent.getAction();


            Log.d("broadcast_service", "action received:" + action.toString());


            // if our action contains "TIME_TICK" we upload to the server via socket
            if (action.contains("TIME_TICK")) {
                database_controller.RemoveMessages();
            }


        }

    };

}
