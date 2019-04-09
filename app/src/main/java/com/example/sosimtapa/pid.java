package com.example.sosimtapa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class pid extends Activity {
    TextView selectedText;
    Spinner spinner;
    Spinner spinner1;
    String[] item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        Button button=(Button) findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),write.class);
                startActivity(intent);
            }
        });
        spinner = (Spinner) findViewById(R.id.spinner1);
        // spinner.setOnItemSelectedListener(this);
        item = new String[]{"강서구", "관악구", "동작구", "영등포구", "성북구"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner1 = (Spinner) findViewById(R.id.spinner2);
        // spinner.setOnItemSelectedListener(this);
        item = new String[]{"여의도", "신림", "서울대입구", "영등포구", "성북구"};


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter((ListAdapter) new ImageAdapter(this));


    }
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private Integer[] mThumblds= {R.drawable.b,R.drawable.c,R.drawable.h,R.drawable.f,R.drawable.d,R.drawable.e,R.drawable.b,R.drawable.c,R.drawable.h,R.drawable.e};
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
                imageView.setLayoutParams(new GridView.LayoutParams(250,250));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            }else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumblds[position]);
            return imageView;
        }


    }
}
   /* @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedText.setText(item[i]);
        if (selectedText.getText().toString().equals("관악구")) {
            selectedText.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedText.setText("");
    }*/
