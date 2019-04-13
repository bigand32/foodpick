package com.example.sosimtapa;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainTabFragment2 extends Fragment {
    PieChart pieChart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view2= inflater.inflate(R.layout.tab_fragment2,container,false);
        PieChart pieChart = view2.findViewById(R.id.piechart);
        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new Entry(5f, 0));
        NoOfEmp.add(new Entry(10f, 1));
        NoOfEmp.add(new Entry(2f, 2));
        NoOfEmp.add(new Entry(7f, 3));
        NoOfEmp.add(new Entry(6f, 4));
        NoOfEmp.add(new Entry(8f, 5));
        NoOfEmp.add(new Entry(20f, 6));
        NoOfEmp.add(new Entry(1, 7));
        NoOfEmp.add(new Entry(2, 8));
        NoOfEmp.add(new Entry(3, 9));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");

        ArrayList year = new ArrayList();

        year.add("떡볶이");
        year.add("포테이토피자");
        year.add("뿌링클");
        year.add("튀김우동");
        year.add("파스타");
        year.add("순댓국");
        year.add("삼겹살");
        year.add("쌀국수");
        year.add("햄버거");
        year.add("치킨마요덮밥");
        PieData data = new PieData(year, dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(5000, 5000);
        return view2;
    }
}