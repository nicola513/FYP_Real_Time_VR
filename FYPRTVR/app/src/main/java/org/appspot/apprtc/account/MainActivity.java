package org.appspot.apprtc.account;

import android.app.Activity;


import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.appspot.apprtc.MainActivitySelect;
import org.appspot.apprtc.R;


public class MainActivity extends Activity {


    private Button login, register, aboutUs;
    private TextView ourName;
    private Boolean showName = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.btnRegister);
        aboutUs = (Button) findViewById(R.id.aboutUs);
        ourName = (TextView)findViewById(R.id.ourName);

        login.setOnClickListener(listener);
        register.setOnClickListener(listener);
        aboutUs.setOnClickListener(listener);


    }



    private Button.OnClickListener listener = new Button.OnClickListener() {

        public void onClick(View v) {
            //ConnectActivity connectActivity = new ConnectActivity();
            Intent intent = new Intent();
            switch (v.getId()) {

                case R.id.btnLogin:
                    intent.setClass(MainActivity.this, Login.class);
                    startActivity(intent);
                    break;

                case R.id.btnRegister:
                    intent.setClass(MainActivity.this, Registration.class);//TODO Registration
                    startActivity(intent);
                    break;

                case R.id.aboutUs:


                    if(showName == false)
                    {
                        ourName.setText(R.string.our_name);
                        showName = true;
                    }
                    else
                    {
                        ourName.setText("");
                        showName = false;
                    }
                   // intent.setClass(MainActivity.this, MainActivitySelect.class);
                    //startActivity(intent);
                    break;

            }
        }
    };


}
