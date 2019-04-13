package com.example.sosimtapa;

import android.app.Activity;
import android.content.Intent;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view1=inflater.inflate(R.layout.tab_fragment3,container,false);
        tv_result = (TextView) view1. findViewById(R.id.tv_st_reason);
        Button button1=(Button)  view1.findViewById(R.id.imageView);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity(),pid.class);
                startActivity(intent);
            }
        });
        Button button=(Button) view1. findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String food="";
                Random dice = new Random();
                int result = dice.nextInt(6) + 1;
                if(result==1){
                    food="떡볶이";
                }else if(result==2){
                    food="삼겹살";
                }
                else if(result==3){
                    food="순댓국";
                }
                else if(result==4){
                    food="김치찌개";
                }
                else if(result==5){
                    food="제육볶음";
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
