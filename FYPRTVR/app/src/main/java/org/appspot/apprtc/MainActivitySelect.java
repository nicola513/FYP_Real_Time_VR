package org.appspot.apprtc;

import android.app.Activity;


import android.content.Intent;

import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.appspot.apprtc.account.MainActivity;
import org.appspot.apprtc.account.UserInfo;
import org.appspot.apprtc.recorder.Recorder;
import org.appspot.apprtc.recorder.ViewVideo;
import org.appspot.apprtc.share.ShareFragment;
import org.appspot.apprtc.share.ShareMain;
import org.appspot.apprtc.share.ShowTravelActivity;
import org.appspot.apprtc.travelogue.AddTravel;
import org.appspot.apprtc.travelogue.AddTravelDetails;

import java.util.ArrayList;


public class MainActivitySelect extends Activity implements ReturnTravel{


    private Button stream, record_self, watch_self, youtube, travel;

    public static String userID;
    public static String userName;
    UserInfo userInfo = new UserInfo();
    private TextView user;
    private String userWelocome;

    private ArrayList<String> travelTitle = new ArrayList<>();
    private ArrayList<String> travelImage = new ArrayList<>();
    private ArrayList<String> travelId = new ArrayList<>();
    private ReturnTravel returnTravel;
    private Intent mintent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        stream = (Button) findViewById(R.id.stream);
        record_self = (Button) findViewById(R.id.record_self);
        watch_self = (Button) findViewById(R.id.watch_self);
        //youtube = (Button) findViewById(R.id.youtube);
        travel = (Button) findViewById(R.id.travel);

        user = (TextView) findViewById(R.id.user);

        stream.setOnClickListener(listener);
        record_self.setOnClickListener(listener);
        watch_self.setOnClickListener(listener);
        //youtube.setOnClickListener(listener);
        travel.setOnClickListener(listener);


        userID = userInfo.getUserID();
        userName = userInfo.getUserName();

        if(userID==null &&userName==null ){
            user.setText("No Connection");
        }else{
            userWelocome = "Hello, "+userName+" ("+userID+")";
            user.setText(userWelocome);
        }



        returnTravel=this;


    }



    private Button.OnClickListener listener = new Button.OnClickListener() {

        public void onClick(View v) {
            //ConnectActivity connectActivity = new ConnectActivity();
            Intent intent = new Intent();
            mintent = intent;
            switch (v.getId()) {

                case R.id.stream:
                    //Intent intent = new Intent();
                    intent.setClass(MainActivitySelect.this, ConnectActivity.class);
                    startActivity(intent);
                    break;

                case R.id.record_self:
                    //Intent intent = new Intent();
                    intent.setClass(MainActivitySelect.this, Recorder.class);
                    startActivity(intent);
                    break;

                case R.id.watch_self:
                    //intent.setClass(MainActivitySelect.this, AddTravelDetails.class);
                    intent.setClass(MainActivitySelect.this, ViewVideo.class);
                    startActivity(intent);
                    break;


                case R.id.travel:
                    //Intent intent = new Intent();

                    ShowTravelActivity showTravelActivity = new ShowTravelActivity();
                    showTravelActivity.returnTravel = returnTravel;
                    showTravelActivity.execute();

                    //Log.v("Tag", travelList.size()+"");

                    // This solution will leak memory!  Don't use!!!
                    Toast.makeText(MainActivitySelect.this,"Loading... ", Toast.LENGTH_LONG).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("Tag", travelId.size()+"");
                            ShareFragment shareFragment = new ShareFragment();
                            //ArrayList<String> strings = new ArrayList<>();
                            //strings.add("1");
                            //strings.add("2");
                            //Log.v("Tag", travelImage.size()+"");
                            //String text = "";
                            //Toast.makeText(context,"String: "+travelImage.get(0), Toast.LENGTH_LONG).show();
                            shareFragment.setIMAGE(travelTitle,travelImage,travelId);
                            mintent.setClass(MainActivitySelect.this, ShareMain.class);
                            startActivity(mintent);

                        }
                    }, 10000);
                    break;

            }
        }
    };

    @Override
    public void processFinish(String[] output) {
        travelId.clear();
        travelTitle.clear();
        travelImage.clear();
        for(int i=0;i<output.length;i+=3){
            travelId.add(output[i]);
            travelTitle.add(output[i+1]);
            travelImage.add(output[i+2]);
        }
        //strings=output;
    }


}
