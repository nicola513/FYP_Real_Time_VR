package org.appspot.apprtc.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import org.appspot.apprtc.ReturnTravel;
import org.appspot.apprtc.account.ChangeIP;
import org.appspot.apprtc.account.UserInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;



public class ShowTravelActivity extends AsyncTask<String,Void,String[]> {
    private Context context;
    private ShareFragment shareFragment;
    public ReturnTravel returnTravel = null;
    UserInfo userInfo = new UserInfo();
    public static String userID;
    private static String IP;
    ChangeIP cIP = new ChangeIP();


    public ShowTravelActivity(){
    }

    @Override
    protected String[] doInBackground(String... string) {
        ArrayList strings = new ArrayList<String>();

        try {
            IP=cIP.getIP();
            String link = "http://"+IP+"/fyp2017/showTravel.php";

            userID = userInfo.getUserID();
            //set the post data
            String data = URLEncoder.encode("userID", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
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
                //break;
            }

            String[] list = sb.toString().split("%");


            //todo testing

            reader.close();

            return list;


        } catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            strings.add("Exception: " + e.getMessage());
            return null;
        }
    }

    protected void onPreExecute() {
    }


    @Override
    protected void onPostExecute(final String[] result) {

       /* final String[] iTid = new String[result.length];
        final String[] iTitle = new String[result.length];
        final String[] iImage = new String[result.length];

        final Bitmap[] bImage = new Bitmap[result.length];

        for (int i = 0; i < result.length; i += 3) {
            iTid[i] = result[i];
            iTitle[i] = result[i + 1];
            iImage[i] = result[i + 1];
        }

        for(int i=0;i<iImage.length;i++)
        {
            byte[] decodedString = Base64.decode(iImage[i], Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            bImage[i] = decodedImage;
        }

        shareFragment.setTid(iTid);
        shareFragment.setTitle(iTitle);
        shareFragment.setBitmap(bImage);*/

        returnTravel.processFinish(result);




    }




}