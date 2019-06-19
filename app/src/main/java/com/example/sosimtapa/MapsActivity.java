package com.example.sosimtapa;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat;
    double lon;
    String name;
    String address;
    int i;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    private ImageView imageView;
    private RecyclerView recyclerView;
    ImageButton a,b;


    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<Upload> uploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_id);
        mToolbar.setTitle("피드");
        a=(ImageButton)findViewById(R.id.menu);
        b=(ImageButton)findViewById(R.id.list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        a.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setLayoutManager(new GridLayoutManager(MapsActivity.this,3,GridLayoutManager.VERTICAL,false));
            }
        });
        b.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setLayoutManager(new LinearLayoutManager(MapsActivity.this));
            }
        });




        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();
        Intent intent = getIntent();
        imageView = (ImageView) findViewById(R.id.imageView);

        lat = intent.getDoubleExtra("latitude", 0);
        lon = intent.getDoubleExtra("longitude", 0);
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        String folderName = "images";
        String imageName = address;
        String storagePath = folderName + "/" + imageName+i;

        //mRecyclerView.setHasFixedSize(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        Query query = mRef.child("img").orderByChild("address").equalTo(address);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                //creating adapter
                adapter = new MyAdapter(getApplicationContext(), uploads);

                //adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });




//        FirebaseStorage storage = FirebaseStorage.getInstance("gs://pickfood-b8be9.appspot.com");
//
////생성된 FirebaseStorage를 참조하는 storage 생성
//        StorageReference storageRef = storage.getReference();
//        StorageReference imageRef = storageRef.child(storagePath);
//
//        try {
//            // Storage 에서 다운받아 저장시킬 임시파일
//            final File imageFile = File.createTempFile("images", "jpg");
//            imageRef.getFile(imageFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    // Success Case
//                    Bitmap bitmapImage = BitmapFactory.decodeFile(imageFile.getPath());
//                    int a[];
//                    imageView.setImageBitmap(bitmapImage);
//                    Toast.makeText(getApplicationContext(), "Success !!", Toast.LENGTH_LONG).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    // Fail Case
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Fail !!", Toast.LENGTH_LONG).show();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        GridView gridView = (GridView) findViewById(R.id.gridview);
//        gridView.setAdapter((ListAdapter) new ImageAdapter(this));

        // 과거에는 GoogleMap 객체를 얻어오기 위해
        // mapFragment.getMap() 메소드를 사용했으나
        // 현재 getMap() 은 deprecate 되었습니다.
        // 지금은 위와 같이 getMapAsync() 를 하고
        // 아래의 OnMapReady() 에서 처리를 GoogleMap 객체관련
        // 처리를 합니다.

    }
//    @Override
//    protected  void onStart(){
//        super.onStart();
//
//        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter =
//                new FirebaseRecyclerAdapter<Model, ViewHolder>(
//                        Model.class,
//                        R.layout.row,
//                        ViewHolder.class,
//                        mRef
//                ) {
//                    @Override
//                    protected void populateViewHolder(ViewHolder viewHolder, Model model, int position) {
//                        viewHolder.setDetails(getApplicationContext(),model.getTitle(),model.getImage());
//
//                    }
//                };
//        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
//    }
//    public class ImageAdapter extends BaseAdapter {
//        private Context mContext;
//    Integer[] mThumblds= {R.drawable.b,R.drawable.c,R.drawable.h,R.drawable.f,R.drawable.d,R.drawable.e,R.drawable.b,R.drawable.c,R.drawable.h,R.drawable.e};
//        public ImageAdapter(Context c) {
//            mContext = c;
//        }
//        public int getCount() {
//            return mThumblds.length;
//        }
//        public Object getItem(int position) {
//            return null;
//        }
//        public long getItemId(int position) {
//            return 0;
//        }
//        public View getView(int position, View convertView,
//                            ViewGroup parent) {
//            ImageView imageView;
//            if (convertView == null) {
//                imageView = new ImageView(mContext);
//                imageView.setLayoutParams(new GridView.LayoutParams(250,250));
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            }else {
//                imageView = (ImageView) convertView;
//            }
//            imageView.setImageResource(mThumblds[position]);
//            return imageView;
//        }
//
//
//    }

    /**
     * OnMapReady 는 map이 사용가능하면 호출되는 콜백 메소드입니다
     * 여기서 marker 나 line, listener, camera 이동 등을 설정해두면 됩니다.
     * 이번 예제에서는 서울역 근처에 marker를 더하고 적절한 title과, zoom을 설정해둡니다
     * 이 시점에서, 만약 사용자 기기게 Google Play service가 설치되지 않으면
     * 설치하라고 메세지가 뜨게 되고,  설치후에 다시 이 앱으로 제어권이 넘어옵니다
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // ↑매개변수로 GoogleMap 객체가 넘어옵니다.

        // camera 좌쵸를 서울역 근처로 옮겨 봅니다.
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(lat, lon)   // 위도, 경도
        ));

        // 구글지도(지구) 에서의 zoom 레벨은 1~23 까지 가능합니다.
        // 여러가지 zoom 레벨은 직접 테스트해보세요
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        googleMap.animateCamera(zoom);   // moveCamera 는 바로 변경하지만,
        // animateCamera() 는 근거리에선 부드럽게 변경합니다

        // marker 표시
        // market 의 위치, 타이틀, 짧은설명 추가 가능.
        MarkerOptions marker = new MarkerOptions();
        marker .position(new LatLng(lat, lon))
                .title(name);

        googleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        // 마커클릭 이벤트 처리
        // GoogleMap 에 마커클릭 이벤트 설정 가능.
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 마커 클릭시 호출되는 콜백 메서드
                Toast.makeText(getApplicationContext(),
                        marker.getTitle() + " 클릭했음"
                        , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

}