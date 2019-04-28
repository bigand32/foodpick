package com.example.sosimtapa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
    TextView selectedText;
    Spinner spinner;
    Spinner spinner1;
    String[] item;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view2= inflater.inflate(R.layout.tab_fragment2,container,false);
        Button button=(Button) view2.findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),write.class);
                startActivity(intent);
            }
        });
        spinner = (Spinner) view2.findViewById(R.id.spinner1);
        // spinner.setOnItemSelectedListener(this);
        item = new String[]{"강남구", "관악구", "동작구", "영등포구", "성북구"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner1 = (Spinner) view2.findViewById(R.id.spinner2);
        // spinner.setOnItemSelectedListener(this);
        item = new String[]{"역삼동", "신림", "서울대입구", "영등포구", "성북구"};


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);

        GridView gridView = (GridView) view2.findViewById(R.id.gridview);
        gridView.setAdapter((ListAdapter) new ImageAdapter(getActivity()));


        return view2;
    }
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private Integer[] mThumblds = {R.drawable.b, R.drawable.c, R.drawable.h, R.drawable.f, R.drawable.d, R.drawable.e, R.drawable.b, R.drawable.c, R.drawable.h, R.drawable.e};

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumblds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumblds[position]);
            return imageView;
        }


    }
}