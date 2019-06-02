package com.example.sosimtapa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Belal on 2/23/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Upload> uploads;

    TextView textViewName1,textViewName2,textViewName3;

    ListView listView;

    private List<Bbs> datas = new ArrayList<>();
    private String s,v1,b;
    Upload upload;
    RatingBar rb;
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



        upload = uploads.get(position);

        holder.textViewName.setText(upload.getName());



        Glide.with(context).load(upload.getUrl()).apply(new RequestOptions().override(150)).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageView;
        String content;
        String star;
        float st;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title=textViewName.getText().toString();
                    Drawable mDrawable = imageView.getDrawable();
                    Bitmap mBitmap=((BitmapDrawable)mDrawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    byte[] bytes = stream.toByteArray();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference bbsRef=database.getReference();
                    Query query = bbsRef.child("img").orderByChild("name").equalTo(title);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                            content=snapshot.child("content").getValue(String.class);
                             star=snapshot.child("star").getValue(String.class);
                             st=Float.parseFloat(star);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //view가 alert 이면 팝업실행 즉 버튼을 누르면 팝업창이 뜨는 조건
                    Context mContext = view.getContext();
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                    //R.layout.dialog는 xml 파일명이고 R.id.popup은 보여줄 레이아웃 아이디
                    View layout = inflater.inflate(R.layout.popup_activity, (ViewGroup) view.findViewById(R.id.popup));
                    AlertDialog.Builder aDialog = new AlertDialog.Builder(view.getContext());
                    //aDialog.setTitle("리뷰보기"); //타이틀바 제목

                   rb = (RatingBar) layout.findViewById(R.id.ratingBar1);
                    textViewName1=(TextView)layout.findViewById(R.id.text1);
                    textViewName2=(TextView)layout.findViewById(R.id.text2);
                    ImageView img1=(ImageView)layout.findViewById(R.id.imageView1);
                    textViewName1.setText(title);
                    img1.setImageBitmap(mBitmap);
                    // if(s.equals(v)){
                    s=upload.getContent();

                    textViewName2.setText(content);
                   rb.setRating(st);
                   rb.setIsIndicator(true);
                    // }
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

      }
    }

