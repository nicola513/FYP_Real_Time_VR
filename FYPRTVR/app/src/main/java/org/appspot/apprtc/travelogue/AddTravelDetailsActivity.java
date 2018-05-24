package org.appspot.apprtc.travelogue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.CheckedTextView;
import android.widget.Toast;

import org.appspot.apprtc.account.ChangeIP;

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

public class AddTravelDetailsActivity extends AsyncTask<String,Void,String[]> {
    private Context context;
    private Activity activity;
    private CheckedTextView content;
    private static String IP;
    ChangeIP cIP = new ChangeIP();

    //flag 0 means get and 1 means post.(By default it is get.)`
    public AddTravelDetailsActivity(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    //從前面get string variable
    @Override
    protected String[] doInBackground(String ... string) {
        ArrayList strings = new ArrayList<String>();

        try{
            //this is post function
            IP=cIP.getIP();
            String tDate = string[0].toString();
            String tTime = string[1].toString();
            String tAddress = string[2].toString();
            String tTitle = string[3].toString();
            String tContent = string[4].toString();
            String imageBitMap = string[5].toString();
            String tid = string[6].toString();



            String link="http://"+IP+"/fyp2017/addTravelDetails.php";
            //set the post data
            String data  = URLEncoder.encode("date", "UTF-8") + "=" +
                    URLEncoder.encode(tDate, "UTF-8");

            data += "&" + URLEncoder.encode("time", "UTF-8") + "=" +
                    URLEncoder.encode(tTime, "UTF-8");

            data += "&" + URLEncoder.encode("address", "UTF-8") + "=" +
                    URLEncoder.encode(tAddress, "UTF-8");

            data += "&" + URLEncoder.encode("title", "UTF-8") + "=" +
                    URLEncoder.encode(tTitle, "UTF-8");

            data += "&" + URLEncoder.encode("content", "UTF-8") + "=" +
                    URLEncoder.encode(tContent, "UTF-8");

            data += "&" + URLEncoder.encode("image", "UTF-8") + "=" +
                    URLEncoder.encode(imageBitMap, "UTF-8");

            data += "&" + URLEncoder.encode("tID", "UTF-8") + "=" +
                    URLEncoder.encode(tid, "UTF-8");

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

            String [] list  = sb.toString().split("^");


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
            Toast.makeText(context,"Added!",Toast.LENGTH_LONG).show();
            Intent aIntent = new Intent(activity, TravelDetailsList.class);
            aIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            aIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(aIntent);
        }

    }



}