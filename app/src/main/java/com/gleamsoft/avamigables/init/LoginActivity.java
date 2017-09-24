package com.gleamsoft.avamigables.init;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gleamsoft.avamigables.R;
import com.gleamsoft.avamigables.home.MainActivity;
import com.gleamsoft.avamigables.init.repository.Auhentication;
import com.gleamsoft.avamigables.util.Methods;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.tapadoo.alerter.Alerter;

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
    
    ParseUser currentUser = ParseUser.getCurrentUser();
    if(currentUser != null){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}

public void checkLogin(View arg0) {
    
    final String email = emailEditText.getText().toString();
    if (!Methods.isValidInput(email)) {
        //Set error message for email field
        emailEditText.setError("Debe ingresar un usuario");
    }
    
    final String pass = passEditText.getText().toString();
    if (!Methods.isValidPassword(pass)) {
        //Set error message for password field
        passEditText.setError("Debe ingresar una contraseña");
    }
    
    
    
    ParseUser.logInInBackground(email, pass, new LogInCallback() {
        public void done(ParseUser user, ParseException e) {
            
            if (user != null) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Alerter.create(LoginActivity.this)
                        .setTitle("Error!")
                        .setText("Los datos ingresados no son válidos...")
                        .setBackgroundColorRes(R.color.colorAccent)
                        .show();
            }
        }
    });
    
         
    
    
}

    public void checRegister(View arg0) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
