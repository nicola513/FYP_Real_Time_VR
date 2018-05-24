package org.appspot.apprtc.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.appspot.apprtc.R;

/**
 * Created by ownder on 05/05/17.
 */

public class Login extends Activity {
    private EditText emailField,passwordField;
    private TextView status, test;
    public static String sendEmail, sendPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = (EditText)findViewById(R.id.email_ac);
        passwordField = (EditText)findViewById(R.id.password);

        status = (TextView)findViewById(R.id.hints);

    }


    public void Registration(View view){
        Intent intent = new Intent();
        intent.setClass(Login.this,Registration.class);
        startActivity(intent);
    }


    public void loginPost(View view){
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        //execute 是用來給資料給background functio。
        if(checkingEmail(email)!=null){
            status.setText(checkingEmail(email));
        }else if(checkingPassword(password)!=null){
            status.setText(checkingPassword(password));
        }else {
            new LoginActivity(this, status).execute(email, password);
            setEmail(email);
            setPassword(password);
            //status.setText(userName);
            if(sendEmail==null&&sendPassword==null){
                Toast.makeText(this, "No Data! ", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Loading... ", Toast.LENGTH_LONG).show();
            }


            Intent intent = new Intent(this, UserInfo.class);
            startActivity(intent);


        }
    }


    public String checkingEmail(String email){

        if(email.matches("")){
            return "Please enter your email address.";
        }
        Boolean result = email.matches("^[\\w|\\.]*@[\\w\\-]+(\\.[\\w\\-]+)+");
        if(!result){
            return "Email error!";
        }
        return null;
    }

    public String checkingPassword(String password){
        if(password.matches("")) {
            return "Please enter your password.";
        }
        return null;
    }

    public void setEmail(String sendEmail){
        this.sendEmail = sendEmail;
    }
    public String getEmail(){
        return sendEmail;
    }

    public void setPassword(String password){
        this.sendPassword = password;
    }
    public String getPassword(){
        return sendPassword;
    }
}

