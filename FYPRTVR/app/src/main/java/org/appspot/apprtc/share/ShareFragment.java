package org.appspot.apprtc.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;

import org.appspot.apprtc.R;
import org.appspot.apprtc.account.ChangeIP;
import org.appspot.apprtc.account.UserInfo;
import org.appspot.apprtc.travelogue.AddTravelActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class ShareFragment extends Fragment {
    private CallbackManager mCallbackManager;
    private OnShareContentChangedListener mShareContentChangedListener;
    private ViewPager mViewPage;
    private static String IP;
    ChangeIP cIP = new ChangeIP();

    public static Context context;
    public static Activity activity;
    public static ArrayList<Pair <Pair < Pair <Bitmap, String>,  String>,  String > > IMAGE_IDS = new ArrayList<>();


    public void setIMAGE(ArrayList<String> title,ArrayList<String> image,ArrayList<String> tid){
        IMAGE_IDS.clear();
        IP=cIP.getIP();
        /*if(title.size()==0||image.size()==0){
            Drawable myDrawable = getResources().getDrawable(R.drawable.photo1);
            Bitmap defaultImage      = ((BitmapDrawable) myDrawable).getBitmap();
            IMAGE_IDS.add( new Pair<>(  new Pair<>(defaultImage,
                    "https://d3uu10x6fsg06w.cloudfront.net/shareitexampleapp/goofy/index.html"),"me"));
        }else{*/

        for(int i=0;i<tid.size();i++){

            Log.v("Image String", title.get(i)+"");
            byte[] decodedString = Base64.decode(image.get(i), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            IMAGE_IDS.add(new Pair<>( new Pair<>(  new Pair<>(decodedImage,"http://"+IP+"/fyp2017/travelogueOnline.php?tid="+tid.get(i)),title.get(i)),tid.get(i)));
        }
        //}

        //IMAGE_IDS.add( new Pair<>(  new Pair<>(R.drawable.photo2,"https://d3uu10x6fsg06w.cloudfront.net/shareitexampleapp/liking/index.html")    ,"2"));
        //IMAGE_IDS.add( new Pair<>(  new Pair<>(R.drawable.photo3,"https://d3uu10x6fsg06w.cloudfront.net/shareitexampleapp/viking/index.html")    ,"3"));
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();

        View view = inflater.inflate(R.layout.share_it_view, container, false);
        Activity activity = getActivity();

        //new ShowCoverActivity(activity,view).execute();

        /*setBip = getImageID();
        setTid = getTid();
        setTitle = getTitlet();*/


        setupViewPage(view);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setOnShareContentChangeListener(OnShareContentChangedListener listener) {
        mShareContentChangedListener = listener;
    }

    public Bitmap getImage(){
        return IMAGE_IDS.get(mViewPage.getCurrentItem()).first.first.first;
    }

    public String getCurrentShareContent() {
        return IMAGE_IDS.get(mViewPage.getCurrentItem()).first.first.second;
    }

    public String getTitle(){
        return IMAGE_IDS.get(mViewPage.getCurrentItem()).first.second;
    }

    public String getTid(){
        return IMAGE_IDS.get(mViewPage.getCurrentItem()).second;
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ShareImageFragment imageFragment = new ShareImageFragment();

            imageFragment.setImage(IMAGE_IDS.get(position).first.first.first);

            imageFragment.setImageTitle(IMAGE_IDS.get(position).first.second);

            imageFragment.setTid(IMAGE_IDS.get(position).second);

            imageFragment.setContext(context);

            imageFragment.setActivity(activity);

            return imageFragment;
        }

        @Override
        public int getCount() {
            return IMAGE_IDS.size();
        }
    }

    private void setupViewPage(View view) {
        mViewPage = (ViewPager) view.findViewById(R.id.pager);
        PagerAdapter adapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mViewPage.setAdapter(adapter);

        final PageSelector pageSelector = (PageSelector) view.findViewById(R.id.page_selector);
        pageSelector.setImageCount(IMAGE_IDS.size());

        //final LikeView photoLike = (LikeView) view.findViewById(R.id.like_photo);
        //photoLike.setFragment(this);

        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(
                    int position,
                    float positionOffset,
                    int positionOffsetPixels) {
                pageSelector.setPosition(position);
                String shareContent = IMAGE_IDS.get(position).first.first.second;
                //photoLike.setObjectIdAndType(
                //shareContent,
                //LikeView.ObjectType.OPEN_GRAPH);
                mShareContentChangedListener.onShareContentChanged(shareContent);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public interface OnShareContentChangedListener {
        void onShareContentChanged(String content);
    }







}
