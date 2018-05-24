package org.appspot.apprtc.travelogue;

import android.app.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.appspot.apprtc.R;
import org.appspot.apprtc.account.UserInfo;
import org.appspot.apprtc.share.ShareFragment;
import org.appspot.apprtc.share.ShareMain;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by ngnicola on 5/5/2017.
 */

public class EditTravel extends Activity {
    private Button save, select;
    private EditText title;
    private ImageView image;

    ShareMain shareMain = new ShareMain ();

    // private String tid = "JKA41";
    private static String tid;

    public String title_text;
    public static String image_id;
    public static Bitmap image_bitmap;

    private int PICK_IMAGE_REQUEST = 1;
    private int imageMaxw =  720;
    private int imageMaxh = 1280;

    public static String userID;
    UserInfo userInfo = new UserInfo();
    String travelTitle;

    private Context context;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_travel);
        userID = userInfo.getUserID();
        tid = shareMain.getSelectTid();
        title_text = shareMain.getSelectTitle();
        image_bitmap = shareMain.getSelectImage();

        save = (Button)findViewById(R.id.saveTravelogue);
        select = (Button)findViewById(R.id.saveTravelImage);
        select.setOnClickListener(listener);
        save.setOnClickListener(listener);

        title = (EditText)findViewById(R.id.travelTitle);
        image = (ImageView)findViewById(R.id.imageView);

        if(title_text!=null)
            title.setText(title_text);
        if(image_bitmap!=null)
            image.setImageBitmap(image_bitmap);


        context = this;
        activity = this;
    }

    private Button.OnClickListener listener = new Button.OnClickListener() {

        public void onClick(View v) {
            //ConnectActivity connectActivity = new ConnectActivity();
            Intent intent = new Intent();
            switch (v.getId()) {

                case R.id.saveTravelImage:

                    //new getTravelActivity(context).execute(userID, tid);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


                    break;

                case R.id.saveTravelogue:


                    travelTitle = title.getText().toString();

                    if (image_bitmap != null) {
                        int w = image_bitmap.getWidth();
                        int h = image_bitmap.getHeight();

                        if (w > imageMaxw || h > imageMaxh) {
                            float scaleWidth = ((float) imageMaxw) / w;
                            float scaleHeight = ((float) imageMaxh) / h;

                            Matrix matrix = new Matrix();
                            matrix.postScale(scaleWidth, scaleHeight);
                            // 得到新的圖片
                            Bitmap newbm = Bitmap.createBitmap(image_bitmap, 0, 0, w, h, matrix, true);
                            image_bitmap = newbm;
                        }
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


                        //Toast.makeText(getApplicationContext(),"TID: " + tid + "Title: " + travelTitle, Toast.LENGTH_LONG).show();
                        new EditTravelActivity(context, activity).execute(userID,tid, travelTitle,encodedImage );


                        break;


                    }
            }
        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                image_bitmap = bitmap;

                image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}
