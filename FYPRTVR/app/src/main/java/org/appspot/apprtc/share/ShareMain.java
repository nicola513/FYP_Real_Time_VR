package org.appspot.apprtc.share;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.appspot.apprtc.R;
import org.appspot.apprtc.account.UserInfo;
import org.appspot.apprtc.travelogue.AddTravel;
import org.appspot.apprtc.travelogue.DeleteTravelActivity;
import org.appspot.apprtc.travelogue.EditTravel;
import org.appspot.apprtc.travelogue.TravelDetailsList;

public class ShareMain extends ActionBarActivity {
    private ShareActionProvider mShareActionProvider;
    private static ShareFragment mShareFragment;
    private AddTravel mAddTravel;
    private Context context;
    private Activity activity;
    public static String userID;
    UserInfo userInfo = new UserInfo();



    public static String SelectTid;
    public static String SelectTitle;
    public static Bitmap SelectImage;
   // String DeleteTid = "JKA45";
   // String DeleteTitle = "title";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.share_main);


        //new ShowTravelActivity(context, mShareFragment).execute();
        //mShareFragment.setIMAGE_IDS();

        mShareFragment.context = this;
        mShareFragment.activity = this;

        FragmentManager fm = getSupportFragmentManager();
        mShareFragment = (ShareFragment) fm.findFragmentById(R.id.sharefragment);
        mShareFragment.setOnShareContentChangeListener(
                new ShareFragment.OnShareContentChangedListener() {
                    @Override
                    public void onShareContentChanged(String content) {
                        setShareUrl(content);
                    }
                });
        userID = userInfo.getUserID();



        //SelectTid = getSelectTid();
        //show = showlist[0];
        context = this;
        activity = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.share_menu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        ImageButton addTravel = (ImageButton) findViewById(R.id.addTravel);
        ImageButton refresh= (ImageButton) findViewById(R.id.refresh);
        Button delete = (Button) findViewById(R.id.addTravelDelete);
        Button edit = (Button) findViewById(R.id.addTravelEdit);

        ImageView image  = (ImageView) findViewById(R.id.share_image);

        addTravel.setOnClickListener(addListener);
        refresh.setOnClickListener(addListener);

        delete.setOnClickListener(btnListener);
        edit.setOnClickListener(btnListener);

        //image.setOnClickListener(imageListener);



        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareUrl(mShareFragment.getCurrentShareContent());

        // Return true to display menu
        return true;
    }

    private void setShareUrl(String shareUrl) {
        // When using androids share built into the ActionBar app attribution will not be
        // present when sharing to facebook and app events will not be logged.
        if (mShareActionProvider != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
            mShareActionProvider.setShareIntent(shareIntent);
            //Toast.makeText(context, ""+shareUrl, Toast.LENGTH_LONG).show();
        }//else{
           // Toast.makeText(context, "null", Toast.LENGTH_LONG).show();
        //}
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        // Don't save any state
    }

    private ImageButton.OnClickListener addListener = new ImageButton.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {

                case R.id.addTravel:
                    intent.setClass(ShareMain.this, AddTravel.class);
                    startActivity(intent);
                    break;

                case R.id.refresh:

                    Toast.makeText(context,"Loading... ", Toast.LENGTH_LONG).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent reIntent = getIntent();
                            finish();
                            startActivity(reIntent);

                        }
                    }, 10000);


                   /* Intent reIntent = getIntent();
                    finish();
                    startActivity(reIntent);*/

                    break;
            }
        }
    };


    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:

                    new DeleteTravelActivity(context,activity).execute(userID,getSelectTid());
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };




    private Button.OnClickListener btnListener = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {

                case R.id.addTravelDelete:

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("User: "+userID+", Do you want to delete Travel_Id: "+getSelectTid()+" Title: "+getSelectTitle()+"?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                    break;

                case R.id.addTravelEdit:
                    //// TODO: 5/5/2017 please pass the tid,title,image to editTravel, there has one example

                    //Toast.makeText(context,"Page Number: "+ mShareFragment.getTid(), Toast.LENGTH_LONG).show();

                    intent.setClass(ShareMain.this, EditTravel.class);
                    startActivity(intent);

                    break;
            }
        }
    };

 /*   private ImageView.OnClickListener imageListener = new ImageView.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {

                case R.id.share_image:
                    intent.setClass(ShareMain.this, TravelDetailsList.class);
                    startActivity(intent);
                    break;

            }
        }
    };
*/
   // SelectTid = mShareFragment.getTid();
   // SelectTitle = mShareFragment.getTitle();
   // SelectImage = mShareFragment.getImage();



    public static String getSelectTid(){
        return mShareFragment.getTid();
    }

    public static String getSelectTitle(){
        return mShareFragment.getTitle();
    }

    public static Bitmap getSelectImage(){
        return mShareFragment.getImage();
    }



}
