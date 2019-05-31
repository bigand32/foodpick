package com.example.sosimtapa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Belal on 2/23/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Upload> uploads;
    String s;

    public MyAdapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bbsRef=database.getReference("write");

        bbsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 데이터를 읽어올 때 모든 데이터를 읽어오기때문에 List 를 초기화해주는 작업이 필요하다.

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key  = snapshot.getKey();
                    Bbs bbs = snapshot.getValue(Bbs.class); // 컨버팅되서 Bbs로........
                    bbs.key = key;

                }
                // notifyDataSetChanged를 안해주면 ListView 갱신이 안됨

                // ListView 의 위치를 마지막으로 보내주기 위함

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        Upload upload = uploads.get(position);

        holder.textViewName.setText(upload.getName());
        s=upload.getCotent();
        Glide.with(context).load(upload.getUrl()).apply(new RequestOptions().override(150)).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //view가 alert 이면 팝업실행 즉 버튼을 누르면 팝업창이 뜨는 조건
            Context mContext = view.getContext();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            //R.layout.dialog는 xml 파일명이고 R.id.popup은 보여줄 레이아웃 아이디
            View layout = inflater.inflate(R.layout.popup_activity, (ViewGroup) view.findViewById(R.id.popup));
            AlertDialog.Builder aDialog = new AlertDialog.Builder(view.getContext());
            aDialog.setTitle("히든스탯 목록"); //타이틀바 제목
            TextView textViewName1 = (TextView) view.findViewById(R.id.text1);
            TextView textViewName2 = (TextView) view.findViewById(R.id.text2);
            //if(s==)
            aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅 //그냥 닫기버튼을 위한 부분
            aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            }); //팝업창 생성
            AlertDialog ad = aDialog.create();
            ad.show();//보여줌!


        }



        });

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
