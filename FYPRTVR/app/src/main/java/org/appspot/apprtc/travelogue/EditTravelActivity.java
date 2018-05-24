package org.appspot.apprtc.travelogue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.appspot.apprtc.account.ChangeIP;
import org.appspot.apprtc.share.ShareFragment;
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

public class EditTravelActivity extends AsyncTask<String,Void,String[]> {
    private Context context;
    private Activity activity;
    private static String IP;
    ChangeIP cIP = new ChangeIP();

    //flag 0 means get and 1 means post.(By default it is get.)
    public EditTravelActivity(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    //從前面get string variable
    @Override
    protected String[] doInBackground(String... string) {

        try {
            //this is post function

            IP=cIP.getIP();
            String userID = string[0].toString();
            String tID = string[1].toString();
            String tTitle = string[2].toString();
            String imageBitMap = string[3].toString();

            //// TODO: 4/5/2017  change the php address
            String link = "http://"+IP+"/fyp2017/travelUpdate.php";
            //set the post data
            String data  = URLEncoder.encode("userId", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");

            data += "&" + URLEncoder.encode("travelId", "UTF-8") + "=" +
                    URLEncoder.encode(tID, "UTF-8");

            data += "&" + URLEncoder.encode("title", "UTF-8") + "=" +
                    URLEncoder.encode(tTitle, "UTF-8");

            data += "&" + URLEncoder.encode("image", "UTF-8") + "=" +
                    URLEncoder.encode(imageBitMap, "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            //send the data to php
            wr.write(data);
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

            String[] list = sb.toString().split(",");


            //todo testing

            reader.close();


            return list;


        } catch (Exception e) {
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

        if (result==null) {
            Toast.makeText(context, result[1], Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(context, result[1], Toast.LENGTH_LONG).show();

        /*    Intent intent = new Intent(activity, ShareMain.class);
            activity.finish();
            activity.startActivity(activity.getIntent());
            activity.startActivity(intent);
*/
            //mIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            Intent intent = new Intent(activity, ShareMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            activity.startActivity(intent);


           // activity.setIntent(mIntent);
           // activity.startActivity(activity.getIntent());

            //activity.finish();



        }

    }


}
