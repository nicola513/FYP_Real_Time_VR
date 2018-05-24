package org.appspot.apprtc.travelogue;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;

import org.appspot.apprtc.R;
import org.appspot.apprtc.account.ChangeIP;
import org.appspot.apprtc.account.Login;
import org.appspot.apprtc.account.Registration;
import org.appspot.apprtc.account.RegistrationActivity;
import org.appspot.apprtc.account.UserInfo;
import org.appspot.apprtc.share.ShareFragment;
import org.appspot.apprtc.share.ShareMain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


public class TravelDetailsList extends Activity {

    private Context context;
    private Activity activity;
    private ListView travelDetailsList;
    ImageButton addTravelDetail, refresh;
    public static String Did;
    public static String aDate;
    public static String aTime;
    public static String aAddress;
    public static String aTitle;
    public static String aContent;
    public static String aImage;

    ShareMain shareMain = new ShareMain ();

   private static String tid;
    private static String IP;
    ChangeIP cIP = new ChangeIP();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_details_list);


        IP=cIP.getIP();
        tid = shareMain.getSelectTid();
        addTravelDetail = (ImageButton) findViewById(R.id.addTravelDetail) ;

        refresh = (ImageButton) findViewById(R.id.refresh) ;

        addTravelDetail.setOnClickListener(addListener);
        refresh.setOnClickListener(addListener);

        travelDetailsList=(ListView)findViewById(R.id.listDetail);


        new DetailsListActivity(this,travelDetailsList,activity).execute(tid);
        travelDetailsList.setEmptyView(findViewById(R.id.empty));

        context = this;
        activity = this;
        registerForContextMenu(travelDetailsList);
        travelDetailsList.setLongClickable(true);

 /*       travelDetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();

                 new GetDIDActivity().execute(text);


                //Toast.makeText(getApplicationContext(), DidText, Toast.LENGTH_LONG).show();

                PopupWindow popUp = new PopupWindow(TravelDetailsList.this);
                LinearLayout layout = new LinearLayout(TravelDetailsList.this);
                TextView tv= new TextView(TravelDetailsList.this);
                Button ed =new Button(TravelDetailsList.this);
                ed.setText("Edit");
                ed.setTextSize(25);
                ed.setWidth(100);
                Button de = new Button(TravelDetailsList.this);
                de.setText("Delete");
                de.setTextSize(25);
                ed.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){

                        Intent intent = new Intent();
                        intent.setClass(TravelDetailsList.this, EditDetail.class);
                        startActivity(intent);

                    }
                });
                de.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Do you want to delete this Travel Details ID. "+getDID()+"?")
                                .setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();

                    }
                });


                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(ed, params);
                layout.addView(de, params);
                popUp.setContentView(layout);
                popUp.setFocusable(true);
                popUp.setOutsideTouchable(true);
                popUp.setBackgroundDrawable(new ColorDrawable(
                        Color.TRANSPARENT));
                popUp.showAtLocation(layout,Gravity.BOTTOM,0,0);



            }
       });

*/

    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listDetail) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //YourObject obj = (YourObject) lv.getItemAtPosition(acmi.position);
            String text = (String) travelDetailsList.getItemAtPosition(acmi.position).toString();
            new GetDIDActivity().execute(text);

            menu.setHeaderTitle("Action");
            menu.add("Edit");
            menu.add("Delete");

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
     /*


        int listPosition = info.position;
        String text = travelDetailsList.getItemAtPosition(listPosition).toString();
        new GetDIDActivity().execute(text);

        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        Toast.makeText(getApplicationContext(), menuItemIndex, Toast.LENGTH_LONG).show();
        return true;*/


        if (item.getTitle() == "Edit") {
           // Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(TravelDetailsList.this, EditDetail.class);
            startActivity(intent);


        } else if (item.getTitle() == "Delete") {
            //Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to delete this Travel Details ID. "+getDID()+"?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();


        } else {
            return false;
        }
        return true;


    }





    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:

                    new DeleteDetailsActivity(context, activity).execute(getDID());

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };




    private ImageButton.OnClickListener addListener = new ImageButton.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {

                case R.id.addTravelDetail:
                    intent.setClass(TravelDetailsList.this, AddTravelDetails.class);
                    startActivity(intent);
                    break;

                case R.id.refresh:
                    Intent reIntent = getIntent();
                    finish();
                    startActivity(reIntent);
                    break;

            }
        }
    };




    public static void setDID(String Did) {
        TravelDetailsList.Did = Did;
    }

    public static String getDID() {
        return Did;
    }

    public static void setDDate(String aDate) {
        TravelDetailsList.aDate = aDate;
    }

    public static String getDDate() {
        return aDate;
    }

    public static void setDTime(String aTime) {
        TravelDetailsList.aTime = aTime;
    }

    public static String getDTime() {
        return aTime;
    }

    public static void setDAddress(String aAddress) {
        TravelDetailsList.aAddress = aAddress;
    }

    public static String getDAddress() {
        return aAddress;
    }

    public static void setDTitle(String aTitle) {
        TravelDetailsList.aTitle = aTitle;
    }

    public static String getDTitle() {
        return aTitle;
    }

    public static void setDContent(String aContent) {
        TravelDetailsList.aContent = aContent;
    }

    public static String getDContent() {
        return aContent;
    }

    public static void setDImage(String aImage) {
        TravelDetailsList.aImage = aImage;
    }

    public static String getDImage() {
        return aImage;
    }




    public class GetDIDActivity extends AsyncTask<String,Void,String[]> {

        @Override
        protected String[] doInBackground(String ... string) {
            ArrayList strings = new ArrayList<String>();

            try{
                //this is post function
                String ddetail = string[0].toString();


                String link="http://"+IP+"/fyp2017/getDid.php";
                //set the post data
                String data  = URLEncoder.encode("ddetail", "UTF-8") + "=" +
                        URLEncoder.encode(ddetail, "UTF-8");


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
           // Toast.makeText(getApplicationContext(), result[0], Toast.LENGTH_LONG).show();
            setDID(result[0]);
            new GetDDetailsActivity().execute(getDID());
        }
    }


    public class GetDDetailsActivity extends AsyncTask<String,Void,String[]> {

        @Override
        protected String[] doInBackground(String ... string) {
            ArrayList strings = new ArrayList<String>();

            try{
                //this is post function
                String dID = string[0].toString();


                String link="http://"+IP+"/fyp2017/getDetail.php";
                //set the post data
                String data  = URLEncoder.encode("dID", "UTF-8") + "=" +
                        URLEncoder.encode(dID, "UTF-8");


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

                String [] list = sb.toString().split("%");

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
            // Toast.makeText(getApplicationContext(), result[0], Toast.LENGTH_LONG).show();
            setDDate(result[0]);
            setDTime(result[1]);
            setDAddress(result[2]);
            setDTitle(result[3]);
            setDContent(result[4]);
            setDImage(result[5]);
        }
    }




}
