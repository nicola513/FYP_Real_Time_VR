package org.appspot.apprtc.account;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.appspot.apprtc.MainActivitySelect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


public class RegistrationActivity extends AsyncTask<String,Void,String[]> {
    private Context context;
    private TextView stute;
    private static String IP;
    ChangeIP cIP = new ChangeIP();

    //flag 0 means get and 1 means post.(By default it is get.)
    public RegistrationActivity(Context context,TextView stute) {
        this.context = context;
        this.stute = stute;
    }

    //從前面get string variable
    @Override
    protected String[] doInBackground(String ... string) {
        ArrayList strings = new ArrayList<String>();

        try{
            //this is post function
            IP=cIP.getIP();
            String email    = string[0].toString();
            String password = string[1].toString();
            String name = string[2].toString();


            String link="http://"+IP+"/fyp2017/registration.php";
            //set the post data
            String data  = URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");

            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(name, "UTF-8");

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

    //this function is for show data, data 要在這才離開background，這才可以output。
    @Override
    protected void onPostExecute(String [] result){
        //String text = result.get(0);
        Toast.makeText(context,"Registration Success ", Toast.LENGTH_LONG).show();
        //Log.e(TAG, "The result is " + text);
        this.stute.setText(result[1]);
    }


}