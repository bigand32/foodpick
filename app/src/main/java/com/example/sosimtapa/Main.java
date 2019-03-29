package com.example.sosimtapa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Main extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            //Do Something
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(Main.this,login.class); // xxx가 현재 activity,
                //yyy가 이동할 activity
                startActivity(i);
                finish();
            }
        }, 3000); // 1000ms
    }
}
