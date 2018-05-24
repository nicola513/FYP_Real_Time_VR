package org.appspot.apprtc.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.appspot.apprtc.R;
import org.appspot.apprtc.travelogue.TravelDetailsList;


public class ShareImageFragment extends Fragment {

    private Bitmap mImageIdB;

    private String mImageTitle;

    private Context context;

    private Activity activity;

    private String tid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.image_slide_view, container, false);

        //if (mImageIdB != null) {
            ImageView image = (ImageView) rootView.findViewById(R.id.share_image);
            image.setImageBitmap(mImageIdB);

            image.setOnClickListener(imageListener);

            TextView travelTitle = (TextView) rootView.findViewById(R.id.travelTitle);
            travelTitle.setText(mImageTitle);
        //}

        return rootView;
    }


    public void setImage(Bitmap imageIdB) {
        mImageIdB = imageIdB;
    }

    public void setImageTitle(String imageTitle) {
        mImageTitle = imageTitle;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void setTid(String tid){
        this.tid = tid;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    private ImageView.OnClickListener imageListener = new ImageView.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {

                case R.id.share_image:
                    //Toast.makeText(context, "Tid: "+tid, Toast.LENGTH_LONG).show();
                    intent.setClass(context, TravelDetailsList.class);
                    startActivity(intent);
                    break;

            }
        }
    };


}
