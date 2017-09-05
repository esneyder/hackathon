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

public class RegisterActivity extends AppCompatActivity {
private EditText emailEditText;
private EditText passEditText;
private EditText nameEditText;
private Auhentication auhentication;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    auhentication = new Auhentication();
    // Address the email and password field
    emailEditText = (EditText) findViewById(R.id.email);
    passEditText = (EditText) findViewById(R.id.password);
    nameEditText = (EditText) findViewById(R.id.username);
}

public void checkRegister(View arg0) {
    final String name = nameEditText.getText().toString();
    if (!Methods.isValidInout(name)) {
        //Set error message for email field
        emailEditText.setError("Debe ingresar un usuario");
    }
    
    final String pass = passEditText.getText().toString();
    if (!Methods.isValidPassword(pass)) {
        //Set error message for password field
        passEditText.setError("Debe ingresar una contraseña");
    }
    final String email = emailEditText.getText().toString();
    if (!Methods.isValidPassword(pass)) {
        //Set error message for password field
        emailEditText.setError("Debe ingresar un correo válido");
    }
    
    if(Methods.isValidInout(name) && Methods.isValidPassword(pass) && Methods.isValidEmail(email))
    {
        if(auhentication.register(name,email,pass)){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            Alerter.create(this)
                    .setTitle("Error!")
                    .setText("Algo salió mal. intenta nuevamente..")
                    .setBackgroundColorRes(R.color.colorAccent)
                    .show();
        }
    }
    
}
public void  back (View v){
    startActivity(new Intent(this, LoginActivity.class));
}
}
