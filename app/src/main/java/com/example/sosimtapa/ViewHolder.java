//package com.example.sosimtapa;
//
//import com.squareup.picasso.Picasso;
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class ViewHolder extends RecyclerView.ViewHolder {
//
//    View mView;
//
//    public ViewHolder(View itemView){
//        super(itemView);
//
//        mView=itemView;
//    }
//    public void setDetails(Context ctx,String title, String image){
//        TextView mTitleTv=mView.findViewById(R.id.rTitleTv);
//        ImageView mImageIv = mView.findViewById(R.id.rImageView);
//        mTitleTv.setText(title);
//        Picasso.get().load(image).resize(50, 50)
//                .centerCrop().into(mImageIv);
//
//    }
//}
