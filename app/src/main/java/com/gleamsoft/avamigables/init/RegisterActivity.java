package com.gleamsoft.avamigables.init;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gleamsoft.avamigables.R;
import com.gleamsoft.avamigables.home.MainActivity;
import com.gleamsoft.avamigables.init.repository.Auhentication;
import com.gleamsoft.avamigables.util.Methods;
import com.gleamsoft.avamigables.util.State;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.tapadoo.alerter.Alerter;

public class RegisterActivity extends AppCompatActivity {
private EditText emailEditText;
private EditText passEditText;
private EditText userEditText;
private EditText nameEditText;
private EditText phoneEditText;
private EditText plateEditText;
private Auhentication auhentication;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    auhentication = new Auhentication();
    // Address the email and password field
    emailEditText = (EditText) findViewById(R.id.email);
    passEditText = (EditText) findViewById(R.id.password);
    userEditText = (EditText) findViewById(R.id.username);
    nameEditText = (EditText) findViewById(R.id.name);
    phoneEditText = (EditText) findViewById(R.id.phone);
    plateEditText = (EditText) findViewById(R.id.plate);
}

public void checkRegister(View arg0) {
    final String user = userEditText.getText().toString();
    if (!Methods.isValidUsername(user)) {
        //Set error message for email field
        emailEditText.setError("Debe ingresar su usuario");
    }
    
    final String name = nameEditText.getText().toString();
    if (!Methods.isValidInput(name)) {
        //Set error message for email field
        nameEditText.setError("Debe ingresar su nombre");
    }
    final String phone = phoneEditText.getText().toString();
    if (!Methods.isValidInput(phone)) {
        //Set error message for email field
        phoneEditText.setError("Debe ingresar su usuario");
    }
    
    final String plate = plateEditText.getText().toString();
    if (!Methods.isValidInput(plate)) {
        //Set error message for email field
        phoneEditText.setError("Debe ingresar su usuario");
    }
    
    final String pass = passEditText.getText().toString();
    if (!Methods.isValidPassword(pass)) {
        //Set error message for password field
        passEditText.setError("Debe ingresar una contraseña");
    }
    final String email = emailEditText.getText().toString();
    if (!Methods.isValidEmail(email)) {
        //Set error message for password field
        emailEditText.setError("Debe ingresar un correo válido");
    }
    
    ParseUser userlogin = new ParseUser();
    userlogin.setUsername(user);
    userlogin.setPassword(pass);
    userlogin.setEmail(email);
    userlogin.put("name", name);
    userlogin.put("phone", phone);
    userlogin.put("plate", plate);
    userlogin.put("state", State.active);
    
    userlogin.signUpInBackground(new SignUpCallback() {
        public void done(ParseException e) {
            if (e == null) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            } else {
                Alerter.create(RegisterActivity.this)
                        .setTitle("Error!")
                        .setText("Algo salió mal. intenta nuevamente..")
                        .setBackgroundColorRes(R.color.colorAccent)
                        .show();
            }
        }
    });
    
    
    
     
    
}
public void  back (View v){
    startActivity(new Intent(this, LoginActivity.class));
}
}
