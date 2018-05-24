package org.appspot.apprtc.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.appspot.apprtc.MainActivitySelect;
import org.appspot.apprtc.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by ownder on 04/05/17.
 */

public class UserInfo extends Activity {

    private Context context;
    public static String userID, userName, userEmail, userPassword;
    private UserInfoActivity userTask = null;
    Login login = new Login();
    private static String IP;
    ChangeIP cIP = new ChangeIP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IP=cIP.getIP();

        userEmail = login.getEmail();
        userPassword = login.getPassword();

        context = this;
        useUserTask();


        /*Intent goToMain = new Intent(UserInfo.this, MainActivitySelect.class);
        startActivity(goToMain);*/

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent goToMain = new Intent(UserInfo.this, MainActivitySelect.class);
                startActivity(goToMain);

            }
        }, 5000);





    }

    @Override
    protected void onStart() {
        super.onStart();
        setVisible(true);
    }


    public static void setUserID(String userID) {
        UserInfo.userID = userID;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserName(String userName) {
        UserInfo.userName = userName;
    }

    public static String getUserName() {
        return userName;
    }



    public class UserInfoActivity extends AsyncTask<String,Void,String[]> {

        @Override
        protected String[] doInBackground(String ... string) {
            ArrayList strings = new ArrayList<String>();

            try{
                //this is post function
                String email    = string[0].toString();
                String password = string[1].toString();


                String link="http://"+IP+"/fyp2017/login.php";
                //set the post data
                String data  = URLEncoder.encode("email", "UTF-8") + "=" +
                        URLEncoder.encode(email, "UTF-8");

                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");


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
                }

                String [] list = sb.toString().split(",");

                //todo testing

                reader.close();

                return list;


            } catch(Exception e){
                //return new String("Exception: " + e.getMessage());
                strings.add("Exception: " + e.getMessage());
                return null;
            }
        }


        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String[] result) {
            setUserID(result[1]);
            setUserName(result[2]);
            //TuserID.setText(getUserID());
            //TuserName.setText(getUserName());
        }
    }

    private void useUserTask() {
        if (userTask != null && userTask.getStatus() != UserInfoActivity.Status.FINISHED) {
            userTask.cancel(true);
        }
        userTask = (UserInfoActivity) new UserInfoActivity().execute(userEmail, userPassword);
    }



}
