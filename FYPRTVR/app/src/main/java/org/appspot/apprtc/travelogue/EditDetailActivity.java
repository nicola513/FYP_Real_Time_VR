package org.appspot.apprtc.travelogue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.appspot.apprtc.account.ChangeIP;
import org.appspot.apprtc.share.ShareMain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by ngnicola on 5/5/2017.
 */

public class EditDetailActivity extends AsyncTask<String,Void,String[]> {
    private Context context;
    private Activity activity;
    private static String IP;
    ChangeIP cIP = new ChangeIP();

    //flag 0 means get and 1 means post.(By default it is get.)
    public EditDetailActivity(Context context,Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    //從前面get string variable
    @Override
    protected String[] doInBackground(String... string) {

        try{
            //this is post function

            IP=cIP.getIP();
            String did = string[0].toString();
            String tDate = string[1].toString();
            String tTime = string[2].toString();
            String tAddress = string[3].toString();
            String tTitle = string[4].toString();
            String tContent = string[5].toString();
            String tImage = string[6].toString();


            //// TODO: 5/5/2017 please change the link address
            String link="http://"+IP+"/fyp2017/detailUpdate.php";
            //set the post data
            String data  = URLEncoder.encode("did", "UTF-8") + "=" +
                    URLEncoder.encode(did, "UTF-8");

            data += "&" + URLEncoder.encode("date", "UTF-8") + "=" +
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
                    URLEncoder.encode(tImage, "UTF-8");


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
            String [] strings = new String[2];
            strings[0]="0";
            strings[1]="Exception: " + e.getMessage();
            return strings;
        }
    }

    protected void onPreExecute() {
    }

    //this functon is for show data, data 要在這才離開background，這才可以output。
    @Override
    protected void onPostExecute(String[] result) {

        if (result[0]=="0") {
            Toast.makeText(context, result[1], Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, result[1], Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity, TravelDetailsList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);


        }

    }


}

