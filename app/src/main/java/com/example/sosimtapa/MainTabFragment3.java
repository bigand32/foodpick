package com.example.sosimtapa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainTabFragment3 extends Fragment {
    public ArrayList<HashMap<String,String>> Data = new ArrayList<HashMap<String, String>>();
    private HashMap<String,String> InputData1 = new HashMap<>();
    private HashMap<String,String> InputData2 = new HashMap<>();
    private ListView listView;
    private TextView tv_result;
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view1=inflater.inflate(R.layout.tab_fragment3,container,false);
        tv_result = (TextView) view1. findViewById(R.id.tv_st_reason);
        textView = (TextView) view1. findViewById(R.id.font1);




        Button button=(Button) view1. findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String food="";
                Random dice = new Random();
                int result = dice.nextInt(6) + 1;
                if(result==1){
                    food="김치찌개";
                }else if(result==2){
                    food="부대찌개";
                }
                else if(result==3){
                    food="순댓국";
                }
                else if(result==4){
                    food="제육볶음";
                }
                else if(result==5){
                    food="갈치조림";
                }
                else if(result==6){
                    food="비빔밥";
                }

                // TextView에 표시한다.
                tv_result.setText(String.valueOf(food));
            }
        });
        Button button1=(Button) view1. findViewById(R.id.start2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String food="";
                Random dice = new Random();
                int result = dice.nextInt(6) + 1;
                if(result==1){
                    food="짜장면";
                }else if(result==2){
                    food="짬뽕";
                }
                else if(result==3){
                    food="짜장밥";
                }
                else if(result==4){
                    food="우동";
                }
                else if(result==5){
                    food="깐쇼새우";
                }
                else if(result==6){
                    food="탕수육";
                }

                // TextView에 표시한다.
                tv_result.setText(String.valueOf(food));
            }
        });
        Button button2=(Button) view1. findViewById(R.id.start3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String food="";
                Random dice = new Random();
                int result = dice.nextInt(6) + 1;
                if(result==1){
                    food="연어덮밥";
                }else if(result==2){
                    food="차슈덮밥";
                }
                else if(result==3){
                    food="돈까스";
                }
                else if(result==4){
                    food="라멘";
                }
                else if(result==5){
                    food="메밀국수";
                }
                else if(result==6){
                    food="카레";
                }

                // TextView에 표시한다.
                tv_result.setText(String.valueOf(food));
            }
        });

        return view1;
    }

}
