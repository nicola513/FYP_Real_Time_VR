package org.appspot.apprtc.travelogue;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.appspot.apprtc.ConnectActivity;
import org.appspot.apprtc.R;
import org.appspot.apprtc.share.ShareMain;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddTravelDetails extends Activity {

    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_ADDRESS_REQUEST = 2;
    TextView position;
    EditText date, address, title, content;
    DatePickerDialog datePickerDialog;
    ImageButton searchAddress, addImage;
    Button back, save;
    String tDate, tTime, tAddress, tTitle, tContent ;
    public static String tImageUri;
    private Context contextTravelDetails;
    private Activity activity;
    private  Bitmap imageBitMap;
    private ImageView image;
    int imageMaxw, imageMaxh;
    ShareMain shareMain = new ShareMain ();

   // private String tid = "JKA11";
    private static String tid;
    String mapSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_travel_details);

        imageMaxw = 720;
        imageMaxh = 1280;
        tid = shareMain.getSelectTid();
        contextTravelDetails = this;
        activity = this;
        date = (EditText) findViewById(R.id.date);
        position = (TextView) findViewById(R.id.position);
        address = (EditText) findViewById(R.id.address_text);
        title = (EditText) findViewById(R.id.title_text);
        content = (EditText) findViewById(R.id.content_text);
        searchAddress = (ImageButton)findViewById(R.id.search_address);
        addImage = (ImageButton)findViewById(R.id.photoViewBtn);

        image = (ImageView)findViewById(R.id.photoView);

        back = (Button)findViewById(R.id.back);
        save = (Button)findViewById(R.id.saveDetail);
        back.setOnClickListener(listener);
        save.setOnClickListener(listener);
        addImage.setOnClickListener(imageListener);



        searchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent();
                intent.setClass(AddTravelDetails.this, MapsActivity.class);
                startActivity(intent);*/

                Intent i = new Intent(AddTravelDetails.this, MapsActivity.class);
                startActivityForResult(i, PICK_ADDRESS_REQUEST);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                int mWeekday  = c.get(Calendar.DAY_OF_WEEK);

                datePickerDialog = new DatePickerDialog(AddTravelDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                SimpleDateFormat df = new SimpleDateFormat("EE");

                                Date dateSelected = new Date(year, monthOfYear, dayOfMonth-1);
                                String weekday = df.format(dateSelected);

                                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                                Date dateChoose = new Date(year-1900, monthOfYear, dayOfMonth);
                                tDate= df2.format(dateChoose);

                                SimpleDateFormat df3 = new SimpleDateFormat("HH:mm:ss");
                                String timestamp = df3.format(c.getTime());
                                tTime = timestamp;
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year +" ( "+ weekday +" )  - "+tTime);
                                //time.setText(timestamp);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

    }


    private ImageView.OnClickListener imageListener = new ImageView.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {

                case R.id.photoViewBtn:
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    break;

            }
        }
    };




    private Button.OnClickListener listener = new Button.OnClickListener() {

        public void onClick(View v) {

            Intent intent = new Intent();
            switch (v.getId()) {

                case R.id.back:

                    finish();
                    break;

                case R.id.saveDetail:

                    //tDate = date.getText().toString();
                    //tTime = time.getText().toString();
                    tAddress = address.getText().toString();
                    tTitle = title.getText().toString();
                    tContent = content.getText().toString();

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

                        new AddTravelDetailsActivity(contextTravelDetails, activity).execute(tDate, tTime, tAddress, tTitle, tContent, encodedImage, tid);




                    }else
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();


                    //finish();
                    break;


            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch(requestCode){
                case 1:

                    if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                        Uri uri = data.getData();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                            imageBitMap = bitmap;
                            image.setImageBitmap(bitmap);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 2:
                    if (resultCode == Activity.RESULT_OK) {

                        mapSearchText = data.getStringExtra("addressText");
                        address.setText(mapSearchText);

                        String positionText = data.getStringExtra("positionText");
                        position.setText(positionText);

                        break;
                    }
            }

    }


}
