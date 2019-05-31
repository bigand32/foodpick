package com.example.sosimtapa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class write extends Activity {
    ImageView imageView;
    Button button;
    Button button2;
    EditText editText;
    String su;
    String img;
    int i;
    EditText wr;
    RatingBar rb;
    FirebaseDatabase database;
    DatabaseReference bbsRef;
    DatabaseReference imge;
    private Uri filePath;
    Uri downloadUri;
    String url;
    String p;
    StorageReference storageRef;
    String name1;
    String address1;
    String menu,content;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding);
        database = FirebaseDatabase.getInstance();
        Intent intent1 = getIntent();
        name1 = intent1.getStringExtra("name1");
        address1 = intent1.getStringExtra("address1");
        // 2. CRUD 작업의 기준이 되는 노드를 레퍼러느로 가져온다.
        bbsRef = database.getReference("write");
        //final TextView tv = (TextView) findViewById(R.id.textView1); //별점 몇점인지 나타낼때사용
        rb = (RatingBar) findViewById(R.id.ratingBar1);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // tv.setText("rating : " + rating);
            }
        });

        Button button1 = (Button) findViewById(R.id.location);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AutoCompleteActivity.class);
                startActivity(intent);
            }
        });
        button2 = (Button) findViewById(R.id.ok);
      /*  button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),pid.class);
                startActivity(intent);
            }
        });*/

        imageView = (ImageView) findViewById(R.id.image);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        editText = (EditText) findViewById(R.id.menu);
        wr = (EditText) findViewById(R.id.write);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu = editText.getText().toString();
                content = wr.getText().toString();
                String key = bbsRef.push().getKey();
                float rating = rb.getRating();
                String star = String.valueOf(rating);


                uploadFile();
                // 6.2 입력될 키, 값 세트 (레코드)를 생성.
                Map<String, String> postValues = new HashMap<>();
                postValues.put("title", menu);
                postValues.put("content", content);
                postValues.put("star", star);
                postValues.put("adress", address1);
                postValues.put("name", name1);

                DatabaseReference keyRef = bbsRef.child(content);
                keyRef.setValue(postValues);
                wr.setText("");
                editText.setText("");


                // startActivity(new Intent(write.this, MainTabFragment2.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    imageView.setImageBitmap(img);
                    filePath = data.getData();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            Intent intent = getIntent();
            String name = intent.getStringExtra("name1");
            String address = intent.getStringExtra("address1");
            //storage 주소와 폴더 파일명을 지정해 준다.
            storageRef = storage.getReferenceFromUrl("gs://pickfood-b8be9.appspot.com").child("images/" + address + formatter.format(now));
            img = String.valueOf(storageRef.getDownloadUrl());
            //올라가거라...
            UploadTask uploadTask = storageRef.putFile(filePath);
            uploadTask
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기

                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();


                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult();
                        url = downloadUri.toString();

                        imge = database.getReference("img");
                        String key = imge.push().getKey();
                        Map<String, String> postValues = new HashMap<>();
                        postValues.put("name",menu);
                        postValues.put("uri", url);
                        postValues.put("content", content);
                        DatabaseReference keyRef = imge.child(content);
                        keyRef.setValue(postValues);
                        Upload upload = new Upload(editText.getText().toString(), url);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }
}