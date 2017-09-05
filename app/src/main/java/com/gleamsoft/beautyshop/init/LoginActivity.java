package com.gleamsoft.beautyshop.init;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gleamsoft.beautyshop.R;
import com.gleamsoft.beautyshop.home.MainActivity;
import com.gleamsoft.beautyshop.init.repository.Auhentication;
import com.gleamsoft.beautyshop.util.Methods;
import com.tapadoo.alerter.Alerter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class LoginActivity extends AppCompatActivity {
private EditText emailEditText;
private EditText passEditText;
private Auhentication auhentication;
@Override
protected void onCreate(Bundle savedInstanceState) {
   
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    auhentication = new Auhentication();
    // Address the email and password field
    emailEditText = (EditText) findViewById(R.id.username);
    passEditText = (EditText) findViewById(R.id.password);
}

public void checkLogin(View arg0) {
    
    final String email = emailEditText.getText().toString();
    if (!Methods.isValidInout(email)) {
        //Set error message for email field
        emailEditText.setError("Debe ingresar un usuario");
    }
    
    final String pass = passEditText.getText().toString();
    if (!Methods.isValidPassword(pass)) {
        //Set error message for password field
        passEditText.setError("Debe ingresar una contraseña");
    }
    
    if(Methods.isValidInout(email) && Methods.isValidPassword(pass))
    {
        if(auhentication.login(email,pass)){
           startActivity(new Intent(this, MainActivity.class));
        }else{
            Alerter.create(this)
                    .setTitle("Error!")
                    .setText("Los datos ingresados no son válidos...")
                    .setBackgroundColorRes(R.color.colorAccent)
                    .show();
        }
    }
    
}

    public void checRegister(View arg0) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
