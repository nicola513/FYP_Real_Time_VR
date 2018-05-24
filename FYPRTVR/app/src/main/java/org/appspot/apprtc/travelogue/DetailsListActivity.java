package org.appspot.apprtc.travelogue;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import org.appspot.apprtc.R;
import org.appspot.apprtc.account.ChangeIP;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ownder on 04/05/17.
 */

 public class DetailsListActivity extends AsyncTask<String,Void,String[]> {
    private Context context;
    private ListView list;
    private Activity activity;
    private static String IP;
    ChangeIP cIP = new ChangeIP();

    public DetailsListActivity(Context context,ListView list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }


    @Override
    protected String[] doInBackground(String ... string) {
        ArrayList strings = new ArrayList<String>();

        try{

            IP=cIP.getIP();
            ChangeIP cIP = new ChangeIP();
            String tid = string[0].toString();

            String link="http://"+IP+"/fyp2017/showDetailsList.php";

            String data = URLEncoder.encode("tID", "UTF-8") + "=" +
                    URLEncoder.encode(tid, "UTF-8");


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
                break;
            }

            String [] list  = sb.toString().split("%");


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


    @Override
    protected void onPostExecute(String [] result) {

        if (result[0] == "0") {

            Toast.makeText(context, "No Detail Here!", Toast.LENGTH_SHORT).show();

        }else{

            String[] iDid = new String[result.length];
            String[] iDate = new String[result.length];
            String[] iTime = new String[result.length];
            String[] iTitle = new String[result.length];
            String[] iAddress = new String[result.length];
            String[] iContent = new String[result.length];


            for (int i = 0; i < result.length; i += 6) {
                iDid[i] = result[i];
                iDate[i] = result[i + 1];
                iTime[i] = result[i + 2];
                iTitle[i] = result[i + 3];
                iAddress[i] = result[i + 4];
                iContent[i] = result[i + 5];

            }


            List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < iDate.length; i += 6) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("did", iDid[i]);
                item.put("date", iDate[i]);
                item.put("time", iTime[i]);
                item.put("title", iTitle[i]);
                item.put("address", iAddress[i]);
                item.put("content", iContent[i]);
                items.add(item);
            }

            SimpleAdapter simpleAdapter = new SimpleAdapter(this.context,
                    items, R.layout.detail_list_view, new String[]{"did","date", "time", "title", "address", "content"}, new int[]{R.id.detail_did,R.id.detail_date, R.id.detail_time, R.id.detail_title, R.id.detail_address, R.id.detail_content});
            this.list.setAdapter(simpleAdapter);
           // this.list.setEmptyView(activity. findViewById(R.id.empty));
        }

    }

}