package org.appspot.apprtc.travelogue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.appspot.apprtc.ReturnTravel;
import org.appspot.apprtc.account.ChangeIP;
import org.appspot.apprtc.share.ShareFragment;
import org.appspot.apprtc.share.ShareMain;
import org.appspot.apprtc.share.ShowTravelActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by ownder on 03/05/17.
 */

public class AddTravelActivity extends AsyncTask<String,Void,String[]> implements ReturnTravel {
    private Context context;

    private Activity activity;

    private ArrayList<String> aTitle = new ArrayList<>();
    private ArrayList<String> aImage = new ArrayList<>();
    private ArrayList<String> aId = new ArrayList<>();

    private ReturnTravel returnTravel;
    private static String IP;
    ChangeIP cIP = new ChangeIP();


    //flag 0 means get and 1 means post.(By default it is get.)
    public AddTravelActivity(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        returnTravel=this;

    }

    //從前面get string variable
    @Override
    protected String[] doInBackground(String ... string) {
        ArrayList strings = new ArrayList<String>();

        try{
            //this is post function
            IP=cIP.getIP();
            String userID = string[0].toString();
            String travelTitle = string[1].toString();
            String imageBitMap = string[2].toString();


            String link="http://"+IP+"/fyp2017/addTravel.php";
            //set the post data
            String data  = URLEncoder.encode("userID", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");

            data += "&" + URLEncoder.encode("travelTitle", "UTF-8") + "=" +
                    URLEncoder.encode(travelTitle, "UTF-8");

            data += "&" + URLEncoder.encode("image", "UTF-8") + "=" +
                    URLEncoder.encode(imageBitMap, "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            //send the data to php
            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            // get data from php
            //while there has one or more line, add data into string arraylist
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            String [] list  = sb.toString().split(",");


            //todo testing

            reader.close();

            return list;


        } catch(Exception e){
            //return new String("Exception: " + e.getMessage());
            strings.add("Exception: " + e.getMessage());
            return null;
        }
    }

    protected void onPreExecute(){
    }

    //this functon is for show data, data 要在這才離開background，這才可以output。
    @Override
    protected void onPostExecute(String [] result){

        if(result==null){
            Toast.makeText(context,"has problem",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"OK",Toast.LENGTH_LONG).show();
            Log.v("This is action","In here");
            ShowTravelActivity showTravelActivity = new ShowTravelActivity();
            showTravelActivity.returnTravel = returnTravel;
            showTravelActivity.execute();
            Toast.makeText(context,"Loading... ", Toast.LENGTH_LONG).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ShareFragment shareFragment = new ShareFragment();
                    shareFragment.setIMAGE(aTitle,aImage,aId);

                    //for(int i=0;i<shareFragment.IMAGE_IDS.size();i++){
                        if(shareFragment.IMAGE_IDS.get(0).first.first.second!=null){
                            Log.v("IMAGE_IDS ",shareFragment.IMAGE_IDS.get(0).first.first.second);

                            Intent intent = new Intent();
                            intent.setClass(activity, ShareMain.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            activity.finish();
                            activity.startActivity(intent);
                        }else{
                            Log.v("IMAGE_IDS ","is null");
                        }
                    //}
                }
            }, 10000);
        }

    }


    @Override
    public void processFinish(String[] output) {
        aId.clear();
        aTitle.clear();
        aImage.clear();
        for(int i=0;i<output.length;i+=3){
            aId.add(output[i]);
            aTitle.add(output[i+1]);
            aImage.add(output[i+2]);
        }
    }

}