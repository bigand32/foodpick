package com.example.sosimtapa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainTabFragment1 extends Fragment {
    CalendarView calendar;
    private Button button;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view2=inflater.inflate(R.layout.tab_fragment1,container,false);
        calendar=(CalendarView)view2.findViewById(R.id.calendar1);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               //Toast.makeText(getBaseContext(),"Selected date"+dayOfMonth+"/"+month+"/"+year
              //  ,Toast.LENGTH_LONG).show();
            }
        });


        return view2;
    }
}

