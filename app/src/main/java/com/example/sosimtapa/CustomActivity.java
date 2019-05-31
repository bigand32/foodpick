package com.example.sosimtapa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class CustomActivity extends Activity {

    private TextView tv_st_reason;
    private Button btn_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        init();

        Button button=(Button) findViewById(R.id.btn_counp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainTabFragment3.class);
                startActivity(intent);
            }
        });

        btn_button.setOnClickListener(new View.OnClickListener() {

            EditText[] editTextArray = new EditText[] { (EditText)findViewById(R.id.textView1),  (EditText) findViewById(R.id.textView2) ,  (EditText) findViewById(R.id.textView3)};

            Button Button33;

            @Override
            public void onClick(View v) {

                // 1~6 까지 랜덤 숫자를 얻는다.
                Random dice = new Random();
                //
                int result = dice.nextInt(3) + 1;
                EditText result2;


                if(result == 1){
                    result2 = editTextArray[0];
                }
                else if(result == 2){
                    result2 = editTextArray[1];
                }
                else {
                    result2 = editTextArray[2];
                }
                //
                // TextView에 표시한다.
                tv_st_reason.setText(result2.getText());
            }
        });
    }





    public void init() {
        tv_st_reason = (TextView) findViewById(R.id.tv_st_reason);
        btn_button = (Button) findViewById(R.id.btn_roll);
    }




}
