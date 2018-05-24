package org.appspot.apprtc.travelogue;

import android.app.Activity;


import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.appspot.apprtc.R;
import org.appspot.apprtc.account.UserInfo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class AddTravel extends Activity {

    private Button saveTravelImage, saveTravelogue;
    private int PICK_IMAGE_REQUEST = 1;
    public static String imageUri;
    private String travelougeTitle;
    private TextView tuserID;
    private EditText travelTitle;
    private Context contextTravel;
    public static String userID;
    UserInfo userInfo = new UserInfo();

    private  Bitmap imageBitMap;
    private ImageView image;
    int imageMaxw, imageMaxh;
    private ArrayList<String> aTitle = new ArrayList<>();
    private ArrayList<String> aImage = new ArrayList<>();
    private ArrayList<String> aId = new ArrayList<>();
    private Activity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_travel);
        imageMaxw = 720;
        imageMaxh = 720;

        saveTravelImage = (Button) findViewById(R.id.saveTravelImage);
        saveTravelogue = (Button) findViewById(R.id.saveTravelogue);

        saveTravelImage.setOnClickListener(listener);
        saveTravelogue.setOnClickListener(listener);

        travelTitle = (EditText)findViewById(R.id.travelTitle);
        userID = userInfo.getUserID();


        image = (ImageView)findViewById(R.id.imageView);

        contextTravel = this;
        activity = this;

    }

    private Button.OnClickListener listener = new Button.OnClickListener() {

        public void onClick(View v) {
            //ConnectActivity connectActivity = new ConnectActivity();
            Intent intent = new Intent();
            switch (v.getId()) {

                case R.id.saveTravelImage:

                    // Show only images, no videos or anything else
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    // Always show the chooser (if there are multiple options available)
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                    break;

                case R.id.saveTravelogue:

                    travelougeTitle = travelTitle.getText().toString();


                    if(imageBitMap!=null) {
                        int w = imageBitMap.getWidth();
                        int h = imageBitMap.getHeight();

                        if(w>imageMaxw||h>imageMaxh) {
                            float scaleWidth = ((float) imageMaxw) / w;
                            float scaleHeight = ((float) imageMaxh) / h;

                            Matrix matrix = new Matrix();
                            matrix.postScale(scaleWidth, scaleHeight);
                            // 得到新的圖片
                            Bitmap newbm = Bitmap.createBitmap(imageBitMap, 0, 0, w, h, matrix,true);
                            imageBitMap = newbm;

                        }
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        imageBitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                        new AddTravelActivity(contextTravel,activity).execute(userID,travelougeTitle, encodedImage);

                       // new TryDefaultTravel(contextTravel,activity).execute(userID,travelougeTitle, encodedImage);

                    }else
                        Toast.makeText(getApplicationContext(), "no image", Toast.LENGTH_LONG).show();


                    break;


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

                imageBitMap = bitmap;
                image.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
