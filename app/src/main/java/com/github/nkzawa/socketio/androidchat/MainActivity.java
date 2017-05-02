package com.github.nkzawa.socketio.androidchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity  implements  ActivityInteraction{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    public void InitiateLoginActivity() {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
