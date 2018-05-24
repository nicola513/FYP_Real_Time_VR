package org.appspot.apprtc.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.appspot.apprtc.R;

public class Registration extends Activity {
    private EditText emailField,passwordField,nameField,password2Field;
    private Button register,backLogin;
    private Context context;
    private TextView stute;
    private Boolean result = true;
    private String email,password,name,password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailField = (EditText)findViewById(R.id.email);
        passwordField = (EditText)findViewById(R.id.password);
        nameField = (EditText)findViewById(R.id.name);
        password2Field = (EditText)findViewById(R.id.password2);

        register = (Button)findViewById(R.id.btnRegister);
        backLogin = (Button)findViewById(R.id.btnLinkToLoginScreen);
        stute = (TextView)findViewById(R.id.hints);
        context = this;

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                email = emailField.getText().toString();
                password = passwordField.getText().toString();
                name  = nameField.getText().toString();
                password2 = password2Field.getText().toString();
                if(checkingEmail(email)!=null)
                    stute.setText(checkingEmail(email));
                else if(checkingPassword(password,password2)!=null) {
                    stute.setText(checkingPassword(password,password2));
                }
                else if(checkingName(name)!=null){
                    stute.setText(checkingName(name));
                }else{
                    new RegistrationActivity(context,stute).execute(email,password,name);
                    Intent intent = new Intent();
                    intent.setClass(Registration.this,Login.class);
                    startActivity(intent);
                }



            }
        });


        backLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setClass(Registration.this,Login.class);
                startActivity(intent);
            }
        });
    }

    public String checkingEmail(String email){

        if(email.matches(" ")){
            return "Please enter your email address.";
        }else{
            result = email.matches("^[\\w|\\.]*@[\\w\\-]+(\\.[\\w\\-]+)+");
            if(!result){
                return "Email error!";
            }else {
                return null;
            }
        }
    }

    public String checkingPassword(String password,String password2){
        if(password.matches("")) {
            return "Please enter your password.";
        }else if(password2.matches("")){
            return "Please enter your password.";
        }else{
            result = password.matches(password2);
            if(!result){
                return "Two password is not equal.";
            }else{
                return null;
            }
        }
    }

    public String checkingName(String name){
        Boolean hasError = false;
        if(name.matches("")){
            return "Please enter your name.";
        }else{
            String [] username = name.split(" ");
            for(int i =0 ; i<username.length;i++){
                result = username[i].matches("^[\\w|\\.]*");
                if(!result){
                    hasError = true;
                }
            }
            if(!hasError)
                return null;
            else
                return "User name error";
        }
    }
}

