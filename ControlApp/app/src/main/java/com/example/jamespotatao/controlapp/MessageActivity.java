package com.example.jamespotatao.controlapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity{

    private TextView mTextview;
    MyBrodcast myBrodcast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mTextview = findViewById(R.id.tv_message);

        myBrodcast = new MyBrodcast();
        IntentFilter filter = new IntentFilter("com.example.jamespotato.controlapp");
        registerReceiver(myBrodcast,filter);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myBrodcast);
        super.onDestroy();
    }

    public class MyBrodcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            mTextview.setText(intent.getStringExtra("shift"));
        }
    }
}
